package com.kriptastudios.tastytrade.order;
import com.kriptastudios.tastytrade.model.TastytradeData;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PlacedComplexOrder extends TastytradeData {
    private String accountNumber;
    private String type;
    private List<PlacedOrder> orders;
    private int id = -1; // Default value
    private PlacedOrder triggerOrder;
    private String terminalAt;
    private BigDecimal ratioPriceThreshold;
    private String ratioPriceComparator;
    private Boolean isThresholdBasedOnNotional;
    private List<Map<String, String>> relatedOrders;
}