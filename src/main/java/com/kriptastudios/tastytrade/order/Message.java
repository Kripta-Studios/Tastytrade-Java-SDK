package com.kriptastudios.tastytrade.order;
import com.kriptastudios.tastytrade.model.TastytradeData;
import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Message extends TastytradeData {
    private String code;
    private String message;
    private String preflightId;

    @Override
    public String toString() {
        return code + ": " + message;
    }
}