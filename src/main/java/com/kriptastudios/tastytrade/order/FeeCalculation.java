package com.kriptastudios.tastytrade.order;

import com.kriptastudios.tastytrade.TastytradeUtils.PriceEffect;
import com.kriptastudios.tastytrade.model.TastytradeData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FeeCalculation extends TastytradeData {

    // --- Lógica de @model_validator ---
    private BigDecimal regulatoryFees;
    private PriceEffect regulatoryFeesEffect;

    private BigDecimal clearingFees;
    private PriceEffect clearingFeesEffect;

    private BigDecimal commission;
    private PriceEffect commissionEffect;

    private BigDecimal proprietaryIndexOptionFees;
    private PriceEffect proprietaryIndexOptionFeesEffect;

    private BigDecimal totalFees;
    private PriceEffect totalFeesEffect;

    public BigDecimal getSignedRegulatoryFees() {
        return (regulatoryFees != null && regulatoryFeesEffect == PriceEffect.DEBIT)
                ? regulatoryFees.negate() : regulatoryFees;
    }

    public BigDecimal getSignedClearingFees() {
        return (clearingFees != null && clearingFeesEffect == PriceEffect.DEBIT)
                ? clearingFees.negate() : clearingFees;
    }

    public BigDecimal getSignedCommission() {
        return (commission != null && commissionEffect == PriceEffect.DEBIT)
                ? commission.negate() : commission;
    }

    public BigDecimal getSignedProprietaryIndexOptionFees() {
        return (proprietaryIndexOptionFees != null && proprietaryIndexOptionFeesEffect == PriceEffect.DEBIT)
                ? proprietaryIndexOptionFees.negate() : proprietaryIndexOptionFees;
    }

    public BigDecimal getSignedTotalFees() {
        return (totalFees != null && totalFeesEffect == PriceEffect.DEBIT)
                ? totalFees.negate() : totalFees;
    }
}