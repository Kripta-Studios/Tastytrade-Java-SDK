package com.kriptastudios.tastytrade.instrument;
import com.kriptastudios.tastytrade.model.TastytradeData;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class NestedFutureOptionChainExpiration extends TastytradeData {
    private String rootSymbol;
    private BigDecimal notionalValue;
    private String underlyingSymbol;
    private BigDecimal strikeFactor;
    private int daysToExpiration;
    private String optionRootSymbol;
    private LocalDate expirationDate;
    private ZonedDateTime expiresAt;
    private String asset;
    private String expirationType;
    private BigDecimal displayFactor;
    private String optionContractSymbol;
    private ZonedDateTime stopsTradingAt;
    private String settlementType;
    private List<Strike> strikes;
    private List<TickSize> tickSizes;
}