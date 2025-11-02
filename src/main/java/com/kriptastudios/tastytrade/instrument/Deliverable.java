package com.kriptastudios.tastytrade.instrument;
import com.kriptastudios.tastytrade.model.TastytradeData;
import com.kriptastudios.tastytrade.order.InstrumentType;
import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Deliverable extends TastytradeData {
    private int id;
    private String rootSymbol;
    private String deliverableType;
    private String description;
    private BigDecimal amount;
    private String percent;
    private String symbol;
    private InstrumentType instrumentType;
}