package com.kriptastudios.tastytrade.order;
import com.kriptastudios.tastytrade.TastytradeUtils.PriceEffect;
import com.kriptastudios.tastytrade.model.TastytradeData;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PlacedOrder extends TastytradeData {

    private String accountNumber;
    private OrderTimeInForce timeInForce;
    private OrderType orderType;
    private String underlyingSymbol;
    private InstrumentType underlyingInstrumentType;
    private OrderStatus status;
    private boolean cancellable;
    private boolean editable;
    private boolean edited;
    private ZonedDateTime updatedAt;
    private List<Leg> legs;
    private int id = -1; // Default value
    private BigDecimal size;
    private LocalDate gtcDate;
    private String stopTrigger;
    private String contingentStatus;
    private String confirmationStatus;
    private ZonedDateTime cancelledAt;
    private String cancelUserId;
    private String cancelUsername;
    private String replacingOrderId;
    private String replacesOrderId;
    private ZonedDateTime inFlightAt;
    private ZonedDateTime liveAt;
    private ZonedDateTime receivedAt;
    private String rejectReason;
    private String userId;
    private String username;
    private ZonedDateTime terminalAt;
    private String complexOrderId;
    private String complexOrderTag;
    private String preflightId;
    private OrderRule orderRule;
    private String source;

    private BigDecimal price;
    private PriceEffect priceEffect;

    private BigDecimal value;
    private PriceEffect valueEffect;

    public BigDecimal getSignedPrice() {
        return (price != null && priceEffect == PriceEffect.DEBIT) ? price.negate() : price;
    }
    public BigDecimal getSignedValue() {
        return (value != null && valueEffect == PriceEffect.DEBIT) ? value.negate() : value;
    }
}