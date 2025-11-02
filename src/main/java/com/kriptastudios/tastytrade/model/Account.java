package com.kriptastudios.tastytrade.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kriptastudios.tastytrade.TastytradeClient;
import com.kriptastudios.tastytrade.TastytradeUtils;
import com.kriptastudios.tastytrade.order.*; // Importa todo el paquete de órdenes
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Account extends TastytradeData {
    // Dataclass that represents a Tastytrade account object, containing
    // methods for retrieving information about the account, placing orders,
    // and retrieving past transactions.
    //  (mapped from JSON)
    private String accountNumber;
    private ZonedDateTime openedAt;
    private String nickname;
    private String accountTypeName;
    private boolean isClosed;
    private String dayTraderStatus;
    private boolean isFirmError;
    private boolean isFirmProprietary;
    private boolean isFuturesApproved;
    private boolean isTestDrive;
    private String marginOrCash;
    private boolean isForeign;
    private ZonedDateTime createdAt;
    private String externalId;
    private String closedAt;
    private LocalDate fundingDate;
    private String investmentObjective;
    private String liquidityNeeds;
    private String riskTolerance;
    private String investmentTimeHorizon;
    private String futuresAccountPurpose;
    private String externalFdid;
    private String suitableOptionsLevel;
    private String submittingUserId;

    // Logic of the client (dependency injection) ---

    /**
     * The client that created this object.
     * 'transient' = Jackson and other serializers ignore it.
     * 'Setter' = gives Lombok a setter for the client to inject itself.
     */
    @Setter
    private transient TastytradeClient client;

    /**
     * Check if the client was injected before using it.
     */
    private void checkClient() throws IOException {
        if (client == null)
        {
            throw new IOException("This Account object it is not vinculated with a TastytradeClient. " + "Get it using client.getAccounts()");
        }
    }

    // PUBLIC METHODS
    // Note: no need to pass the 'session' (client)

    /**
     * Gets the state of trading in this account.
     */
    public TradingStatus getTradingStatus() throws IOException {
        checkClient();
        String path = String.format("/accounts/%s/trading-status", this.accountNumber);
        String json = client.get(path);
        return client.parseResponse(json, TradingStatus.class);
    }

    /**
     * Get current balance in the account.
     */
    public AccountBalance getBalances() throws IOException {
        checkClient();
        String path = String.format("/accounts/%s/balances/USD", this.accountNumber); // Asume USD
        String json = client.get(path);
        return client.parseResponse(json, AccountBalance.class);
    }

    /**
     * Get current positions in the account.
     */
    public List<CurrentPosition> getPositions() throws IOException {
        checkClient();
        String path = String.format("/accounts/%s/positions", this.accountNumber);
        // getPaginatedList is a method yet to be created in TastytradeClient
        return client.getPaginatedList(path, new HashMap<>(), // Wthout parameters
                CurrentPosition.class);
    }

    /**
     * Get history of transactions of this account.
     */
    public List<Transaction> getHistory() throws IOException {
        checkClient();
        String path = String.format("/accounts/%s/transactions", this.accountNumber);
        Map<String, String> params = new HashMap<>();
        params.put("sort", "Desc"); // Default value

        // Need that getPaginatedList handle TypeReference for Lists
        // Or modify getPaginatedList to accept TypeReference
        // For now, assume that getPaginatedList can handle this
        return client.getPaginatedList(path, params, Transaction.class);
    }

    /**
     * Get current live orders in the account.
     */
    public List<PlacedOrder> getLiveOrders() throws IOException {
        checkClient();
        String path = String.format("/accounts/%s/orders/live", this.accountNumber);
        String json = client.get(path);
        // This endpoint returns { "data": { "items": [...] } }
        return client.parseResponse(json, new TypeReference<List<PlacedOrder>>() {
        });
    }

    /**
     * Get all active complex orders of the day.
     */
    public List<PlacedComplexOrder> getLiveComplexOrders() throws IOException {
        checkClient();
        String path = String.format("/accounts/%s/complex-orders/live", this.accountNumber);
        String json = client.get(path);
        return client.parseResponse(json, new TypeReference<List<PlacedComplexOrder>>() {});
    }

    /**
     * Get a complex order specifically by ID.
     */
    public PlacedComplexOrder getComplexOrder(int orderId) throws IOException {
        checkClient();
        String path = String.format("/accounts/%s/complex-orders/%d", this.accountNumber, orderId);
        String json = client.get(path);
        return client.parseResponse(json, PlacedComplexOrder.class);
    }

    /**
     * Get a unique simple order specifically by ID.
     */
    public PlacedOrder getOrder(int orderId) throws IOException {
        checkClient();
        String path = String.format("/accounts/%s/orders/%d", this.accountNumber, orderId);
        String json = client.get(path);
        return client.parseResponse(json, PlacedOrder.class);
    }

    /**
     * Cancels (eliminates) a complex order by ID.
     */
    public void deleteComplexOrder(int orderId) throws IOException {
        checkClient();
        String path = String.format("/accounts/%s/complex-orders/%d", this.accountNumber, orderId);
        client.delete(path);
    }

    /**
     * Cancels (eliminate) a simple order by ID.
     */
    public void deleteOrder(int orderId) throws IOException {
        checkClient();
        String path = String.format("/accounts/%s/orders/%d", this.accountNumber, orderId);
        client.delete(path);
    }

    /**
     * Sets a new simple order.
     * @param order     The object NewOrder that defines the order.
     * @param dryRun    If it is true, validates the order but does not send it.
     * @return          A response with the order set.
     */
    public PlacedOrderResponse placeOrder(NewOrder order, boolean dryRun) throws IOException {
        checkClient();
        String path = String.format("/accounts/%s/orders%s",
                this.accountNumber,
                dryRun ? "/dry-run" : ""
        );
        String json = client.post(path, order); // 'order' will be serialized by Jackson
        return client.parseResponse(json, PlacedOrderResponse.class);
    }

    /**
     * Sets a new complex order (OCO/OTOCO).
     */
    public PlacedComplexOrderResponse placeComplexOrder(NewComplexOrder order, boolean dryRun) throws IOException {
        checkClient();
        String path = String.format("/accounts/%s/complex-orders%s",
                this.accountNumber,
                dryRun ? "/dry-run" : ""
        );
        String json = client.post(path, order);
        return client.parseResponse(json, PlacedComplexOrderResponse.class);
    }

    /**
     * Replace an existing order with a new definition.
     */
    public PlacedOrder replaceOrder(int oldOrderId, NewOrder newOrder) throws IOException {
        checkClient();
        String path = String.format("/accounts/%s/orders/%d", this.accountNumber, oldOrderId);
        // El SDK de Python excluye las 'legs', pero nuestro 'NewOrder' no las tiene,
        // así que podemos enviar el objeto 'newOrder' completo.
        // Si 'NewOrder' tuviera 'legs', tendríamos que enviar un Map filtrado.
        String json = client.put(path, newOrder);
        return client.parseResponse(json, PlacedOrder.class);
    }

    // --- Métodos de Historial (Paginados) ---

    /**
     * Get history of simple orders.
     * @param params Mapa of parameters (ex. "start-date", "status[]").
     * @return A list of all the orders on every page.
     */
    public List<PlacedOrder> getOrderHistory(Map<String, String> params) throws IOException {
        checkClient();
        String path = String.format("/accounts/%s/orders", this.accountNumber);

        // El SDK de Python convierte la lista de Enums a valores de String
        // Asumimos que el usuario de Java hará esto antes de llamar al método.
        // ex: params.put("status[]", "Filled");

        return client.getPaginatedList(path, params, new TypeReference<List<PlacedOrder>>() {});
    }

    /**
     * Get history of complex orders.
     */
    public List<PlacedComplexOrder> getComplexOrderHistory(Map<String, String> params) throws IOException {
        checkClient();
        String path = String.format("/accounts/%s/complex-orders", this.accountNumber);
        return client.getPaginatedList(path, params, new TypeReference<List<PlacedComplexOrder>>() {});
    }

    /**
     * Get chain of orders (Vast).
     */
    public List<OrderChain> getOrderChains(String symbol, ZonedDateTime startTime, ZonedDateTime endTime) throws IOException {
        checkClient();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

        Map<String, String> params = new HashMap<>();
        params.put("account-numbers[]", this.accountNumber);
        params.put("underlying-symbols[]", symbol);
        params.put("start-at", startTime.format(formatter));
        params.put("end-at", endTime.format(formatter));
        params.put("per-page", "250"); // Valor por defecto del SDK de Python

        String json = client.getVast("/order-chains", params);
        // This endpoint returns { "data": { "items": [...] } }
        return client.parseResponse(json, new TypeReference<List<OrderChain>>() {});
    }

    /**
     * Get historic snapshots of the balance in the account.
     *
     * @param timeOfDay "BOD" (Beginning Of Day) or"EOD" (End Of Day).
     * @param startDate (optional).
     * @param endDate   (opcional).
     * @return list of balance snapshots.
     */
    public List<AccountBalanceSnapshot> getBalanceSnapshots(String timeOfDay, LocalDate startDate, LocalDate endDate) throws IOException {
        checkClient();
        String path = String.format("/accounts/%s/balance-snapshots", this.accountNumber);

        Map<String, String> params = new HashMap<>();
        params.put("time-of-day", timeOfDay != null ? timeOfDay : "EOD");
        if (startDate != null)
        {
            params.put("start-date", startDate.toString());
        }
        if (endDate != null)
        {
            params.put("end-date", endDate.toString());
        }

        // Assuming that getPaginatedList accepts TypeReference
        return client.getPaginatedList(path, params, new TypeReference<List<AccountBalanceSnapshot>>() {
        });
    }

    /**
     * Get a unique transaction by ID.
     */
    public Transaction getTransaction(int transactionId) throws IOException {
        checkClient();
        String path = String.format("/accounts/%s/transactions/%d", this.accountNumber, transactionId);
        String json = client.get(path);
        return client.parseResponse(json, Transaction.class);
    }

    /**
     * Get total of commissions for any given date.
     */
    public FeesInfo getTotalFees(LocalDate date) throws IOException {
        checkClient();
        LocalDate queryDate = (date != null) ? date : TastytradeUtils.todayInNewYork();
        String path = String.format("/accounts/%s/transactions/total-fees", this.accountNumber);

        Map<String, String> params = new HashMap<>();
        params.put("date", queryDate.toString());

        String json = client.get(path, params); // Needs a client.get() that accepts params
        return client.parseResponse(json, FeesInfo.class);
    }

    /**
     * Get the historic net value of liquidation (Net Liq).
     */
    public List<NetLiqOhlc> getNetLiquidatingValueHistory(String timeBack, ZonedDateTime startTime) throws IOException {
        checkClient();
        String path = String.format("/accounts/%s/net-liq/history", this.accountNumber);

        Map<String, String> params = new HashMap<>();
        if (startTime != null)
        {
            // Format to "2025-01-01T15:30:00Z"
            params.put("start-time", startTime.format(DateTimeFormatter.ISO_INSTANT));
        } else if (timeBack != null)
        {
            params.put("time-back", timeBack);
        } else
        {
            throw new IllegalArgumentException("Must specify 'timeBack' or 'startTime'.");
        }

        String json = client.get(path, params); // get with params
        // This endpoint returns { "data": { "items": [...] } }
        return client.parseResponse(json, new TypeReference<List<NetLiqOhlc>>() {
        });
    }

    /**
     * Get the limits of position of the account.
     */
    public PositionLimit getPositionLimit() throws IOException {
        checkClient();
        String path = String.format("/accounts/%s/position-limit", this.accountNumber);
        String json = client.get(path);
        return client.parseResponse(json, PositionLimit.class);
    }

    /**
     * Get the effective margin requirements for a symbol.
     */
    public MarginRequirement getEffectiveMarginRequirements(String symbol) throws IOException {
        checkClient();
        String path = String.format("/accounts/%s/margin-requirements/%s/effective", this.accountNumber, symbol);
        String json = client.get(path);
        return client.parseResponse(json, MarginRequirement.class);
    }

    /**
     * Get a complete inform of margin requirements.
     */
    public MarginReport getMarginRequirements() throws IOException {
        checkClient();
        String path = String.format("/margin/accounts/%s/requirements", this.accountNumber);
        String json = client.get(path);
        return client.parseResponse(json, MarginReport.class);
    }
}