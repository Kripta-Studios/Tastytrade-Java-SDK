package com.kriptastudios.tastytrade.model;

import com.kriptastudios.tastytrade.TastytradeUtils.PriceEffect;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MarginReport extends TastytradeData {

    private String accountNumber;
    private String description;
    private String marginCalculationType;
    private String optionLevel;
    private long lastStateTimestamp;
    private List<MarginReportEntry> groups;

    private BigDecimal maintenanceRequirement;
    private PriceEffect maintenanceRequirementEffect;

    private BigDecimal marginRequirement;
    private PriceEffect marginRequirementEffect;

    private BigDecimal marginEquity;
    private PriceEffect marginEquityEffect;

    private BigDecimal maintenanceExcess;
    private PriceEffect maintenanceExcessEffect;

    private BigDecimal optionBuyingPower;
    private PriceEffect optionBuyingPowerEffect;

    private BigDecimal regTMarginRequirement;
    private PriceEffect regTMarginRequirementEffect;

    private BigDecimal regTOptionBuyingPower;
    private PriceEffect regTOptionBuyingPowerEffect;

    private BigDecimal initialRequirement;
    private PriceEffect initialRequirementEffect;

    // Getters con signo
    public BigDecimal getSignedMaintenanceRequirement() {
        return (maintenanceRequirement != null && maintenanceRequirementEffect == PriceEffect.DEBIT) ? maintenanceRequirement.negate() : maintenanceRequirement;
    }
    public BigDecimal getSignedMarginRequirement() {
        return (marginRequirement != null && marginRequirementEffect == PriceEffect.DEBIT) ? marginRequirement.negate() : marginRequirement;
    }
    public BigDecimal getSignedMarginEquity() {
        return (marginEquity != null && marginEquityEffect == PriceEffect.DEBIT) ? marginEquity.negate() : marginEquity;
    }
    public BigDecimal getSignedMaintenanceExcess() {
        return (maintenanceExcess != null && maintenanceExcessEffect == PriceEffect.DEBIT) ? maintenanceExcess.negate() : maintenanceExcess;
    }
    public BigDecimal getSignedOptionBuyingPower() {
        return (optionBuyingPower != null && optionBuyingPowerEffect == PriceEffect.DEBIT) ? optionBuyingPower.negate() : optionBuyingPower;
    }
    public BigDecimal getSignedRegTMarginRequirement() {
        return (regTMarginRequirement != null && regTMarginRequirementEffect == PriceEffect.DEBIT) ? regTMarginRequirement.negate() : regTMarginRequirement;
    }
    public BigDecimal getSignedRegTOptionBuyingPower() {
        return (regTOptionBuyingPower != null && regTOptionBuyingPowerEffect == PriceEffect.DEBIT) ? regTOptionBuyingPower.negate() : regTOptionBuyingPower;
    }
    public BigDecimal getSignedInitialRequirement() {
        return (initialRequirement != null && initialRequirementEffect == PriceEffect.DEBIT) ? initialRequirement.negate() : initialRequirement;
    }
}