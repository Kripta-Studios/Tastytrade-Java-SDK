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
public class Lot extends TastytradeData {

    private String id;
    private int transactionId;
    private BigDecimal quantity;
    private BigDecimal price;
    private String quantityDirection;
    private ZonedDateTime executedAt;
    private LocalDate transactionDate;
}