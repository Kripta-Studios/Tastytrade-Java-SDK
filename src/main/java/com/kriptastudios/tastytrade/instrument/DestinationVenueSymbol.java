package com.kriptastudios.tastytrade.instrument;
import com.kriptastudios.tastytrade.model.TastytradeData;
import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DestinationVenueSymbol extends TastytradeData {
    private int id;
    private String symbol;
    private String destinationVenue;
    private boolean routable;
    private Integer maxQuantityPrecision;
    private Integer maxPricePrecision;
}