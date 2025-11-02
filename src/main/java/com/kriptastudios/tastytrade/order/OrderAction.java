package com.kriptastudios.tastytrade.order;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OrderAction {
    @JsonProperty("Buy to Open")
    BUY_TO_OPEN,
    @JsonProperty("Buy to Close")
    BUY_TO_CLOSE,
    @JsonProperty("Sell to Open")
    SELL_TO_OPEN,
    @JsonProperty("Sell to Close")
    SELL_TO_CLOSE,
    @JsonProperty("Buy")
    BUY,
    @JsonProperty("Sell")
    SELL,
    UNKNOWN; // Valor por defecto
}