package com.kriptastudios.tastytrade.instrument;
import com.kriptastudios.tastytrade.order.TradeableTastytradeData;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Cryptocurrency extends TradeableTastytradeData {
    private int id;
    private String shortDescription;
    private String description;
    private boolean isClosingOnly;
    private boolean active;
    private BigDecimal tickSize;
    private List<DestinationVenueSymbol> destinationVenueSymbols;
    private String streamerSymbol;
}