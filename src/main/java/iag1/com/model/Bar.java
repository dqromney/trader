package iag1.com.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Bar class.
 *
 * Created by dqromney on 9/9/15.
 */
public class Bar {
    private Date date;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Long volume;
    private Double adjClose;

    private Double rsi;

    public Bar() {
        // Empty
    }

    public Bar(Date date, Double open, Double high, Double low, Double close, Long volume, Double adjClose) {
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.adjClose = adjClose;
    }

    // ----------------------------------------------------------------
    // Access methods
    // ----------------------------------------------------------------

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public Double getAdjClose() {
        return adjClose;
    }

    public void setAdjClose(Double adjClose) {
        this.adjClose = adjClose;
    }

    public Double getRsi() {
        return rsi;
    }

    public void setRsi(Double rsi) {
        this.rsi = rsi;
    }

    @Override
    public String toString() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        final StringBuilder sb = new StringBuilder("Bar{");
        sb.append("date=").append(fmt.format(date));
        sb.append(", open=").append(open);
        sb.append(", high=").append(high);
        sb.append(", low=").append(low);
        sb.append(", close=").append(close);
        sb.append(", volume=").append(volume);
        sb.append(", adjClose=").append(adjClose);
        sb.append(", rsi=").append(rsi);
        sb.append('}');
        return sb.toString();
    }
}
