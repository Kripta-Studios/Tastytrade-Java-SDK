package com.kriptastudios.tastytrade.instrument;
import com.kriptastudios.tastytrade.model.TastytradeData;
import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TickSize extends TastytradeData {
    private BigDecimal value;
    private BigDecimal threshold;
    private String symbol;
}