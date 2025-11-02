package com.kriptastudios.tastytrade.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class EntitySuitability extends TastytradeData {
    // Dataclass containing entity suitability information

    private String id;
    private int annualNetIncome;
    private String coveredOptionsTradingExperience;
    private int entityId;
    private String futuresTradingExperience;
    private int liquidNetWorth;
    private int netWorth;
    private String stockTradingExperience;
    private String taxBracket;
    private String uncoveredOptionsTradingExperience;

}
