package iag1.com;

import com.sun.deploy.util.StringUtils;
import iag1.com.analytics.Technical;
import iag1.com.service.DataService;
import iag1.com.types.SortOrder;
import iag1.com.types.TechnicalEnums;
import iag1.com.utils.Email;
import iag1.com.utils.ReadConfig;
import iag1.com.utils.Report;

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
        String report = null;
        List<Bar> barList;
        Item item;
        sb.append("<!DOCTYPE HTML><HTML><HEADER></HEADER><BODY><TABLE style=\"width:100%\">");
        for(WatchList watch: getWatchList()) {
            barList = dataService.getAllHistory(watch.getSymbol(), SortOrder.ASC);
            barList = Technical.rsi(barList, TechnicalEnums.RSI_PERIOD_AVERAGE_DEFAULT.getValue());
            item = new Item(watch.getSymbol(), watch.getName(), watch.getExchange(), barList);
            System.out.println(Report.generateItem(item, 5));
            sb.append(Report.generateHtmlItem(item, 5));
        }
        sb.append("</TABLE></BODY></HTML>");
        // Email report to myself
        Email email = new Email();
        email.sendEmail("dqromney@gmail.com", "Daily Stock RSI Report", sb.toString());
    }

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

//        watchList.add(new WatchList("RTN", "RAYTHEON CO COM NEW", "NYSE"));
//        watchList.add(new WatchList("TOL", "TOLL BROTHERS INC", "NYSE"));
//        watchList.add(new WatchList("BMI", "BADGER METER INC COM", "NYSE"));
//        watchList.add(new WatchList("OA", "ORBITAL ATK INC COM", "NYSE"));
//        watchList.add(new WatchList("LMT", "LOCKHEED MARTIN CORP COM", "NYSE"));
//        watchList.add(new WatchList("CACI", "CACI INTL INC CL A", "NYSE"));
//        watchList.add(new WatchList("VSTO", "VISTA OUTDOOR INC COM", "NYSE"));
//        watchList.add(new WatchList("SSNI", "SILVER SPRING NETWORKS INC COM", "NYSE"));
//        watchList.add(new WatchList("GD", "GENERAL DYNAMICS CORP COM", "NYSE"));
//        watchList.add(new WatchList("FSDAX", "FIDELITY?? SELECT DEFENSE & AERO PORT", "NYSE"));
//        watchList.add(new WatchList("TRMB", "TRIMBLE NAVIGATION LTD COM", "NYSE"));
//        watchList.add(new WatchList("HII", "HUNTINGTON INGALLS INDS INC", "NYSE"));
//        watchList.add(new WatchList("BHP", "BHP BILLITON LTD SPONSORED ADR", "NYSE"));
//        watchList.add(new WatchList("BGC", "GENERAL CABLE CORP NEW COM", "NYSE"));
//        watchList.add(new WatchList("PWR", "QUANTA SVCS INC COM", "NYSE"));
        return watchList;
    }

}
