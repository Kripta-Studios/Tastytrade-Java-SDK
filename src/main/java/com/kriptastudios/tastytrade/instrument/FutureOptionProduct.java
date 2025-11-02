package com.kriptastudios.tastytrade.instrument;
import com.kriptastudios.tastytrade.model.TastytradeData;
import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FutureOptionProduct extends TastytradeData {
    private String rootSymbol;
    private boolean cashSettled;
    private String code;
    private BigDecimal displayFactor;
    private String exchange;
    private String productType;
    private String expirationType;
    private int settlementDelayDays;
    private String marketSector;
    private String clearingCode;
    private String clearingExchangeCode;
    private BigDecimal clearingPriceMultiplier;
    private boolean isRollover;
    private FutureProduct futureProduct; // Referencia a la clase
    private String productSubtype;
    private String legacyCode;
    private String clearportCode;
}