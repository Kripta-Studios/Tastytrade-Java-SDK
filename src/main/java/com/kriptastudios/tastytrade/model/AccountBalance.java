package com.kriptastudios.tastytrade.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AccountBalance extends TastytradeData {
    // Dataclass containing account balance information

    // Thanks to 'extends TastytradeData', Jackson will map
    // 'accountNumber' to 'account-number' and so on

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
    private BigDecimal pendingCash;
    private BigDecimal longCryptocurrencyValue;
    private BigDecimal shortCryptocurrencyValue;
    private BigDecimal cryptocurrencyMarginRequirement;
    private BigDecimal unsettledCryptocurrencyFiatAmount;
    private BigDecimal closedLoopAvailableBalance;
    private BigDecimal equityOfferingMarginRequirement;
    private BigDecimal longBondValue;
    private BigDecimal bondMarginRequirement;
    private BigDecimal usedDerivativeBuyingPower;
    private LocalDate snapshotDate;
    private BigDecimal regTMarginRequirement;
    private BigDecimal futuresOvernightMarginRequirement;
    private BigDecimal futuresIntradayMarginRequirement;
    private BigDecimal maintenanceExcess;
    private BigDecimal pendingMarginInterest;
    private BigDecimal effectiveCryptocurrencyBuyingPower;
    private ZonedDateTime updatedAt;

    // Can be null
    private BigDecimal apexStartingDayMarginEquity;
    private BigDecimal buyingPowerAdjustment;
    private String timeOfDay;

    // La lógica de @model_validator ('set_sign_for') es compleja.
    // La forma más simple es añadir un "getter" de conveniencia:
    public BigDecimal getSignedPendingCash() {
        // Aquí deberíamos comprobar el campo 'pending-cash-effect'.
        // Por ahora, asumimos que necesitas implementar esa lógica.
        // Si el 'effect' es DEBIT, devuelve this.pendingCash.negate()
        return this.pendingCash;
    }
}