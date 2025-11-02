package com.kriptastudios.tastytrade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.core.type.TypeReference;
import com.kriptastudios.tastytrade.model.Account;
import com.kriptastudios.tastytrade.model.Customer;
import com.kriptastudios.tastytrade.instrument.*;
import com.kriptastudios.tastytrade.order.InstrumentType;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * Java client for Tastytrade API
 * Translation and adaptation of 'Session' class in Python SDK by Tastyware.
 */
public class TastytradeClient {
    private static final String API_URL = "https://api.tastyworks.com";
    private static final String CERT_URL = "https://api.cert.tastyworks.com";
    private static final String API_VERSION = "20251026";
    private static final String SDK_VERSION = "0.1.0-SNAPSHOT"; // Version in pom.xml
    private static final Logger logger = LoggerFactory.getLogger(TastytradeClient.class);
    // Format of the date using serialize/deserialize
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    // Configuration fields (finals)
    private final String clientSecret;
    private final String refreshToken;
    private final boolean isTest;
    private final String baseUrl;
    private final OkHttpClient httpClient;
    private final ObjectMapper jsonMapper;

    private static final String VAST_URL = "https://vast.tastyworks.com";
    private static final String TT_DATE_FMT = "yyyy-MM-dd'T'HH:mm:ss'Z'"; // Formato de fecha

    // Fields for the State of the Session (volatiles)
    @Getter
    private volatile String sessionToken;
    private volatile ZonedDateTime sessionExpiration;

    @Getter
    private volatile String streamerToken;
    @Getter
    private volatile String dxlinkUrl;
    private volatile ZonedDateTime streamerExpiration;

    /**
     * Main constructor of the client.
     */
    public TastytradeClient(String clientSecret, String refreshToken, boolean isTest) throws IOException {
        this.clientSecret = clientSecret;
        this.refreshToken = refreshToken;
        this.isTest = isTest;
        this.baseUrl = isTest ? CERT_URL : API_URL;

        // Config ObjectMapper (for JSON)
        this.jsonMapper = new ObjectMapper().registerModule(new JavaTimeModule()).configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Config HTTP Client
        this.httpClient = new OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS).addInterceptor(chain -> {
            // Interceptor to add default headers
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder().header("Accept", "application/json").header("Accept-Version", API_VERSION).header("User-Agent", "kriptastudios/tastytrade-java-sdk:v" + SDK_VERSION);

            // Adds authorization token, EXCEPT for refresh call
            if (!original.url().toString().contains("/oauth/token") && this.sessionToken != null)
            {
                requestBuilder.header("Authorization", "Bearer " + this.sessionToken);
            }
            return chain.proceed(requestBuilder.build());
        }).build();

        // Get initial Session
        // Set expiration in the past to force a refresh
        this.sessionExpiration = ZonedDateTime.now().minusHours(1);
        this.streamerExpiration = ZonedDateTime.now().minusHours(1);
        logger.info("Client initialized. Getting first session...");
        refreshSession();
    }

    /**
     * Private constructor for deserialization.
     */
    private TastytradeClient(SessionState state) {
        // Reconstructs final state
        this.clientSecret = state.getClientSecret();
        this.refreshToken = state.getRefreshToken();
        this.isTest = state.isTest();
        this.baseUrl = this.isTest ? CERT_URL : API_URL;

        // Reconstructs the volatile state
        this.sessionToken = state.getSessionToken();
        this.sessionExpiration = state.getSessionExpiration();
        this.streamerToken = state.getStreamerToken();
        this.dxlinkUrl = state.getDxlinkUrl();
        this.streamerExpiration = state.getStreamerExpiration();

        // Reconstructs the tools (httpClient, jsonMapper)
        this.jsonMapper = new ObjectMapper().registerModule(new JavaTimeModule()).configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        this.httpClient = new OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS).addInterceptor(chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder().header("Accept", "application/json").header("Accept-Version", API_VERSION).header("User-Agent", "kriptastudios/tastytrade-java-sdk:v" + SDK_VERSION);
            if (!original.url().toString().contains("/oauth/token") && this.sessionToken != null)
            {
                requestBuilder.header("Authorization", "Bearer " + this.sessionToken);
            }
            return chain.proceed(requestBuilder.build());
        }).build();
    }

    // PUBLIC METHODS (API of the SDK)

    /**
     * Gets information of client.
     */
    public Customer getCustomer() throws IOException {
        String jsonResponse = get("/customers/me");
        // The generic 'parseResponse' will find the object "Customer"
        return parseResponse(jsonResponse, Customer.class);
    }

    /**
     * Validates current session.
     */
    public boolean validateSession() {
        try
        {
            // It's not necessary to refresh first, if it fails, so be it.
            Request request = new Request.Builder().url(this.baseUrl + "/sessions/validate").post(RequestBody.create(new byte[0])) // POST empty
                    .build();
            try (Response response = httpClient.newCall(request).execute())
            {
                return response.isSuccessful();
            }
        } catch (IOException e)
        {
            logger.warn("Error validating the session: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Serializes the state of the current session into a JSON String.
     */
    public String serialize() throws JsonProcessingException {
        SessionState state = new SessionState();
        state.setClientSecret(this.clientSecret);
        state.setRefreshToken(this.refreshToken);
        state.setTest(this.isTest);
        state.setSessionToken(this.sessionToken);
        state.setSessionExpiration(this.sessionExpiration);
        state.setStreamerToken(this.streamerToken);
        state.setStreamerExpiration(this.streamerExpiration);
        state.setDxlinkUrl(this.dxlinkUrl);
        return jsonMapper.writeValueAsString(state);
    }

    /**
     * Reconstructs a TastytradeClient from a JSON serialized state.
     */
    public static TastytradeClient deserialize(String jsonState) throws IOException {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        SessionState state = mapper.readValue(jsonState, SessionState.class);
        logger.info("Cliente deserialized from JSON saved state.");
        return new TastytradeClient(state);
    }

    // ... ¡AQUÍ AÑADIRÍAS EL RESTO DE MÉTODOS PÚBLICOS! ...
    // ej. public Account getAccount(String accountNumber) throws IOException { ... }
    // ej. public Order placeOrder(...) throws IOException { ... }

    // =======================================================
    // LÓGICA DE SESIÓN Y REFRESCAMIENTO
    // =======================================================

    /**
     * Refreshes the access token (sessionToken) using the refreshToken.
     * Synchronized method to avoid race conditions.
     */
    public synchronized void refreshSession() throws IOException {
        logger.debug("Refreshing token of the session...");
        RefreshTokenRequest requestBody = new RefreshTokenRequest(this.clientSecret, this.refreshToken);
        String jsonBody = jsonMapper.writeValueAsString(requestBody);

        Request request = new Request.Builder().url(this.baseUrl + "/oauth/token").post(RequestBody.create(jsonBody, MediaType.get("application/json"))).build();

        try (Response response = httpClient.newCall(request).execute())
        {
            String responseBody = Objects.requireNonNull(response.body()).string();
            if (!response.isSuccessful())
            {
                logger.error("Error refreshing session's token. Code: {}.\nBody: {}", response.code(), responseBody);
                throw new IOException("Error refreshing session token: " + response.message());
            }

            RefreshTokenResponse tokenData = jsonMapper.readValue(responseBody, RefreshTokenResponse.class);
            this.sessionToken = tokenData.getAccessToken();
            this.sessionExpiration = ZonedDateTime.now().plusSeconds(tokenData.getExpiresIn());
            logger.info("Session refreshed. The token expires in {} seconds.", tokenData.getExpiresIn());

            // Refreshes the token of the streamer if necessary
            if (this.streamerExpiration.isBefore(this.sessionExpiration))
            {
                refreshStreamerToken();
            }
        } catch (Exception e)
        {
            logger.error("Critical exception refreshing the session.", e);
            if (e instanceof IOException) throw (IOException) e;
            throw new IOException("Error processing refreshing answer", e);
        }
    }

    /**
     * Refreshes the token of the streamer (dxfeed).
     */
    private synchronized void refreshStreamerToken() {
        logger.debug("Refreshing streamer's token (dxfeed)...");
        try
        {
            // This call uses the method 'get' which already includes the session token
            String jsonResponse = get("/api-quote-tokens");
            JsonNode data = jsonMapper.readTree(jsonResponse);

            // The answer JSON data should look like this: {"data": {"token": "...", "dxlink-url": "...", "expires-at": "..."}}
            // User 'parseResponse' to get the node 'data'
            JsonNode tokenData = parseResponse(jsonResponse, JsonNode.class);

            this.streamerToken = tokenData.get("token").asText();
            this.dxlinkUrl = tokenData.get("dxlink-url").asText();
            this.streamerExpiration = ZonedDateTime.parse(tokenData.get("expires-at").asText());
            logger.info("Streamer's Token refreshed.");

        } catch (Exception e)
        {
            logger.error("Failure refreshing streamer's token: {}", e.getMessage(), e);
            // Do not relaunch the exception to not break a call of normal session
        }
    }

    /**
     * Checks if the session is active and refreshes if it is necessary.
     * It is called before any request to the API.
     */
    private synchronized void checkAndRefreshSession() throws IOException {
        if (sessionToken == null || sessionExpiration == null || sessionExpiration.isBefore(ZonedDateTime.now().plusMinutes(1)))
        {
            logger.info("Session expired or about to be. Refreshing...");
            refreshSession();
        }
    }

    // HELPER METHODS (GET, POST, ETC)

    /**
     * Executes a GET request and returns the JSON body as a String.
     */
    public String get(String path) throws IOException {
        checkAndRefreshSession(); // Make sure the session is active
        Request request = new Request.Builder().url(this.baseUrl + path).get().build();

        try (Response response = httpClient.newCall(request).execute())
        {
            return TastytradeUtils.validateAndReturnBody(response);
        }
    }

    public String get(String path, Map<String, String> params) throws IOException {
        checkAndRefreshSession();

        // Build URL with parameters
        HttpUrl.Builder urlBuilder = HttpUrl.parse(this.baseUrl + path).newBuilder();
        if (params != null)
        {
            params.forEach(urlBuilder::addQueryParameter);
        }

        Request request = new Request.Builder().url(urlBuilder.build()).get().build();

        try (Response response = httpClient.newCall(request).execute())
        {
            return TastytradeUtils.validateAndReturnBody(response);
        }
    }

    /**
     * Executes a POST request and returns the JSON body as a String.
     */
    public String post(String path, Object payload) throws IOException {
        checkAndRefreshSession();
        String jsonPayload = (payload instanceof String) ? (String) payload : jsonMapper.writeValueAsString(payload);
        RequestBody body = RequestBody.create(jsonPayload, MediaType.get("application/json"));

        Request request = new Request.Builder().url(this.baseUrl + path).post(body).build();

        try (Response response = httpClient.newCall(request).execute())
        {
            return TastytradeUtils.validateAndReturnBody(response);
        }
    }

    /**
     * Executes a PUT request and returns the JSON body as a String.
     */
    public String put(String path, Object payload) throws IOException {
        checkAndRefreshSession();
        String jsonPayload = (payload instanceof String) ? (String) payload : jsonMapper.writeValueAsString(payload);
        RequestBody body = RequestBody.create(jsonPayload, MediaType.get("application/json"));

        Request request = new Request.Builder().url(this.baseUrl + path).put(body).build();

        try (Response response = httpClient.newCall(request).execute())
        {
            return TastytradeUtils.validateAndReturnBody(response);
        }
    }

    /**
     * Executes a DELETE request and returns the response (can have empty body).
     */
    public void delete(String path) throws IOException {
        checkAndRefreshSession();
        Request request = new Request.Builder().url(this.baseUrl + path).delete().build();

        try (Response response = httpClient.newCall(request).execute())
        {
            // Only validates, does not return body
            if (!response.isSuccessful())
            {
                String errorBody = response.body() != null ? response.body().string() : "Empty body";
                logger.error("Error in DELETE at {}.\nCode: {}.\nBody: {}", path, response.code(), errorBody);
                throw new IOException("DELETE request  failed: " + errorBody);
            }
        }
    }

    public String getVast(String path, Map<String, String> params) throws IOException {
        checkAndRefreshSession(); // Asegura que 'sessionToken' no esté expirado

        HttpUrl.Builder urlBuilder = HttpUrl.parse(VAST_URL + path).newBuilder();
        params.forEach(urlBuilder::addQueryParameter);

        // Builds client HTTP only for this call,
        // because uses different authorization headers.
        Request request = new Request.Builder().url(urlBuilder.build()).header("Authorization", this.sessionToken) // ¡Sin "Bearer "!
                .header("Accept", "application/json").header("Content-Type", "application/json").get().build();

        try (Response response = httpClient.newCall(request).execute())
        {
            return TastytradeUtils.validateAndReturnBody(response);
        }
    }

    public <T> List<T> getPaginatedList(String path, Map<String, String> params, Class<T> itemType) throws IOException {
        // TODO: ... (Yet to be implemented paging logic) ...
        // For no, a placeholder:
        String json = get(path, params); // Need a 'get' that accepts params
        return parseResponse(json, new TypeReference<List<T>>() {
        }); // This is a 'data.items'
    }

    /**
     * Parses the JSON response, searching after 'wrapper' of "data".
     */
    public <T> T parseResponse(String jsonResponse, Class<T> valueType) throws IOException {
        return TastytradeUtils.parseResponse(jsonResponse, valueType, this.jsonMapper);
    }

    public <T> T parseResponse(String jsonResponse, TypeReference<T> valueType) throws IOException {
        return TastytradeUtils.parseResponse(jsonResponse, valueType, this.jsonMapper);
    }

    public <T> List<T> getPaginatedList(String path, Map<String, String> params, TypeReference<T> listTypeRef) throws IOException {
        List<T> allItems = new ArrayList<>();
        Map<String, String> currentParams = (params != null) ? new HashMap<>(params) : new HashMap<>();

        int currentPage = 0;
        // Si el usuario no pide una página, iteramos por todas
        boolean fetchAllPages = !currentParams.containsKey("page-offset");
        currentParams.putIfAbsent("page-offset", "0");

        while (true)
        {
            currentParams.put("page-offset", String.valueOf(currentPage));

            // Llama a nuestro helper GET con parámetros
            String jsonResponse = get(path, currentParams);

            JsonNode root = jsonMapper.readTree(jsonResponse);
            JsonNode itemsNode = root.path("data").path("items");
            if (itemsNode.isMissingNode() || !itemsNode.isArray())
            {
                throw new TastytradeException("Respuesta paginada no contiene 'data.items'");
            }

            // Parsea los items de esta página
            List<T> pageItems = jsonMapper.convertValue(itemsNode, listTypeRef);
            allItems.addAll(pageItems);

            // Decide si continuar
            if (!fetchAllPages)
            {
                break; // El usuario solo quería esta página
            }

            JsonNode paginationNode = root.path("pagination");
            if (paginationNode.isMissingNode())
            {
                break; // No hay info de paginación, terminamos
            }

            int totalPages = paginationNode.path("total-pages").asInt(0);
            if (currentPage >= totalPages - 1)
            {
                break; // Estamos en la última página
            }

            currentPage++;
        }
        return allItems;
    }

    /**
     * Get one account by account number.
     */
    public Account getAccount(String accountNumber) throws IOException {
        String path = String.format("/customers/me/accounts/%s", accountNumber);
        String json = get(path);
        Account account = parseResponse(json, Account.class);

        // ¡LA INYECCIÓN!
        if (account != null)
        {
            account.setClient(this);
        }
        return account;
    }

    /**
     * Get all accounts from the user.
     */
    public List<Account> getAccounts(boolean includeClosed) throws IOException {
        String path = "/customers/me/accounts";
        String json = get(path);

        // The JSON is { "data": { "items": [{"account": {...}}, ...] } }
        // That is a complex structure, so have to parse it manually.
        JsonNode root = jsonMapper.readTree(json);
        JsonNode items = root.path("data").path("items");

        List<Account> accounts = new ArrayList<>();
        for (JsonNode item : items)
        {
            JsonNode accountNode = item.path("account");
            Account account = jsonMapper.treeToValue(accountNode, Account.class);
            if (includeClosed || !account.isClosed())
            {
                // The injection
                account.setClient(this);
                accounts.add(account);
            }
        }
        return accounts;
    }

    public Cryptocurrency getCryptocurrency(String symbol) throws IOException {
        String path = String.format("/instruments/cryptocurrencies/%s", symbol.replace("/", "%2F"));
        String json = get(path);
        return parseResponse(json, Cryptocurrency.class);
    }

    public List<Cryptocurrency> getCryptocurrency(List<String> symbols) throws IOException {
        String path = "/instruments/cryptocurrencies";
        Map<String, String> params = new HashMap<>();
        if (symbols != null && !symbols.isEmpty()) {
            params.put("symbol[]", String.join(",", symbols));
        }
        String json = get(path, params); // Asume que 'get' con params une 'symbol[]'
        return parseResponse(json, new TypeReference<List<Cryptocurrency>>() {});
    }

    public List<Equity> getActiveEquities(Map<String, String> params) throws IOException {
        String path = "/instruments/equities/active";
        return getPaginatedList(path, params, new TypeReference<List<Equity>>() {});
    }

    public Equity getEquity(String symbol) throws IOException {
        String path = String.format("/instruments/equities/%s", symbol.replace("/", "%2F"));
        String json = get(path);
        return parseResponse(json, Equity.class);
    }

    public List<Equity> getEquities(List<String> symbols, Map<String, String> params) throws IOException {
        String path = "/instruments/equities";
        // Prepara los parámetros
        Map<String, String> queryParams = (params != null) ? new HashMap<>(params) : new HashMap<>();
        if (symbols != null && !symbols.isEmpty()) {
            queryParams.put("symbol[]", String.join(",", symbols));
        }
        return getPaginatedList(path, queryParams, new TypeReference<List<Equity>>() {});
    }

    public Option getOption(String symbol) throws IOException {
        String path = String.format("/instruments/equity-options/%s", symbol.replace("/", "%2F"));
        String json = get(path);
        return parseResponse(json, Option.class);
    }

    public List<Option> getOptions(List<String> symbols, Map<String, String> params) throws IOException {
        String path = "/instruments/equity-options";
        Map<String, String> queryParams = (params != null) ? new HashMap<>(params) : new HashMap<>();
        if (symbols != null && !symbols.isEmpty()) {
            queryParams.put("symbol[]", String.join(",", symbols));
        }
        return getPaginatedList(path, queryParams, new TypeReference<List<Option>>() {});
    }

    public List<NestedOptionChain> getNestedOptionChain(String symbol) throws IOException {
        String path = String.format("/option-chains/%s/nested", symbol.replace("/", "%2F"));
        String json = get(path);
        return parseResponse(json, new TypeReference<List<NestedOptionChain>>() {});
    }

    public List<FutureProduct> getFutureProducts() throws IOException {
        String path = "/instruments/future-products";
        String json = get(path);
        return parseResponse(json, new TypeReference<List<FutureProduct>>() {});
    }

    public FutureProduct getFutureProduct(String code, String exchange) throws IOException {
        String path = String.format("/instruments/future-products/%s/%s",
                exchange.replace("/", ""),
                code.replace("/", "")
        );
        String json = get(path);
        return parseResponse(json, FutureProduct.class);
    }

    public NestedFutureOptionChain getNestedFutureOptionChain(String symbol) throws IOException {
        String path = String.format("/futures-option-chains/%s/nested", symbol.replace("/", ""));
        String json = get(path);
        return parseResponse(json, NestedFutureOptionChain.class);
    }

    public List<QuantityDecimalPrecision> getQuantityDecimalPrecisions() throws IOException {
        String path = "/instruments/quantity-decimal-precisions";
        String json = get(path);
        return parseResponse(json, new TypeReference<List<QuantityDecimalPrecision>>() {});
    }

    public Map<LocalDate, List<Option>> getOptionChain(String symbol) throws IOException {
        String path = String.format("/option-chains/%s", symbol.replace("/", "%2F"));
        String json = get(path);

        // Parsea la lista de 'items'
        List<Option> allOptions = parseResponse(json, new TypeReference<List<Option>>() {});

        // Agrupa la lista en un Mapa (traducción de defaultdict(list))
        return allOptions.stream()
                .collect(Collectors.groupingBy(Option::getExpirationDate));
    }

    public Map<LocalDate, List<FutureOption>> getFutureOptionChain(String symbol) throws IOException {
        String path = String.format("/futures-option-chains/%s", symbol.replace("/", ""));
        String json = get(path);

        List<FutureOption> allOptions = parseResponse(json, new TypeReference<List<FutureOption>>() {});

        // Agrupa la lista en un Mapa
        return allOptions.stream()
                .collect(Collectors.groupingBy(FutureOption::getExpirationDate));
    }

    /**
     * Obtiene un único instrumento Future por su símbolo.
     * Traducción de 'Future.get(session, "symbol")'
     *
     * @param symbol El símbolo del futuro (ej. "/ESZ9")
     * @return El objeto Future.
     * @throws IOException Si la petición falla.
     */
    public Future getFuture(String symbol) throws IOException {
        // El SDK de Python elimina la barra, así que lo imitamos.
        String path = String.format("/instruments/futures/%s", symbol.replace("/", ""));
        String json = get(path);
        return parseResponse(json, Future.class);
    }

    /**
     * Obtiene una lista de instrumentos Future (paginada).
     * Traducción de 'Future.get(session, ["..."], ...)'
     *
     * @param params Un mapa de parámetros (ej. "symbol[]", "product-code[]", "page-offset").
     * @return Una lista de objetos Future.
     * @throws IOException Si la petición falla.
     */
    public List<Future> getFutures(Map<String, String> params) throws IOException {
        String path = "/instruments/futures";
        return getPaginatedList(path, params, new TypeReference<List<Future>>() {});
    }

    /**
     * Obtiene todos los productos de opciones sobre futuros.
     * Traducción de 'FutureOptionProduct.get(session)'
     *
     * @return Una lista de productos de opciones sobre futuros.
     * @throws IOException Si la petición falla.
     */
    public List<FutureOptionProduct> getFutureOptionProducts() throws IOException {
        String path = "/instruments/future-option-products";
        String json = get(path); // No es paginado, pero devuelve 'items'
        return parseResponse(json, new TypeReference<List<FutureOptionProduct>>() {});
    }

    /**
     * Obtiene un único producto de opción sobre futuro.
     * Traducción de 'FutureOptionProduct.get(session, "root_symbol", "CME")'
     *
     * @param rootSymbol El símbolo raíz del producto (ej. "EW3").
     * @param exchange El exchange (ej. "CME").
     * @return El objeto FutureOptionProduct.
     * @throws IOException Si la petición falla.
     */
    public FutureOptionProduct getFutureOptionProduct(String rootSymbol, String exchange) throws IOException {
        String ex = (exchange != null && !exchange.isEmpty()) ? exchange : "CME";
        String path = String.format("/instruments/future-option-products/%s/%s",
                ex.replace("/", ""),
                rootSymbol.replace("/", "")
        );
        String json = get(path);
        return parseResponse(json, FutureOptionProduct.class);
    }

    /**
     * Obtiene una única opción sobre futuro por su símbolo.
     * Traducción de 'FutureOption.get(session, "symbol")'
     *
     * @param symbol El símbolo de la opción sobre futuro.
     * @return El objeto FutureOption.
     * @throws IOException Si la petición falla.
     */
    public FutureOption getFutureOption(String symbol) throws IOException {
        // El SDK de Python codifica manualmente el símbolo aquí.
        String encodedSymbol = symbol.replace("/", "%2F").replace(" ", "%20");
        String path = String.format("/instruments/future-options/%s", encodedSymbol);
        String json = get(path);
        return parseResponse(json, FutureOption.class);
    }

    /**
     * Obtiene una lista de opciones sobre futuros (paginada).
     * Traducción de 'FutureOption.get(session, ["..."], ...)'
     *
     * @param params Un mapa de parámetros (ej. "symbol[]", "root-symbol", "expiration-date").
     * @return Una lista de objetos FutureOption.
     * @throws IOException Si la petición falla.
     */
    public List<FutureOption> getFutureOptions(Map<String, String> params) throws IOException {
        String path = "/instruments/future-options";
        return getPaginatedList(path, params, new TypeReference<List<FutureOption>>() {});
    }

    /**
     * Obtiene un único Warrant por su símbolo.
     * Traducción de 'Warrant.get(session, "symbol")'
     *
     * @param symbol El símbolo del warrant.
     * @return El objeto Warrant.
     * @throws IOException Si la petición falla.
     */
    public Warrant getWarrant(String symbol) throws IOException {
        String path = String.format("/instruments/warrants/%s", symbol);
        String json = get(path);
        return parseResponse(json, Warrant.class);
    }

    /**
     * Obtiene una lista de Warrants por símbolos.
     * Traducción de 'Warrant.get(session, ["..."])'
     *
     * @param symbols Una lista de símbolos de warrants.
     * @return Una lista de objetos Warrant.
     * @throws IOException Si la petición falla.
     */
    public List<Warrant> getWarrants(List<String> symbols) throws IOException {
        String path = "/instruments/warrants";
        Map<String, String> params = new HashMap<>();
        if (symbols != null && !symbols.isEmpty()) {
            // El SDK de Python une con 'join', pero la API espera 'symbol[]'
            // Asumiremos que nuestro 'get' con params maneja la lista.
            params.put("symbol[]", String.join(",", symbols));
        }

        // Nota: Este endpoint no es paginado, pero devuelve 'items'.
        String json = get(path, params);
        return parseResponse(json, new TypeReference<List<Warrant>>() {});
    }

    // INTERN HELPER CLASSES

    /**
     * Intern class to create the JSON for the refresh request
     */
    @Data
    private static class RefreshTokenRequest {
        @JsonProperty("grant_type")
        private final String grantType = "refresh_token";
        @JsonProperty("client_secret")
        private final String clientSecret;
        @JsonProperty("refresh_token")
        private final String refreshToken;
    }

    /**
     * Intern class to parse the JSON response of the refresh
     */
    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class RefreshTokenResponse {
        @JsonProperty("access_token")
        private String accessToken;
        @JsonProperty("expires_in")
        private long expiresIn;
    }

    /**
     * Intern class to save the state of the client for serialization
     */
    @Data
    @NoArgsConstructor
    private static class SessionState {
        private String clientSecret;
        private String refreshToken;
        private boolean isTest;
        private String sessionToken;
        private ZonedDateTime sessionExpiration;
        private String streamerToken;
        private ZonedDateTime streamerExpiration;
        private String dxlinkUrl;
    }
}