package com.kriptastudios.tastytrade.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Address extends TastytradeData {
    // Dataclass containing customer address information.

    private String city;
    private String country;
    private boolean isDomestic;
    private boolean isForeign;
    private String postalCode;
    private String stateRegion;
    private String streetOne;
    private String streetTwo; // In Java, String objects can be null by default
    private String streetThree;

}