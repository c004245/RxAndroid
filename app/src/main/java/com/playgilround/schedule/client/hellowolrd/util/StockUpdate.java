package com.playgilround.schedule.client.hellowolrd.util;

import com.playgilround.schedule.client.hellowolrd.yahoo.json.YahooStockQuote;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class StockUpdate implements Serializable {

    private final String stockSymbol;
    private final long price;
    private final Date date;
    private Integer id;

    public StockUpdate(String stockSymbol, long price, Date date) {
        this.stockSymbol = stockSymbol;
        this.price = price;
        this.date = date;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public long getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static StockUpdate create(YahooStockQuote r) {
        return new StockUpdate(r.getSymbol(), r.getLastTradePriceOnly(), new Date());
    }
}
