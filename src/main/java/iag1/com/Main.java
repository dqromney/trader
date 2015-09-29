package iag1.com;

import com.sun.deploy.util.StringUtils;
import iag1.com.analytics.Technical;
import iag1.com.model.Bar;
import iag1.com.model.Item;
import iag1.com.service.DataService;
import iag1.com.types.SortOrder;
import iag1.com.types.TechnicalEnums;
import iag1.com.utils.Email;
import iag1.com.utils.ReadConfig;
import iag1.com.utils.Report;
import iag1.com.utils.WatchList;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
        StringBuilder sb = new StringBuilder();
        List<Bar> barList;
        Item item;
        sb.append("<!DOCTYPE HTML><HTML><HEADER></HEADER><BODY><TABLE style=\"width:100%\">");
        for(WatchList watch: getWatchList()) {
            barList = dataService.getAllHistory(watch.getSymbol(), SortOrder.ASC);
            barList = Technical.rsi(barList, TechnicalEnums.RSI_PERIOD_AVERAGE_DEFAULT.getValue());
            item = new Item(watch.getSymbol(), watch.getName(), watch.getExchange(), barList);
            System.out.print(Report.generateItem(item, 5));
            sb.append(Report.generateHtmlItem(item, 5));
        }
        sb.append("</TABLE></BODY></HTML>");
        // Email report to myself
        Email email = new Email();
        email.sendEmail("dqromney@gmail.com", "Daily Stock RSI Report", sb.toString());
    }

    /**
     * Gets a watch list of stocks.
     *
     * @return a list of {@link WatchList} objects
     * @throws IOException
     */
    private static List<WatchList> getWatchList() throws IOException {
        List<WatchList> watchList = new ArrayList<WatchList>();
        ReadConfig readConfig = new ReadConfig("watchList.properties");
        Properties props = readConfig.getPropValues();
        String value;
        for(int i = 1; i < 1000; i++) {
            String key = String.format("watch.list.%1$03d", i);
            value = (String)props.get(key);
            if (value != null) {
                String[] items = StringUtils.splitString(value, "|");
                watchList.add(new WatchList(items[0], items[1], items[2]));
            } else {
                break;
            }
        }
        return watchList;
    }

}
