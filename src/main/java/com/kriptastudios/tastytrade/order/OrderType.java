package com.kriptastudios.tastytrade.order;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OrderType {
    @JsonProperty("Limit")
    LIMIT,
    @JsonProperty("Market")
    MARKET,
    @JsonProperty("Marketable Limit")
    MARKETABLE_LIMIT,
    @JsonProperty("Stop")
    STOP,
    @JsonProperty("Stop Limit")
    STOP_LIMIT,
    @JsonProperty("Notional Market")
    NOTIONAL_MARKET,
    UNKNOWN;
}