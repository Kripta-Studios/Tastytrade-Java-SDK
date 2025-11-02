package com.kriptastudios.tastytrade.instrument;
import com.kriptastudios.tastytrade.model.TastytradeData;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class NestedFutureOptionSubchain extends TastytradeData {
    private String underlyingSymbol;
    private String rootSymbol;
    private String exerciseStyle;
    private List<NestedFutureOptionChainExpiration> expirations;
}