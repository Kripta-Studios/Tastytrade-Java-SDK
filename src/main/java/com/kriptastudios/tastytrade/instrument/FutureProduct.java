package com.kriptastudios.tastytrade.instrument;
// (imports)
import com.kriptastudios.tastytrade.model.TastytradeData;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FutureProduct extends TastytradeData {
    private String rootSymbol;
    private String code;
    private String description;
    private String exchange;
    private String productType;
    private List<FutureMonthCode> listedMonths;
    private List<FutureMonthCode> activeMonths;
    private BigDecimal notionalMultiplier;
    private BigDecimal tickSize;
    private BigDecimal displayFactor;
    private String streamerExchangeCode;
    private boolean smallNotional;
    private boolean backMonthFirstCalendarSymbol;
    private boolean firstNotice;
    private boolean cashSettled;
    private String marketSector;
    private String clearingCode;
    private String clearingExchangeCode;
    private Roll roll;
    private Integer baseTick;
    private Integer subTick;
    private Integer contractLimit;
    private String productSubtype;
    private String securityGroup;
    private String trueUnderlyingCode;
    private String clearportCode;
    private String legacyCode;
    private String legacyExchangeCode;
    private List<FutureOptionProduct> optionProducts; // Referencia a la clase
}