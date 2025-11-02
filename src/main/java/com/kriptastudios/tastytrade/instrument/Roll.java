package com.kriptastudios.tastytrade.instrument;
import com.kriptastudios.tastytrade.model.TastytradeData;
import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Roll extends TastytradeData {
    private String name;
    private int activeCount;
    private boolean cashSettled;
    private int businessDaysOffset;
    private boolean firstNotice;
}