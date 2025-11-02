package com.kriptastudios.tastytrade.order;
import com.kriptastudios.tastytrade.model.TastytradeData;
import lombok.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FillInfo extends TastytradeData {
    private String fillId;
    private BigDecimal quantity;
    private BigDecimal fillPrice;
    private ZonedDateTime filledAt;
    private String destinationVenue;
    private String extGroupFillId;
    private String extExecId;
}