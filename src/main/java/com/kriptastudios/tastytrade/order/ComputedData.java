package com.kriptastudios.tastytrade.order;

import com.kriptastudios.tastytrade.TastytradeUtils.PriceEffect;
import com.kriptastudios.tastytrade.model.TastytradeData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ComputedData extends TastytradeData {

    private boolean open;
    private ZonedDateTime updatedAt;
    private boolean winnerRealizedAndClosed;
    private boolean winnerRealized;
    private boolean winnerRealizedWithFees;
    private int rollCount;
    private ZonedDateTime openedAt;
    private ZonedDateTime lastOccurredAt;
    private int startedAtDaysToExpiration;
    private int duration;
    private BigDecimal gcdOpenQuantity;
    private boolean feesMissing;
    private List<OrderChainEntry> openEntries;

    private BigDecimal totalCostPerUnit;
    private PriceEffect totalCostPerUnitEffect;

    private BigDecimal totalFees;
    private PriceEffect totalFeesEffect;

    private BigDecimal totalCommissions;
    private PriceEffect totalCommissionsEffect;

    private BigDecimal realizedGain;
    private PriceEffect realizedGainEffect;

    private BigDecimal realizedGainWithFees;
    private PriceEffect realizedGainWithFeesEffect;

    private BigDecimal totalOpeningCost;
    private PriceEffect totalOpeningCostEffect;

    private BigDecimal totalClosingCost;
    private PriceEffect totalClosingCostEffect;

    private BigDecimal totalCost;
    private PriceEffect totalCostEffect;

    // --- Getters con Signo ---

    public BigDecimal getSignedTotalCostPerUnit() {
        return (totalCostPerUnit != null && totalCostPerUnitEffect == PriceEffect.DEBIT)
                ? totalCostPerUnit.negate() : totalCostPerUnit;
    }

    public BigDecimal getSignedTotalFees() {
        return (totalFees != null && totalFeesEffect == PriceEffect.DEBIT)
                ? totalFees.negate() : totalFees;
    }

    public BigDecimal getSignedTotalCommissions() {
        return (totalCommissions != null && totalCommissionsEffect == PriceEffect.DEBIT)
                ? totalCommissions.negate() : totalCommissions;
    }

    public BigDecimal getSignedRealizedGain() {
        return (realizedGain != null && realizedGainEffect == PriceEffect.DEBIT)
                ? realizedGain.negate() : realizedGain;
    }

    public BigDecimal getSignedRealizedGainWithFees() {
        return (realizedGainWithFees != null && realizedGainWithFeesEffect == PriceEffect.DEBIT)
                ? realizedGainWithFees.negate() : realizedGainWithFees;
    }

    public BigDecimal getSignedTotalOpeningCost() {
        return (totalOpeningCost != null && totalOpeningCostEffect == PriceEffect.DEBIT)
                ? totalOpeningCost.negate() : totalOpeningCost;
    }

    public BigDecimal getSignedTotalClosingCost() {
        return (totalClosingCost != null && totalClosingCostEffect == PriceEffect.DEBIT)
                ? totalClosingCost.negate() : totalClosingCost;
    }

    public BigDecimal getSignedTotalCost() {
        return (totalCost != null && totalCostEffect == PriceEffect.DEBIT)
                ? totalCost.negate() : totalCost;
    }
}