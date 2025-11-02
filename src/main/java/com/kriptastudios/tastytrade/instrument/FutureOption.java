package com.kriptastudios.tastytrade.instrument;
import com.kriptastudios.tastytrade.order.TradeableTastytradeData;
import com.kriptastudios.tastytrade.order.InstrumentType;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FutureOption extends TradeableTastytradeData {
    private String underlyingSymbol;
    private String productCode;
    private LocalDate expirationDate;
    private String rootSymbol;
    private String optionRootSymbol;
    private BigDecimal strikePrice;
    private String exchange;
    private String streamerSymbol;
    private OptionType optionType;
    private String exerciseStyle;
    private boolean isVanilla;
    private boolean isPrimaryDeliverable;
    private BigDecimal futurePriceRatio;
    private BigDecimal multiplier;
    private BigDecimal underlyingCount;
    private boolean isConfirmed;
    private BigDecimal notionalValue;
    private BigDecimal displayFactor;
    private String settlementType;
    private BigDecimal strikeFactor;
    private LocalDate maturityDate; // Jackson manages the parsing
    private boolean isExercisableWeekly;
    private String lastTradeTime;
    private int daysToExpiration;
    private boolean isClosingOnly;
    private boolean active;
    private ZonedDateTime stopsTradingAt;
    private ZonedDateTime expiresAt;
    private String exchangeSymbol;
    private String securityExchange;
    private String sxId;
    private InstrumentType instrumentType = InstrumentType.FUTURE_OPTION;
    private FutureOptionProduct futureOptionProduct;
}