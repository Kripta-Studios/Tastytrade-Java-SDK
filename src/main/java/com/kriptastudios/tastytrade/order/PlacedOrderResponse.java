package com.kriptastudios.tastytrade.order;
import com.kriptastudios.tastytrade.model.TastytradeData;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PlacedOrderResponse extends TastytradeData {
    private BuyingPowerEffect buyingPowerEffect;
    private PlacedOrder order;
    private FeeCalculation feeCalculation;
    private List<Message> warnings;
    private List<Message> errors;
}