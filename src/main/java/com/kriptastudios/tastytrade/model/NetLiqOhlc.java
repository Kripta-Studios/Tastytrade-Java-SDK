package com.kriptastudios.tastytrade.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class NetLiqOhlc extends TastytradeData {

    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;
    private BigDecimal pendingCashOpen;
    private BigDecimal pendingCashHigh;
    private BigDecimal pendingCashLow;
    private BigDecimal pendingCashClose;
    private BigDecimal totalOpen;
    private BigDecimal totalHigh;
    private BigDecimal totalLow;
    private BigDecimal totalClose;
    private String time;
}