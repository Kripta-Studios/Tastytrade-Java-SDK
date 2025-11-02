package com.kriptastudios.tastytrade.instrument;
import com.kriptastudios.tastytrade.model.TastytradeData;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class NestedOptionChain extends TastytradeData {
    private String underlyingSymbol;
    private String rootSymbol;
    private String optionChainType;
    private int sharesPerContract;
    private List<TickSize> tickSizes;
    private List<NestedOptionChainExpiration> expirations;
    private List<Deliverable> deliverables;
}