package com.kriptastudios.tastytrade.order;
import com.kriptastudios.tastytrade.model.TastytradeData;
import lombok.*;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OrderRule extends TastytradeData {
    private ZonedDateTime routeAfter;
    private ZonedDateTime routedAt;
    private ZonedDateTime cancelAt;
    private ZonedDateTime cancelledAt;
    private List<OrderCondition> orderConditions;
}