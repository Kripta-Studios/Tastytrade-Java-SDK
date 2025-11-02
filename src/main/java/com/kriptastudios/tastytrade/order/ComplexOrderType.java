package com.kriptastudios.tastytrade.order;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ComplexOrderType {
    @JsonProperty("OCO")
    OCO,
    @JsonProperty("OTOCO")
    OTOCO,
    UNKNOWN;
}