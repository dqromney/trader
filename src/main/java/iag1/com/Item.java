package iag1.com;

import java.util.ArrayList;
import java.util.List;

/**
 * Financial instrument.
 *
 * Created by dqromney on 9/9/15.
 */
public class Item {

    private String symbol;
    private String name;
    private List<Bar> bars;

    public Item(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
        this.bars = new ArrayList<Bar>();
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

    public List<Bar> getBars() {
        return bars;
    }

    public void setBars(List<Bar> bars) {
        this.bars = bars;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Item{");
        sb.append("symbol='").append(symbol).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", bars=").append(bars);
        sb.append('}');
        return sb.toString();
    }
}
