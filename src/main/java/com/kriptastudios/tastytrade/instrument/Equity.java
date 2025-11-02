package com.kriptastudios.tastytrade.instrument;
import com.kriptastudios.tastytrade.order.TradeableTastytradeData;
import lombok.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Equity extends TradeableTastytradeData {
    private int id;
    private boolean isIndex;
    private String description;
    private String lendability;
    private String marketTimeInstrumentCollection;
    private boolean isClosingOnly;
    private boolean isOptionsClosingOnly;
    private boolean active;
    private boolean isIlliquid;
    private boolean isEtf;
    private String streamerSymbol;
    private BigDecimal borrowRate;
    private String cusip;
    private String shortDescription;
    private ZonedDateTime haltedAt;
    private ZonedDateTime stopsTradingAt;
    private Boolean isFractionalQuantityEligible;
    private List<TickSize> tickSizes;
    private String listedMarket;
    private List<TickSize> optionTickSizes;
}