package com.kriptastudios.tastytrade.instrument;
import com.kriptastudios.tastytrade.model.TastytradeData;
import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Strike extends TastytradeData {
    private BigDecimal strikePrice;
    private String call;
    private String put;
    private String callStreamerSymbol;
    private String putStreamerSymbol;
}