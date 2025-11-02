package com.kriptastudios.tastytrade.order;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OrderStatus {
    @JsonProperty("Received")
    RECEIVED,
    @JsonProperty("Cancelled")
    CANCELLED,
    @JsonProperty("Filled")
    FILLED,
    @JsonProperty("Expired")
    EXPIRED,
    @JsonProperty("Live")
    LIVE,
    @JsonProperty("Rejected")
    REJECTED,
    @JsonProperty("Contingent")
    CONTINGENT,
    @JsonProperty("Routed")
    ROUTED,
    @JsonProperty("In Flight")
    IN_FLIGHT,
    @JsonProperty("Cancel Requested")
    CANCEL_REQUESTED,
    @JsonProperty("Replace Requested")
    REPLACE_REQUESTED,
    @JsonProperty("Removed")
    REMOVED,
    @JsonProperty("Partially Removed")
    PARTIALLY_REMOVED,
    UNKNOWN;
}