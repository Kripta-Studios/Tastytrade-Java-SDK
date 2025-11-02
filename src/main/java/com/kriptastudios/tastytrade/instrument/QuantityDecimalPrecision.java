package com.kriptastudios.tastytrade.instrument;
import com.kriptastudios.tastytrade.model.TastytradeData;
import com.kriptastudios.tastytrade.order.InstrumentType;
import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class QuantityDecimalPrecision extends TastytradeData {
    private InstrumentType instrumentType;
    private int value;
    private int minimumIncrementPrecision;
    private String symbol;
}