package iag1.com.model;

import iag1.com.types.Direction;

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
    private Double closeChange;
    private Double closePercentChange;
    // Calculated
    private Long volumeChange = 0L;
    private Direction volumeChangeDirection = Direction.UNDEFINED;
    // Relative Strength Index
    private Double rsi = 0.0;
    private Direction rsiDirection = Direction.UNDEFINED;
    // Simple Moving Average
    private Double sma = 0.0;
    private Direction smaDirection = Direction.UNDEFINED;;

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

    public Double getSma() {
        return sma;
    }

    public void setSma(Double sma) {
        this.sma = sma;
    }

    public Direction getRsiDirection() {
        return rsiDirection;
    }

    public void setRsiDirection(Direction rsiDirection) {
        this.rsiDirection = rsiDirection;
    }

    public Direction getSmaDirection() {
        return smaDirection;
    }

    public void setSmaDirection(Direction smaDirection) {
        this.smaDirection = smaDirection;
    }

    public Double getCloseChange() {
        return closeChange;
    }

    public void setCloseChange(Double closeChange) {
        this.closeChange = closeChange;
    }

    public Double getClosePercentChange() {
        return closePercentChange;
    }

    public void setClosePercentChange(Double closePercentChange) {
        this.closePercentChange = closePercentChange;
    }

    public Long getVolumeChange() {
        return volumeChange;
    }

    public void setVolumeChange(Long volumeChange) {
        this.volumeChange = volumeChange;
    }

    public Direction getVolumeChangeDirection() {
        return volumeChangeDirection;
    }

    public void setVolumeChangeDirection(Direction volumeChangeDirection) {
        this.volumeChangeDirection = volumeChangeDirection;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Bar{");
        sb.append("date=").append(date);
        sb.append(", open=").append(open);
        sb.append(", high=").append(high);
        sb.append(", low=").append(low);
        sb.append(", close=").append(close);
        sb.append(", volume=").append(volume);
        sb.append(", adjClose=").append(adjClose);
        sb.append(", closeChange=").append(closeChange);
        sb.append(", closePercentChange=").append(closePercentChange);
        sb.append(", volumeChange=").append(volumeChange);
        sb.append(", volumeChangeDirection=").append(volumeChangeDirection);
        sb.append(", rsi=").append(rsi);
        sb.append(", rsiDirection=").append(rsiDirection);
        sb.append(", sma=").append(sma);
        sb.append(", smaDirection=").append(smaDirection);
        sb.append('}');
        return sb.toString();
    }
}
