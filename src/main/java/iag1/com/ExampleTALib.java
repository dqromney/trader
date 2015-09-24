package iag1.com;

import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;
import iag1.com.utils.FileLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ExampleTALib {

    /**
     * The total number of periods to generate data for.
     */
    public static final int TOTAL_PERIODS = 100;

    /**
     * The number of periods to average together.
     */
    public static final int PERIODS_AVERAGE = 14;

    public static void main(String[] args) throws ParseException, IOException {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        MInteger begin = new MInteger();
        MInteger length = new MInteger();
        Item[] items = new Item[getWatchList().size()];

        int index = 0;
        for(WatchList watch: getWatchList()) {
            items[index++] = getBars(watch.getSymbol(), watch.getName());
        }

        Item item = getBars("VSTO", "Vista ...");

        // Sort by date in descending order
        item.getBars().sort(new Comparator<Bar>() {
            public int compare(Bar o1, Bar o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        double[] closePrice = new double[item.getBars().size()];
        double[] out = new double[item.getBars().size()];

        for (int i = 0; i < item.getBars().size(); i++) {
            closePrice[i] = item.getBars().get(i).getClose();
        }

        Core c = new Core();
        // RetCode retCode = c.sma(0, closePrice.length - 1, closePrice, PERIODS_AVERAGE, begin, length, out);
        RetCode retCode = c.rsi(0, item.getBars().size() - 1, closePrice, PERIODS_AVERAGE, begin, length, out);
        if (retCode == RetCode.Success) {
            System.out.println("Output Begin:" + begin.value);
            System.out.println("Output Begin:" + length.value);

            for (int i = begin.value; i < closePrice.length; i++) {
                StringBuilder line = new StringBuilder();
                line.append("Date ");
                line.append(fmt.format(item.getBars().get(i).getDate()));
                line.append("\tclose= ");
                line.append(String.format("%1$2.4f", closePrice[i]));
                line.append("\trsi=");
                line.append(String.format("%1$2.2f", out[i - begin.value]));
                System.out.println(line.toString());
            }

        } else {
            System.out.println("Error");
        }
    }

    private static Item getBars(String pSymbol, String pSymbolName) throws IOException, ParseException {
        URL url = new URL("http://ichart.yahoo.com/table.csv?s=" +  pSymbol);
        FileLoader fileLoader = new FileLoader(new BufferedReader(new InputStreamReader(url.openStream())));
        Item item = fileLoader.read();
        item.setName(pSymbolName);
        item.setSymbol(pSymbol);

        return item;
    }

    private static List<WatchList> getWatchList() {
        List<WatchList> watchListList = new ArrayList<WatchList>();
        watchListList.add(new WatchList("VSTO", "VISTA OUTDOOR INC", "NYSE"));
        watchListList.add(new WatchList("TOL", "TOLL BROTHERS INC", "NYSE"));
        watchListList.add(new WatchList("HII", "HUNTINGTON INGALLS INDS INC", "NYSE"));
        return watchListList;
    }

}
