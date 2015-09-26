package iag1.com;

import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;
import iag1.com.service.DataService;
import iag1.com.types.SortOrder;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Main {

    /**
     * The total number of periods to generate data for.
     */
    public static final int TOTAL_PERIODS = 100;

    /**
     * The number of periods to average together.
     */
    public static final int PERIODS_AVERAGE = 14;

    public static void main(String[] args) throws ParseException, IOException {
        DataService dataService = new DataService();

        List<Bar> barList;
        for(WatchList watch: getWatchList()) {
            barList = dataService.getAllHistory(watch.getSymbol(), SortOrder.DESC);
            getRsi(new Item(watch.getSymbol(), watch.getName(), watch.getExchange(), barList));
        }
    }

    // TODO Put this out to an analytics package
    private static void getRsi(Item item) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        MInteger begin = new MInteger();
        MInteger length = new MInteger();

        double[] closePrice = new double[item.getBars().size()];
        double[] out = new double[item.getBars().size()];

        for (int i = 0; i < item.getBars().size(); i++) {
            closePrice[i] = item.getBars().get(i).getClose();
        }

        Core c = new Core();
        // RetCode retCode = c.sma(0, closePrice.length - 1, closePrice, PERIODS_AVERAGE, begin, length, out);
        RetCode retCode = c.rsi(0, item.getBars().size() - 1, closePrice, PERIODS_AVERAGE, begin, length, out);
        if (retCode == RetCode.Success) {

            System.out.println(String.format("%1$s (%2$s) - %3$s", item.getSymbol(), item.getName(), item.getExchange()));
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
                item.getBars().get(i - begin.value).setRsi(out[i - begin.value]);
                System.out.println(item.getBars().get(i - begin.value));
            }

        } else {
            System.out.println("Error");
        }
    }

    private static List<WatchList> getWatchList() {
        List<WatchList> watchListList = new ArrayList<WatchList>();
        watchListList.add(new WatchList("VSTO", "VISTA OUTDOOR INC", "NYSE"));
        watchListList.add(new WatchList("TOL", "TOLL BROTHERS INC", "NYSE"));
        watchListList.add(new WatchList("HII", "HUNTINGTON INGALLS INDS INC", "NYSE"));
        return watchListList;
    }

}
