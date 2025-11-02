package com.kriptastudios.tastytrade.order;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum InstrumentType {
    @JsonProperty("Bond")
    BOND,
    @JsonProperty("Cryptocurrency")
    CRYPTOCURRENCY,
    @JsonProperty("Currency Pair")
    CURRENCY_PAIR,
    @JsonProperty("Equity")
    EQUITY,
    @JsonProperty("Equity Offering")
    EQUITY_OFFERING,
    @JsonProperty("Equity Option")
    EQUITY_OPTION,
    @JsonProperty("Fixed Income Security")
    FIXED_INCOME,
    @JsonProperty("Future")
    FUTURE,
    @JsonProperty("Future Option")
    FUTURE_OPTION,
    @JsonProperty("Index")
    INDEX,
    @JsonProperty("Liquidity Pool")
    LIQUIDITY_POOL,
    @JsonProperty("Unknown")
    UNKNOWN,
    @JsonProperty("Warrant")
    WARRANT;
}