package com.kriptastudios.tastytrade.order;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OrderTimeInForce {
    @JsonProperty("Day")
    DAY,
    @JsonProperty("GTC")
    GTC,
    @JsonProperty("GTD")
    GTD,
    @JsonProperty("Ext")
    EXT,
    @JsonProperty("GTC Ext")
    GTC_EXT,
    @JsonProperty("IOC")
    IOC,
    UNKNOWN;
}