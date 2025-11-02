package com.kriptastudios.tastytrade.order;
import com.kriptastudios.tastytrade.model.TastytradeData;
import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OrderConditionPriceComponent extends TastytradeData {
    private String symbol;
    private InstrumentType instrumentType;
    private BigDecimal quantity;
    private String quantityDirection;
}