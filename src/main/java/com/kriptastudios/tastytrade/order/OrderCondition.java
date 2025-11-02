package com.kriptastudios.tastytrade.order;
import com.kriptastudios.tastytrade.model.TastytradeData;
import lombok.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OrderCondition extends TastytradeData {
    private String id;
    private String action;
    private String symbol;
    private InstrumentType instrumentType;
    private String indicator;
    private String comparator;
    private BigDecimal threshold;
    private boolean isThresholdBasedOnNotional;
    private ZonedDateTime triggeredAt;
    private BigDecimal triggeredValue;
    private List<OrderConditionPriceComponent> priceComponents;
}