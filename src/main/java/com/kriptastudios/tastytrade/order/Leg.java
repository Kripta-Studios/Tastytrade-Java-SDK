package com.kriptastudios.tastytrade.order;
import com.kriptastudios.tastytrade.model.TastytradeData;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor // Útil para construirlo
@EqualsAndHashCode(callSuper = false)
public class Leg extends TastytradeData {
    private InstrumentType instrumentType;
    private String symbol;
    private OrderAction action;
    private BigDecimal quantity;
    private BigDecimal remainingQuantity;
    private List<FillInfo> fills;
}