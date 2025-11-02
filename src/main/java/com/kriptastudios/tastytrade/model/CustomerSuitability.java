package com.kriptastudios.tastytrade.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CustomerSuitability extends TastytradeData {
    // Dataclass containing customer suitability information

    private int id;
    private int annualNetIncome;
    private String coveredOptionsTradingExperience;
    private String employmentStatus;
    private String futuresTradingExperience;
    private int liquidNetWorth;
    private String maritalStatus;
    private int netWorth;
    private int numberOfDependents;
    private String stockTradingExperience;
    private String uncoveredOptionsTradingExperience;
    private String customerId;
    private String employerName;
    private String jobTitle;
    private String occupation;
    private String taxBracket;

}
