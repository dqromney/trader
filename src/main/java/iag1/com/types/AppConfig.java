package iag1.com.types;

/**
 * Application Configuration.
 *
 * Created by dqromney on 9/26/15.
 */
public enum AppConfig {
    YAHOO_EOD_WEB_SERVICE_URL("http://ichart.yahoo.com/table.csv?s="),
    YAHOO_EOD_HEADER_DATE("Date"),
    YAHOO_EOD_HEADER_OPEN("Open"),
    YAHOO_EOD_HEADER_HIGH("High"),
    YAHOO_EOD_HEADER_LOW("Low"),
    YAHOO_EOD_HEADER_CLOSE("Close"),
    YAHOO_EOD_HEADER_VOLUME("Volume"),
    YAHOO_EOD_HEADER_ADJ_CLOSE("Adj Close"),
    YAHOO_EOD_HEADER_RSI("RSI"),
    ;

    private String value;

    AppConfig(String pValue) {
        this.value = pValue;
    }

    public String getValue() {
        return value;
    }
}
