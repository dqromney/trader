package iag1.com;

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
    private Long volumne;
    private Double adjClose;

    public Bar(Date date, Double open, Double high, Double low, Double close, Long volumne, Double adjClose) {
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volumne = volumne;
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

    public Long getVolumne() {
        return volumne;
    }

    public void setVolumne(Long volumne) {
        this.volumne = volumne;
    }

    public Double getAdjClose() {
        return adjClose;
    }

    public void setAdjClose(Double adjClose) {
        this.adjClose = adjClose;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Bar{");
        sb.append("date=").append(date);
        sb.append(", open=").append(open);
        sb.append(", high=").append(high);
        sb.append(", low=").append(low);
        sb.append(", close=").append(close);
        sb.append(", volumne=").append(volumne);
        sb.append(", adjClose=").append(adjClose);
        sb.append('}');
        return sb.toString();
    }
}
