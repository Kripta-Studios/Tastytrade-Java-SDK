package com.kriptastudios.tastytrade.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kriptastudios.tastytrade.TastytradeUtils;
import com.kriptastudios.tastytrade.TastytradeUtils.PriceEffect;
import com.kriptastudios.tastytrade.model.TastytradeData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class NewOrder extends TastytradeData {

    private OrderTimeInForce timeInForce;
    private OrderType orderType;
    private List<Leg> legs;

    // Defautl values
    private String source = "kriptastudios/tastytrade-java-sdk"; // the 'version_str'

    // Optionals
    private LocalDate gtcDate;
    private BigDecimal stopTrigger;
    private String partitionKey;
    private String preflightId;
    private OrderRule rules;
    private AdvancedInstructions advancedInstructions;

    // Negative price for debit
    @JsonIgnore // Not serialize this field directly
    private BigDecimal price;

    @JsonIgnore // Not serialize this field directly
    private BigDecimal value; // For notional orders

    /**
     * Serializes the absolute value of price.
     */
    @JsonProperty("price")
    public BigDecimal getSerializedPrice() {
        return (price != null) ? price.abs() : null;
    }

    /**
     * Serialize the effect of price (Credit/Debit).
     */
    @JsonProperty("price-effect")
    public PriceEffect getPriceEffect() {
        return TastytradeUtils.getSign(this.price);
    }

    /**
     * Serialize the absolute value of 'value'.
     */
    @JsonProperty("value")
    public BigDecimal getSerializedValue() {
        return (value != null) ? value.abs() : null;
    }

    /**
     * Serialize the effect of 'value' (Credit/Debit).
     */
    @JsonProperty("value-effect")
    public PriceEffect getValueEffect() {
        return TastytradeUtils.getSign(this.value);
    }
}