package iag1.com.repository;

import iag1.com.model.Bar;
import iag1.com.types.AppConfig;
import iag1.com.utils.FileLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.util.Collections;
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
     * Retrieve history for the last number of days.
     *
     * @param pSymbol the {@link String} symbol
     * @param pDays the number {@link Integer} of days
     * @return a list of end-of-day data for the symbol
     */
    public List<Bar> historyForDays(final String pSymbol, final Integer pDays) {
        return Collections.EMPTY_LIST;
    }
}
