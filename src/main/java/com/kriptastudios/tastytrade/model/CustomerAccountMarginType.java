package com.kriptastudios.tastytrade.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CustomerAccountMarginType extends TastytradeData {
    // Dataclass containing margin information for a customer account type

    private String name;
    private boolean isMargin;

}
