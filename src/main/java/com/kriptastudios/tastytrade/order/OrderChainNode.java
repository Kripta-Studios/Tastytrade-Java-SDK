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
public class OrderChainNode extends TastytradeData {

    private String nodeType;
    private String id;
    private String description;
    private ZonedDateTime occurredAt;
    private BigDecimal gcdQuantity;
    private Integer orderFillCount;
    private Boolean roll;
    private List<OrderChainLeg> legs;
    private List<OrderChainEntry> entries;

    // --- Lógica de @model_validator ---
    private BigDecimal totalFees;
    private PriceEffect totalFeesEffect;

    private BigDecimal totalFillCost;
    private PriceEffect totalFillCostEffect;

    private BigDecimal fillCostPerQuantity;
    private PriceEffect fillCostPerQuantityEffect;

    // --- Getters con Signo ---

    public BigDecimal getSignedTotalFees() {
        return (totalFees != null && totalFeesEffect == PriceEffect.DEBIT)
                ? totalFees.negate() : totalFees;
    }

    public BigDecimal getSignedTotalFillCost() {
        return (totalFillCost != null && totalFillCostEffect == PriceEffect.DEBIT)
                ? totalFillCost.negate() : totalFillCost;
    }

    public BigDecimal getSignedFillCostPerQuantity() {
        return (fillCostPerQuantity != null && fillCostPerQuantityEffect == PriceEffect.DEBIT)
                ? fillCostPerQuantity.negate() : fillCostPerQuantity;
    }
}