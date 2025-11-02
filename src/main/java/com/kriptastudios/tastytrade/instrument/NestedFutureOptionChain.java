package com.kriptastudios.tastytrade.instrument;
import com.kriptastudios.tastytrade.model.TastytradeData;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class NestedFutureOptionChain extends TastytradeData {
    private List<NestedFutureOptionFuture> futures;
    private List<NestedFutureOptionSubchain> optionChains;
}