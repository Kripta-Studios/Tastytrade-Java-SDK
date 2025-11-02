package com.kriptastudios.tastytrade.order;
import com.kriptastudios.tastytrade.model.TastytradeData;
import lombok.*;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class NewComplexOrder extends TastytradeData {

    private List<NewOrder> orders;
    private ComplexOrderType type;
    private NewOrder triggerOrder;
    private String source = "kriptastudios/tastytrade-java-sdk";

    // Constructor to handle the logic of OTOCO
    public NewComplexOrder(List<NewOrder> orders, NewOrder triggerOrder) {
        this.orders = orders;
        this.triggerOrder = triggerOrder;
        if (triggerOrder != null) {
            this.type = ComplexOrderType.OTOCO;
        } else {
            this.type = ComplexOrderType.OCO;
        }
    }
}