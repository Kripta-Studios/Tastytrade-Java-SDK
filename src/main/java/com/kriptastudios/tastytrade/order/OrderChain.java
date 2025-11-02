package com.kriptastudios.tastytrade.order;
import com.kriptastudios.tastytrade.model.TastytradeData;
import lombok.*;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OrderChain extends TastytradeData {
    private int id;
    private String accountNumber;
    private String description;
    private String underlyingSymbol;
    private ComputedData computedData;
    private List<OrderChainNode> liteNodes;
    private Integer liteNodesSizes;
    private ZonedDateTime updatedAt;
    private ZonedDateTime createdAt;
}