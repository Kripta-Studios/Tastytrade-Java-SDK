package com.kriptastudios.tastytrade.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MarginRequirement extends TastytradeData {

    private String underlyingSymbol;
    private BigDecimal longEquityInitial;
    private BigDecimal shortEquityInitial;
    private BigDecimal longEquityMaintenance;
    private BigDecimal shortEquityMaintenance;
    private BigDecimal nakedOptionStandard;
    private BigDecimal nakedOptionMinimum;
    private BigDecimal nakedOptionFloor;
    private String clearingIdentifier;
    private Boolean isDeleted;
}