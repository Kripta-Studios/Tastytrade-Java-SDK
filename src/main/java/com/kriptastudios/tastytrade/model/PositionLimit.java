package com.kriptastudios.tastytrade.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PositionLimit extends TastytradeData {

    private String accountNumber;
    private int equityOrderSize;
    private int equityOptionOrderSize;
    private int futureOrderSize;
    private int futureOptionOrderSize;
    private int underlyingOpeningOrderLimit;
    private int equityPositionSize;
    private int equityOptionPositionSize;
    private int futurePositionSize;
    private int futureOptionPositionSize;
}