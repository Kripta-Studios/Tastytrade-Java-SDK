package com.kriptastudios.tastytrade.model;

import com.kriptastudios.tastytrade.order.InstrumentType;
import com.kriptastudios.tastytrade.TastytradeUtils.PriceEffect;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CurrentPosition extends TastytradeData {

    private String accountNumber;
    private String symbol;
    private InstrumentType instrumentType; // Dependency of 'order.py'
    private String underlyingSymbol;
    private BigDecimal quantity;
    private String quantityDirection;
    private BigDecimal closePrice;
    private BigDecimal averageOpenPrice;
    private int multiplier;
    private String costEffect;
    private boolean isSuppressed;
    private boolean isFrozen;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private BigDecimal mark;
    private BigDecimal markPrice;
    private BigDecimal restrictedQuantity;
    private ZonedDateTime expiresAt;
    private BigDecimal fixingPrice;
    private String deliverableType;
    private BigDecimal averageYearlyMarketClosePrice;
    private BigDecimal averageDailyMarketClosePrice;
    private LocalDate realizedDayGainDate;
    private LocalDate realizedTodayDate;

    private BigDecimal realizedDayGain;
    private PriceEffect realizedDayGainEffect;

    private BigDecimal realizedToday;
    private PriceEffect realizedTodayEffect;

    public BigDecimal getSignedRealizedDayGain() {
        if (realizedDayGain == null) {
            return BigDecimal.ZERO;
        }
        if (realizedDayGainEffect == PriceEffect.DEBIT) {
            return realizedDayGain.negate();
        }
        return realizedDayGain;
    }

    public BigDecimal getSignedRealizedToday() {
        if (realizedToday == null) {
            return BigDecimal.ZERO;
        }
        if (realizedTodayEffect == PriceEffect.DEBIT) {
            return realizedToday.negate();
        }
        return realizedToday;
    }
}