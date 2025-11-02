package com.kriptastudios.tastytrade.order;
import com.kriptastudios.tastytrade.model.TastytradeData;
import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public abstract class TradeableTastytradeData extends TastytradeData {
    private InstrumentType instrumentType;
    private String symbol;

    /**
     * Translation of 'build_leg'.
     * Builds an object Leg from an instrument.
     */
    public Leg buildLeg(BigDecimal quantity, OrderAction action) {
        return new Leg(
                this.instrumentType,
                this.symbol,
                action,
                quantity,
                null, // remainingQuantity
                null  // fills
        );
    }
}