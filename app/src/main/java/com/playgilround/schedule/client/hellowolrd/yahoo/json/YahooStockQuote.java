package com.playgilround.schedule.client.hellowolrd.yahoo.json;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class YahooStockQuote {

    private String symbol;

    @SerializedName("Name")
    private String name;

    @SerializedName("LastTradePriceOnly")
    private BigDecimal lastTradePriceOnly;

    @SerializedName("DaysLow")
    private BigDecimal daysLow;

    @SerializedName("DaysHigh")
    private BigDecimal daysHigh;

    @SerializedName("Volume")
    private String volume;

    public String getSymbol() {
        return symbol;
    }
    public String getName() {
        return name;
    }

    public BigDecimal getLastTradePriceOnly() {
        return lastTradePriceOnly;
    }

    public BigDecimal getDaysLow() {
        return daysLow;
    }

    public BigDecimal getDaysHigh() {
        return daysHigh;
    }

    public String getVolumn() {
        return volume;
    }
}
