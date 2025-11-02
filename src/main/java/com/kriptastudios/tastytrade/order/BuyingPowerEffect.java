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
public class BuyingPowerEffect extends TastytradeData {

    private boolean isSpread;
    private PriceEffect effect;

    private BigDecimal changeInMarginRequirement;
    private PriceEffect changeInMarginRequirementEffect;

    private BigDecimal changeInBuyingPower;
    private PriceEffect changeInBuyingPowerEffect;

    private BigDecimal currentBuyingPower;
    private PriceEffect currentBuyingPowerEffect;

    private BigDecimal newBuyingPower;
    private PriceEffect newBuyingPowerEffect;

    private BigDecimal isolatedOrderMarginRequirement;
    private PriceEffect isolatedOrderMarginRequirementEffect;

    private BigDecimal impact;
    // Nota: 'impact' no estaba en la lista de 'set_sign_for',
    // pero 'effect' (el campo principal) sí lo aplica.
    // Dejaremos 'impact' tal cual, pero añadimos su 'effect' si existe.
    private PriceEffect impactEffect;


    // --- Getters con Signo ---

    public BigDecimal getSignedChangeInMarginRequirement() {
        return (changeInMarginRequirement != null && changeInMarginRequirementEffect == PriceEffect.DEBIT)
                ? changeInMarginRequirement.negate() : changeInMarginRequirement;
    }

    public BigDecimal getSignedChangeInBuyingPower() {
        return (changeInBuyingPower != null && changeInBuyingPowerEffect == PriceEffect.DEBIT)
                ? changeInBuyingPower.negate() : changeInBuyingPower;
    }

    public BigDecimal getSignedCurrentBuyingPower() {
        return (currentBuyingPower != null && currentBuyingPowerEffect == PriceEffect.DEBIT)
                ? currentBuyingPower.negate() : currentBuyingPower;
    }

    public BigDecimal getSignedNewBuyingPower() {
        return (newBuyingPower != null && newBuyingPowerEffect == PriceEffect.DEBIT)
                ? newBuyingPower.negate() : newBuyingPower;
    }

    public BigDecimal getSignedIsolatedOrderMarginRequirement() {
        return (isolatedOrderMarginRequirement != null && isolatedOrderMarginRequirementEffect == PriceEffect.DEBIT)
                ? isolatedOrderMarginRequirement.negate() : isolatedOrderMarginRequirement;
    }

    public BigDecimal getSignedImpact() {
        // Asumimos que el 'effect' principal se aplica a 'impact'
        return (impact != null && effect == PriceEffect.DEBIT)
                ? impact.negate() : impact;
    }
}