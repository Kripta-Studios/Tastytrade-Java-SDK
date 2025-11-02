package com.kriptastudios.tastytrade.instrument;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OptionType {
    @JsonProperty("C")
    CALL,
    @JsonProperty("P")
    PUT,
    UNKNOWN;
}