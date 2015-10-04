package iag1.com.types;

/**
 * History Quote intervals (Yahoo).
 *
 * Created by dqromney on 10/4/15.
 */
public enum HistoryQuoteInterval {
    DAILY("Daily", "d", "&g=d"),
    WEEKLY("Weekly", "w", "&g=w"),
    MONTHLY("Monthly", "m", "&g=m")
    ;

    private String name;
    private String interval;
    private String intervalParam;

    HistoryQuoteInterval(String name, String interval, String intervalParam) {
        this.name = name;
        this.interval = interval;
        this.intervalParam = intervalParam;
    }

    // ----------------------------------------------------------------
    // Access methods
    // ----------------------------------------------------------------
    public String getName() {
        return name;
    }

    public String getInterval() {
        return interval;
    }

    public String getIntervalParam() {
        return intervalParam;
    }
}
