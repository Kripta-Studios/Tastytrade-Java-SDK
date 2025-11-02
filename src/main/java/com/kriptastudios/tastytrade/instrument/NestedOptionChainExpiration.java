package com.kriptastudios.tastytrade.instrument;
import com.kriptastudios.tastytrade.model.TastytradeData;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class NestedOptionChainExpiration extends TastytradeData {
    private String expirationType;
    private LocalDate expirationDate;
    private int daysToExpiration;
    private String settlementType;
    private List<Strike> strikes;
}