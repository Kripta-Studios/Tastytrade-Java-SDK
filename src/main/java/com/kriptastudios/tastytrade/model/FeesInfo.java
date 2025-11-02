package com.kriptastudios.tastytrade.model;

import com.kriptastudios.tastytrade.TastytradeUtils.PriceEffect;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FeesInfo extends TastytradeData {

    private BigDecimal totalFees;
    private PriceEffect totalFeesEffect;

    public BigDecimal getSignedTotalFees() {
        if (totalFees == null) {
            return BigDecimal.ZERO;
        }
        if (totalFeesEffect == PriceEffect.DEBIT) {
            return totalFees.negate();
        }
        return totalFees;
    }
}