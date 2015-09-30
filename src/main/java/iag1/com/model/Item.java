package iag1.com.model;

import iag1.com.utils.WatchList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Financial instrument.
 *
 * Created by dqromney on 9/9/15.
 */
public class Item {

    private String symbol;
    private String name;
    private String exchange;
    private List<Bar> bars;
    private WatchList watchList;

    public Item() {
        // Empty
    }

    public Item(String pSymbol, String pName, String pExchange) {
        this.symbol = pSymbol;
        this.name = pName;
        this.exchange = pExchange;
        this.bars = new ArrayList<Bar>();
    }

    public Item(String symbol, String name, String pExchange, List<Bar> pBarList) {
        this.symbol = symbol;
        this.name = name;
        this.exchange = pExchange;
        this.bars = pBarList == null ? Collections.EMPTY_LIST : pBarList;
    }

    public Item(WatchList pWatchList, List<Bar> pBarList) {
        this.symbol = pWatchList.getSymbol();
        this.name = pWatchList.getName();
        this.exchange = pWatchList.getExchange();
        this.bars = pBarList == null ? Collections.EMPTY_LIST : pBarList;
        this.watchList = pWatchList;
    }

    // ----------------------------------------------------------------
    // Access methods
    // ----------------------------------------------------------------
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public List<Bar> getBars() {
        return bars;
    }

    public void setBars(List<Bar> bars) {
        this.bars = bars;
    }

    public WatchList getWatchList() {
        return watchList;
    }

    public void setWatchList(WatchList watchList) {
        this.watchList = watchList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Item{");
        sb.append("symbol='").append(symbol).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", exchange='").append(exchange).append('\'');
        sb.append(", bars=").append(bars);
        sb.append(", watchList=").append(watchList);
        sb.append('}');
        return sb.toString();
    }
}
