package iag1.com.repository;

import iag1.com.utils.ReadConfig;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Watch list repository.
 * <p/>
 * Created by dqromney on 10/2/15.
 */
public class WatchList {

    /**
     * Gets a watch list of stocks.
     *
     * @return a list of {@link iag1.com.utils.WatchList} objects
     * @throws IOException
     */
    public static List<iag1.com.utils.WatchList> getWatchList() throws IOException, ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        List<iag1.com.utils.WatchList> watchList = new ArrayList<iag1.com.utils.WatchList>();
        ReadConfig readConfig = new ReadConfig("watchList.properties");
        Properties props = readConfig.getPropValues();
        String value;
        for (int i = 1; i < 1000; i++) {
            String key = String.format("watch.list.%1$03d", i);
            value = (String) props.get(key);
            if (value != null) {
                // TODO Clean this up with opencsv
                String[] items = StringUtils.split(value, "|");
                // # Watch List (Symbol|Name|Exchange|EWR Issue Date[YYYY-MM-DD]|EWR Risk Level|EWR Profit Potential| EWR Payoff Period in years)
                iag1.com.utils.WatchList wlItem = null;
                if (items.length == 3) {
                    wlItem = new iag1.com.utils.WatchList.WatchListBuilder(items[0], items[1], items[2]).build();
                } else {
                    wlItem = new iag1.com.utils.WatchList.WatchListBuilder(items[0], items[1], items[2])
                            .ewrIssueDate(items[3] == null ? null : fmt.parse(items[3]))
                            .ewrRiskLevel(new Double(items[4]))
                            .ewrProfitPotential(new Double(items[5]))
                            .ewrPayoffPeriod(new Double(items[6]))
                            .build();
                }
                watchList.add(wlItem);
            } else {
                break;
            }
        }
        return watchList;
    }

}
