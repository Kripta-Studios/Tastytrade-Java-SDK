package com.kriptastudios.tastytrade.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TradingStatus extends TastytradeData {

    private String accountNumber;
    private String equitiesMarginCalculationType;
    private String feeScheduleName;
    private BigDecimal futuresMarginRateMultiplier;
    private boolean hasIntradayEquitiesMargin;
    private int id;
    private boolean isAggregatedAtClearing;
    private boolean isClosed;
    private boolean isClosingOnly;
    private boolean isCryptocurrencyEnabled;
    private boolean isFrozen;
    private boolean isFullEquityMarginRequired;
    private boolean isFuturesClosingOnly;
    private boolean isFuturesIntraDayEnabled;
    private boolean isFuturesEnabled;
    private boolean isInDayTradeEquityMaintenanceCall;
    private boolean isInMarginCall;
    private boolean isPatternDayTrader;
    private boolean isSmallNotionalFuturesIntraDayEnabled;
    private boolean isRollTheDayForwardEnabled;
    private boolean areFarOtmNetOptionsRestricted;
    private String optionsLevel;
    private boolean shortCallsEnabled;
    private BigDecimal smallNotionalFuturesMarginRateMultiplier;
    private boolean isEquityOfferingEnabled;
    private boolean isEquityOfferingClosingOnly;
    private ZonedDateTime updatedAt;

    // Optional fields (nullable)
    private Boolean isPortfolioMarginEnabled;
    private Boolean isRiskReducingOnly;
    private Integer dayTradeCount;
    private String autotradeAccountType;
    private String clearingAccountNumber;
    private String clearingAggregationIdentifier;
    private Boolean isCryptocurrencyClosingOnly;
    private LocalDate pdtResetOn;
    private Integer cmtaOverride;
    private ZonedDateTime enhancedFraudSafeguardsEnabledAt;
}