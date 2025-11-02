package com.kriptastudios.tastytrade.model;

import com.kriptastudios.tastytrade.TastytradeUtils.PriceEffect;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AccountBalanceSnapshot extends TastytradeData {
    // Dataclass containing account balance information

    private String accountNumber;
    private BigDecimal cashBalance;
    private BigDecimal longEquityValue;
    private BigDecimal shortEquityValue;
    private BigDecimal longDerivativeValue;
    private BigDecimal shortDerivativeValue;
    private BigDecimal longFuturesValue;
    private BigDecimal shortFuturesValue;
    private BigDecimal longFuturesDerivativeValue;
    private BigDecimal shortFuturesDerivativeValue;
    private BigDecimal longMargineableValue;
    private BigDecimal shortMargineableValue;
    private BigDecimal marginEquity;
    private BigDecimal equityBuyingPower;
    private BigDecimal derivativeBuyingPower;
    private BigDecimal dayTradingBuyingPower;
    private BigDecimal futuresMarginRequirement;
    private BigDecimal availableTradingFunds;
    private BigDecimal maintenanceRequirement;
    private BigDecimal maintenanceCallValue;
    private BigDecimal regTCallValue;
    private BigDecimal dayTradingCallValue;
    private BigDecimal dayEquityCallValue;
    private BigDecimal netLiquidatingValue;
    private BigDecimal cashAvailableToWithdraw;
    private BigDecimal dayTradeExcess;
    private LocalDate snapshotDate;
    private String timeOfDay;
    private BigDecimal longCryptocurrencyValue;
    private BigDecimal shortCryptocurrencyValue;
    private BigDecimal cryptocurrencyMarginRequirement;
    private BigDecimal closedLoopAvailableBalance;
    private BigDecimal equityOfferingMarginRequirement;
    private BigDecimal longBondValue;
    private BigDecimal bondMarginRequirement;
    private BigDecimal usedDerivativeBuyingPower;

    private BigDecimal pendingCash;
    private PriceEffect pendingCashEffect; // Maps "pending-cash-effect"

    private BigDecimal unsettledCryptocurrencyFiatAmount;
    private PriceEffect unsettledCryptocurrencyFiatEffect; // Maps "unsettled-cryptocurrency-fiat-effect"

    /**
     * Translation of the logic 'set_sign_for' for pendingCash.
     *
     * @return The 'pendingCash' with sign applied (negative if it is DEBIT).
     */
    public BigDecimal getSignedPendingCash() {
        if (pendingCash == null)
        {
            return BigDecimal.ZERO;
        }
        if (pendingCashEffect == PriceEffect.DEBIT)
        {
            return pendingCash.negate();
        }
        return pendingCash;
    }

    /**
     * Translate validation logic for unsettledCryptocurrencyFiatAmount.
     *
     * @return The amount with the sign applied (negative if it is DEBIT).
     */
    public BigDecimal getSignedUnsettledCryptocurrencyFiatAmount() {
        if (unsettledCryptocurrencyFiatAmount == null)
        {
            return BigDecimal.ZERO;
        }
        if (unsettledCryptocurrencyFiatEffect == PriceEffect.DEBIT)
        {
            return unsettledCryptocurrencyFiatAmount.negate();
        }
        return unsettledCryptocurrencyFiatAmount;
    }
}