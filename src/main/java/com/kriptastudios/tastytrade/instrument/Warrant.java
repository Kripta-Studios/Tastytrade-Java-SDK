package com.kriptastudios.tastytrade.instrument;
import com.kriptastudios.tastytrade.model.TastytradeData;
import com.kriptastudios.tastytrade.order.InstrumentType;
import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Warrant extends TastytradeData {
    private String symbol;
    private InstrumentType instrumentType;
    private String listedMarket;
    private String description;
    private boolean isClosingOnly;
    private boolean active;
    private String cusip;
}