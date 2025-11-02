package com.kriptastudios.tastytrade.model;

import com.kriptastudios.tastytrade.TastytradeUtils.PriceEffect;
import com.kriptastudios.tastytrade.order.InstrumentType;
import com.kriptastudios.tastytrade.order.OrderAction;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Transaction extends TastytradeData {

    private int id;
    private String accountNumber;
    private String transactionType;
    private String transactionSubType;
    private String description;
    private ZonedDateTime executedAt;
    private LocalDate transactionDate;
    private boolean isEstimatedFee;

    // Optional fields (nullable)
    private String symbol;
    private InstrumentType instrumentType;
    private String underlyingSymbol;
    private OrderAction action;
    private BigDecimal quantity;
    private BigDecimal price;
    private String extExchangeOrderNumber;
    private Integer extGlobalOrderNumber;
    private String extGroupId;
    private String extGroupFillId;
    private String extExecId;
    private String execId;
    private String exchange;
    private Integer orderId;
    private String exchangeAffiliationIdentifier;
    private Integer legCount;
    private String destinationVenue;
    private String otherChargeDescription;
    private Integer reversesId;
    private LocalDate costBasisReconciliationDate;
    private List<Lot> lots;
    private BigDecimal agencyPrice;
    private BigDecimal principalPrice;

    private BigDecimal value;
    private PriceEffect valueEffect;

    private BigDecimal netValue;
    private PriceEffect netValueEffect;

    private BigDecimal regulatoryFees;
    private PriceEffect regulatoryFeesEffect;

    private BigDecimal clearingFees;
    private PriceEffect clearingFeesEffect;

    private BigDecimal commission;
    private PriceEffect commissionEffect;

    private BigDecimal proprietaryIndexOptionFees;
    private PriceEffect proprietaryIndexOptionFeesEffect;

    private BigDecimal otherCharge;
    private PriceEffect otherChargeEffect;

    // Getters with sign
    public BigDecimal getSignedValue() {
        return (value != null && valueEffect == PriceEffect.DEBIT) ? value.negate() : value;
    }
    public BigDecimal getSignedNetValue() {
        return (netValue != null && netValueEffect == PriceEffect.DEBIT) ? netValue.negate() : netValue;
    }
    public BigDecimal getSignedRegulatoryFees() {
        return (regulatoryFees != null && regulatoryFeesEffect == PriceEffect.DEBIT) ? regulatoryFees.negate() : regulatoryFees;
    }
    public BigDecimal getSignedClearingFees() {
        return (clearingFees != null && clearingFeesEffect == PriceEffect.DEBIT) ? clearingFees.negate() : clearingFees;
    }
    public BigDecimal getSignedCommission() {
        return (commission != null && commissionEffect == PriceEffect.DEBIT) ? commission.negate() : commission;
    }
    public BigDecimal getSignedProprietaryIndexOptionFees() {
        return (proprietaryIndexOptionFees != null && proprietaryIndexOptionFeesEffect == PriceEffect.DEBIT) ? proprietaryIndexOptionFees.negate() : proprietaryIndexOptionFees;
    }
    public BigDecimal getSignedOtherCharge() {
        return (otherCharge != null && otherChargeEffect == PriceEffect.DEBIT) ? otherCharge.negate() : otherCharge;
    }
}