package iag1.com.types;

/**
 * Application Configuration.
 * Note(s):
 * Some other JSON based real-time quotes - used for alerting, SMS, etc.
 * See http://stackoverflow.com/questions/27543776/yahoo-finance-webservice-api
 * http://finance.yahoo.com/webservice/v1/symbols/COALINDIA.NS/quote?format=json
 * http://finance.yahoo.com/webservice/v1/symbols/COALINDIA.NS/quote?format=json&view=detail
 * http://finance.yahoo.com/webservice/v1/symbols/YHOO,AAPL/quote?format=json&view=detail
 * 15 minute delayed.
 * http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20IN%20(%22YHOO%22,%22AAPL%22)&format=json&env=http://datatables.org/alltables.env
 *
 * Created by dqromney on 9/26/15.
 */
public enum AppConfig {
    YAHOO_CHART_WEB_SERVICE_URL("http://ichart.yahoo.com/table.csv?s="),
    YAHOO_FINANCE_WEB_SERVICE_URL("http://finance.yahoo.com/webservice/v1/symbols/"),
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
