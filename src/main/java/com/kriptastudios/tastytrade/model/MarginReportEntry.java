package com.kriptastudios.tastytrade.model;

import com.kriptastudios.tastytrade.TastytradeUtils.PriceEffect;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MarginReportEntry extends TastytradeData {

    private String description;
    private String code;
    private String marginCalculationType;
    private BigDecimal expectedPriceRangeUpPercent;
    private BigDecimal expectedPriceRangeDownPercent;

    private List<Map<String, Object>> groups;
    private BigDecimal pointOfNoReturnPercent;
    private BigDecimal priceIncreasePercent;
    private BigDecimal priceDecreasePercent;
    private String underlyingSymbol;
    private String underlyingType;

    private BigDecimal buyingPower;
    private PriceEffect buyingPowerEffect;

    private BigDecimal marginRequirement;
    private PriceEffect marginRequirementEffect;

    private BigDecimal initialRequirement;
    private PriceEffect initialRequirementEffect;

    private BigDecimal maintenanceRequirement;
    private PriceEffect maintenanceRequirementEffect;

    public BigDecimal getSignedBuyingPower() {
        return (buyingPower != null && buyingPowerEffect == PriceEffect.DEBIT) ? buyingPower.negate() : buyingPower;
    }
    public BigDecimal getSignedMarginRequirement() {
        return (marginRequirement != null && marginRequirementEffect == PriceEffect.DEBIT) ? marginRequirement.negate() : marginRequirement;
    }
    public BigDecimal getSignedInitialRequirement() {
        return (initialRequirement != null && initialRequirementEffect == PriceEffect.DEBIT) ? initialRequirement.negate() : initialRequirement;
    }
    public BigDecimal getSignedMaintenanceRequirement() {
        return (maintenanceRequirement != null && maintenanceRequirementEffect == PriceEffect.DEBIT) ? maintenanceRequirement.negate() : maintenanceRequirement;
    }
}