package iag1.com.repository;

import iag1.com.model.Bar;
import iag1.com.types.AppConfig;
import iag1.com.utils.FileLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.util.Calendar;
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
        URL url = new URL(AppConfig.YAHOO_CHART_WEB_SERVICE_URL.getValue() + pSymbol);
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

        URL url = new URL(AppConfig.YAHOO_CHART_WEB_SERVICE_URL.getValue() + pSymbol + fromDateFragment);
        FileLoader fileLoader = new FileLoader(new BufferedReader(new InputStreamReader(url.openStream())));
        return fileLoader.readBars();
    }
}
