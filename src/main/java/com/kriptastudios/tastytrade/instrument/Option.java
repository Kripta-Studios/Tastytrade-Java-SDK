package com.kriptastudios.tastytrade.instrument;

import com.kriptastudios.tastytrade.order.TradeableTastytradeData;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Option extends TradeableTastytradeData {

    private boolean active;
    private BigDecimal strikePrice;
    private String rootSymbol;
    private String underlyingSymbol;
    private LocalDate expirationDate;
    private String exerciseStyle;
    private int sharesPerContract;
    private OptionType optionType;
    private String optionChainType;
    private String expirationType;
    private String settlementType;
    private ZonedDateTime stopsTradingAt;
    private String marketTimeInstrumentCollection;
    private int daysToExpiration;
    private ZonedDateTime expiresAt;
    private boolean isClosingOnly;
    private String listedMarket;
    private ZonedDateTime haltedAt;
    private String oldSecurityNumber;

    private String streamerSymbol;
    private static final DateTimeFormatter EXP_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");
    private static final Pattern OCC_PATTERN = Pattern.compile("(\\d{6})([CP])(\\d{5})(\\d{3})");
    private static final Pattern STREAMER_PATTERN = Pattern.compile("\\.([A-Z]+)(\\d{6})([CP])(\\d+)(\\.(\\d+))?");

    public String getStreamerSymbol() {
        if (streamerSymbol == null || streamerSymbol.isEmpty()) {
            calculateStreamerSymbol();
        }
        return streamerSymbol;
    }

    private void calculateStreamerSymbol() {
        String strike;
        // Comprueba si el strike no tiene decimales
        if (strikePrice.stripTrailingZeros().scale() <= 0) {
            strike = String.format("%.0f", strikePrice);
        } else {
            strike = String.format("%.2f", strikePrice);
            if (strike.endsWith("0")) {
                strike = strike.substring(0, strike.length() - 1);
            }
        }
        String exp = expirationDate.format(EXP_DATE_FORMATTER);
        this.streamerSymbol = String.format(".%s%s%s%s",
                underlyingSymbol, exp, optionType.name().equals("CALL") ? "C" : "P", strike);
    }

    public static String streamerSymbolToOcc(String streamerSymbol) {
        Matcher matcher = STREAMER_PATTERN.matcher(streamerSymbol);
        if (!matcher.matches()) {
            return "";
        }
        String symbol = String.format("%-6s", matcher.group(1).substring(0, Math.min(6, matcher.group(1).length())));
        String exp = matcher.group(2);
        String optionType = matcher.group(3);
        String strike = String.format("%05d", Integer.parseInt(matcher.group(4)));
        String decimal = "000";
        if (matcher.group(6) != null) {
            decimal = String.format("%03d", (int) (Double.parseDouble("0." + matcher.group(6)) * 1000));
        }
        return symbol + exp + optionType + strike + decimal;
    }

    public static String occToStreamerSymbol(String occ) {
        String symbol = occ.substring(0, 6).trim();
        String info = occ.substring(6);
        Matcher matcher = OCC_PATTERN.matcher(info);
        if (!matcher.matches()) {
            return "";
        }
        String exp = matcher.group(1);
        String optionType = matcher.group(2);
        int strike = Integer.parseInt(matcher.group(3));
        int decimal = Integer.parseInt(matcher.group(4));

        String res = String.format(".%s%s%s%d", symbol, exp, optionType, strike);
        if (decimal != 0) {
            res += String.format("%.3f", (double) decimal / 1000.0).substring(1); // Añade .125
        }
        return res;
    }
}