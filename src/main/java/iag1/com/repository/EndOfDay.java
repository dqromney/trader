package iag1.com.repository;

import iag1.com.model.Bar;
import iag1.com.types.AppConfig;
import iag1.com.types.HistoryQuoteInterval;
import iag1.com.utils.FileLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * End of Day data repository.
 *
 * Created by dqromney on 9/24/15.
 */
public class EndOfDay {
    /**
     * Retrieve All history for symbol.
     *
     * @param pSymbol the {@link String} symbol
     * @return a list of end-of-day data for the symbol
     * @throws IOException
     * @throws ParseException
     */
    public List<Bar> all(final String pSymbol) throws IOException, ParseException {
        URL url = new URL(AppConfig.YAHOO_CHART_WEB_SERVICE_URL.getValue() +
                pSymbol + HistoryQuoteInterval.DAILY.getIntervalParam());
        FileLoader fileLoader = new FileLoader(new BufferedReader(new InputStreamReader(url.openStream())));
        return fileLoader.readBars();
    }

    /**
     * Retrieve history from a date forward.
     *
     * @param pSymbol the {@link String} symbol
     * @param pFromDate the number {@link Integer} of days
     * @return a list of end-of-day data for the symbol
     */
    public List<Bar> fromDate(final String pSymbol, final Date pFromDate) throws IOException, ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(pFromDate);

        String fromDateFragment = String.format("&a=%1$d&b=%2$d&c=%3$d",
                cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.YEAR));

        URL url = new URL(AppConfig.YAHOO_CHART_WEB_SERVICE_URL.getValue() +
                pSymbol + fromDateFragment + HistoryQuoteInterval.DAILY.getIntervalParam());
        FileLoader fileLoader = new FileLoader(new BufferedReader(new InputStreamReader(url.openStream())));
        return fileLoader.readBars();
    }

    /**
     * Retrieve history from and to date.
     *
     * @param pSymbol the {@link String} symbol
     * @param pFromDate the from {@link Date}
     * @param pToDate the to {@link Date}
     * @return a list of end-of-day data for the symbol
     */
    public List<Bar> fromToDate(String pSymbol, Date pFromDate, Date pToDate) throws IOException, ParseException {
        Calendar cal = Calendar.getInstance();
        List<Bar> barList = new ArrayList<>();
        if (pFromDate == null || pToDate == null) {
            barList = Collections.EMPTY_LIST;
        }
        if (pFromDate.before(pToDate)) {
            // From date
            cal.setTime(pFromDate);
            String fromDateFragment = String.format("&a=%1$d&b=%2$d&c=%3$d",
                    cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.YEAR));
            // To date
            cal.setTime(pToDate);
            String toDateFragment = String.format("&d=%1$d&e=%2$d&f=%3$d",
                    cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.YEAR));

            URL url = new URL(AppConfig.YAHOO_CHART_WEB_SERVICE_URL.getValue() +
                    pSymbol + fromDateFragment + toDateFragment + HistoryQuoteInterval.DAILY.getIntervalParam());
            FileLoader fileLoader = new FileLoader(new BufferedReader(new InputStreamReader(url.openStream())));
            barList = fileLoader.readBars();
        } else {
            barList = Collections.EMPTY_LIST;
        }
        return barList;
    }
}
