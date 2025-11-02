package com.kriptastudios.tastytrade.order;
import com.kriptastudios.tastytrade.model.TastytradeData;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PlacedComplexOrderResponse extends TastytradeData {
    private BuyingPowerEffect buyingPowerEffect;
    private PlacedComplexOrder complexOrder;
    private FeeCalculation feeCalculation;
    private List<Message> warnings;
    private List<Message> errors;
}