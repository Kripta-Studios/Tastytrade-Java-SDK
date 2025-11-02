package com.kriptastudios.tastytrade.instrument;
import com.kriptastudios.tastytrade.model.TastytradeData;
import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FutureEtfEquivalent extends TastytradeData {
    private String symbol;
    private int shareQuantity;
}