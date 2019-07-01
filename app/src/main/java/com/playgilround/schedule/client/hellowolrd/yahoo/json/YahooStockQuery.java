package com.playgilround.schedule.client.hellowolrd.yahoo.json;

import java.util.Date;

public class YahooStockQuery {

    private int count;
    private Date created;
    private YahooStockResult results;

    public YahooStockResult getResults() {
        return results;
    }

    public Date getCreated() {
        return created;
    }
}
