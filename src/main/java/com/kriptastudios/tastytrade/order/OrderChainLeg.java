package com.kriptastudios.tastytrade.order;
import com.kriptastudios.tastytrade.model.TastytradeData;
import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OrderChainLeg extends TastytradeData {
    private String symbol;
    private InstrumentType instrumentType;
    private OrderAction action;
    private BigDecimal fillQuantity;
    private BigDecimal orderQuantity;
}