package com.kriptastudios.tastytrade.instrument;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum FutureMonthCode {
    @JsonProperty("F") JAN,
    @JsonProperty("G") FEB,
    @JsonProperty("H") MAR,
    @JsonProperty("J") APR,
    @JsonProperty("K") MAY,
    @JsonProperty("M") JUN,
    @JsonProperty("N") JUL,
    @JsonProperty("Q") AUG,
    @JsonProperty("U") SEP,
    @JsonProperty("V") OCT,
    @JsonProperty("X") NOV,
    @JsonProperty("Z") DEC,
    UNKNOWN;
}