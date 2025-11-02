package com.kriptastudios.tastytrade.instrument;
import com.kriptastudios.tastytrade.order.TradeableTastytradeData;
import com.kriptastudios.tastytrade.order.InstrumentType;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Future extends TradeableTastytradeData {
    private String productCode;
    private BigDecimal tickSize;
    private BigDecimal notionalMultiplier;
    private BigDecimal displayFactor;
    private LocalDate lastTradeDate;
    private LocalDate expirationDate;
    private boolean active;
    private boolean activeMonth;
    private boolean nextActiveMonth;
    private boolean isClosingOnly;
    private ZonedDateTime stopsTradingAt;
    private ZonedDateTime expiresAt;
    private String productGroup;
    private String exchange;
    private String streamerExchangeCode;
    private boolean backMonthFirstCalendarSymbol;
    private InstrumentType instrumentType = InstrumentType.FUTURE; // Valor por defecto
    private String streamerSymbol = ""; // Valor por defecto
    private LocalDate closingOnlyDate;
    private Boolean isTradeable;
    private FutureProduct futureProduct;
    private BigDecimal contractSize;
    private BigDecimal mainFraction;
    private BigDecimal subFraction;
    private LocalDate firstNoticeDate;
    private String rollTargetSymbol;
    private String trueUnderlyingSymbol;
    private FutureEtfEquivalent futureEtfEquivalent;
    private List<TickSize> tickSizes;
    private List<TickSize> optionTickSizes;
    private List<TickSize> spreadTickSizes;
}