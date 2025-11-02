package com.kriptastudios.tastytrade.order;
import com.kriptastudios.tastytrade.model.TastytradeData;
import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AdvancedInstructions extends TastytradeData {
    // The ' = false' in Python is handled initializing the value
    private boolean strictPositionEffectValidation = false;
}