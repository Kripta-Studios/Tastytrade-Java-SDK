package com.kriptastudios.tastytrade.instrument;
import com.kriptastudios.tastytrade.model.TastytradeData;
import lombok.*;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class NestedFutureOptionFuture extends TastytradeData {
    private String rootSymbol;
    private int daysToExpiration;
    private LocalDate expirationDate;
    private ZonedDateTime expiresAt;
    private boolean nextActiveMonth;
    private String symbol;
    private boolean activeMonth;
    private ZonedDateTime stopsTradingAt;
    private LocalDate maturityDate;
}