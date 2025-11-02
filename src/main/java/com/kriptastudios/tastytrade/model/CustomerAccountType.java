package com.kriptastudios.tastytrade.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CustomerAccountType extends TastytradeData {
    // Dataclass containing information for a type of customer account

    private String name;
    private String description;
    private boolean isTaxAdvantaged;
    private boolean isPubliclyAvailable;
    private boolean hasMultipleOwners;
    private List<CustomerAccountMarginType> marginTypes;

}
