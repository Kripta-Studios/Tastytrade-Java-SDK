package com.kriptastudios.tastytrade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Objects;

/**
 * Class for static utilities for Tastytrade SDK.
 */
public final class TastytradeUtils {

    private static final Logger logger = LoggerFactory.getLogger(TastytradeUtils.class);

    // Equivalent to TZ = ZoneInfo("US/Eastern")
    // "America/New_York" is the IANA identifier standard and safest.
    public static final ZoneId TZ_NEW_YORK = ZoneId.of("America/New_York");

    // Private constructor to avoid class instantiation
    private TastytradeUtils() {
    }
    // Personalized exception (Translate of TastytradeError)
    // Extends 'IOException' to be compatible with our client.
    public static class TastytradeException extends IOException {
        public TastytradeException(String message) {
            super(message);
        }

        public TastytradeException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    // Enum of effect of prices
    public enum PriceEffect {
        CREDIT("Credit"),
        DEBIT("Debit"),
        NONE("None");

        private final String value;

        PriceEffect(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    // Helpers of validation
    /**
     * Validates the HTTP response and returns the body if it is successful.
     */
    public static String validateAndReturnBody(Response response) throws IOException {
        String body = response.body() != null ? response.body().string() : null;
        if (response.isSuccessful()) {
            if (body == null) {
                throw new TastytradeException("Response successfully but with empty body.");
            }
            return body;
        }

        // If it was not successful, parsing the error
        String errorMessage = "Request failed (Code " + response.code() + "): ";
        if (body == null || body.isEmpty()) {
            throw new TastytradeException(errorMessage + response.message());
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode errorNode = mapper.readTree(body);
            JsonNode error = errorNode.get("error");
            if (error != null) {
                JsonNode errors = error.get("errors");
                if (errors != null && errors.isArray() && !errors.isEmpty()) {
                    // Múltiples errores
                    StringBuilder messages = new StringBuilder();
                    for (JsonNode err : errors) {
                        messages.append(err.get("message").asText("Unknown Error")).append("\n");
                    }
                    errorMessage += messages.toString();
                } else {
                    // Un solo error
                    errorMessage += error.get("message").asText(body);
                }
            } else {
                errorMessage += body;
            }
        } catch (JsonProcessingException e) {
            logger.warn("Could not parse the JSON body: {}", body);
            errorMessage += body;
        }
        throw new TastytradeException(errorMessage);
    }

    /**
     * Parses the JSON response, searching for 'wrapper' of "data".
     */
    public static <T> T parseResponse(String jsonResponse, Class<T> valueType, ObjectMapper mapper) throws IOException {
        try {
            JsonNode root = mapper.readTree(jsonResponse);
            JsonNode dataNode = root.get("data");

            if (dataNode != null) {
                // Check if there is a 'wrapper' of "items" for paging
                if (dataNode.has("items")) {
                    // The caller is expecting a list, so maps the items
                    return mapper.treeToValue(dataNode.get("items"), valueType);
                }
                // If there is not 'items', it is a simple 'wrapper' of  "data"
                logger.debug("Response wrapped in 'data' detected. Extracting...");
                return mapper.treeToValue(dataNode, valueType);
            } else {
                // Assume that the response is the root object
                logger.debug("Root response. Mapping directly.");
                return mapper.treeToValue(root, valueType);
            }
        } catch (JsonProcessingException e) {
            logger.error("Error parsing the JSON: {}", jsonResponse, e);
            throw new TastytradeException("Failure parsing API's response.", e);
        }
    }

    // Helpers for the logic of business

    public static PriceEffect getSign(BigDecimal value) {
        if (value == null) {
            return PriceEffect.NONE;
        }
        // int compare = value.compareTo(BigDecimal.ZERO);
        if (value.signum() < 0) {
            return PriceEffect.DEBIT;
        } else {
            return PriceEffect.CREDIT;
        }
    }

    // Nota: 'set_sign_for' es más complejo.
    // En Java, esto se maneja mejor con un Deserializador personalizado
    // de Jackson en la propia clase del Modelo, no como una utilidad genérica.
    // Por ahora, lo omitimos hasta que necesitemos esa clase de modelo específica.


    // Helpers for Date and Time

    public static ZonedDateTime nowInNewYork() {
        return ZonedDateTime.now(TZ_NEW_YORK);
    }

    public static LocalDate todayInNewYork() {
        return nowInNewYork().toLocalDate();
    }

    public static LocalDate getThirdFriday(LocalDate day) {
        LocalDate firstDayOfMonth = (day != null ? day : todayInNewYork()).withDayOfMonth(1);

        LocalDate firstFriday = firstDayOfMonth.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));

        return firstFriday.plusWeeks(2);
    }

    // STUBS de Calendario de Mercado

    // NOTA: Todos los métodos siguientes dependen de 'pandas_market_calendars'.
    // Necesitan ser implementados si encuentras una biblioteca de calendario de Java.

    private static void throwCalendarWarning() {
        throw new UnsupportedOperationException(
                "Logic of market calendar not implemented. " +
                        "It requires a lib for financial market calendars (ex. NYSE) in Java."
        );
    }

    public static boolean isMarketOpenOn(LocalDate day) {
        logger.warn("Calling stub method 'isMarketOpenOn'");
        throwCalendarWarning();
        return false; // Nunca se alcanza
    }

    public static LocalDate getFutureFxMonthly(LocalDate day) {
        logger.warn("Calling stub method 'getFutureFxMonthly'");
        throwCalendarWarning();
        return null; // Nunca se alcanza
    }

    public static LocalDate getFutureTreasuryMonthly(LocalDate day) {
        logger.warn("Calling stub method 'getFutureTreasuryMonthly'");
        throwCalendarWarning();
        return null; // Nunca se alcanza
    }

    // Helpers to Paginate
    // Este método es complejo y requerirá una refactorización de cómo
    // se construyen las URLs en TastytradeClient.
    // Por ahora, lo dejaremos fuera hasta que un endpoint REAL lo necesite.
    // La lógica de 'parseResponse' ya maneja la extracción de "items".
}