package iag1.com;

import iag1.com.analytics.Technical;
import iag1.com.service.DataService;
import iag1.com.types.AppConfig;
import iag1.com.types.SortOrder;
import iag1.com.types.TechnicalEnums;

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
        StringBuilder sb = new StringBuilder();
        List<Bar> barList;
        Item item;
        sb.append("<!DOCTYPE HTML><HTML><HEADER></HEADER><BODY><TABLE style=\"width:100%\">");
        for(WatchList watch: getWatchList()) {
            barList = dataService.getAllHistory(watch.getSymbol(), SortOrder.DESC);
            barList = Technical.rsi(barList, TechnicalEnums.RSI_PERIOD_AVERAGE_DEFAULT.getValue());
            item = new Item(watch.getSymbol(), watch.getName(), watch.getExchange(), barList);
            displayItem(item, 5);
            sb.append(generateHtmlItem(item, 5));
        }
        sb.append("</TABLE></BODY></HTML>");
        // Email report to myself
        //Email email = new Email();
        //email.sendEmail("dqromney@gmail.com", "Daily Stock RSI Report", sb.toString());
    }

    private static void displayItem(Item pItem, Integer pLength) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(String.format("\n%1$s - %2$s (%3$s) - %4$d Bars", pItem.getSymbol(), pItem.getName(), pItem.getExchange(), pItem.getBars().size()));
        System.out.println(
                String.format("%1$s\t\t%2$s\t%3$s\t%4$s\t\t%5$s\t%6$s\t%7$s\t%8$s",
                        AppConfig.YAHOO_EOD_HEADER_DATE.getValue(),
                        AppConfig.YAHOO_EOD_HEADER_OPEN.getValue(),
                        AppConfig.YAHOO_EOD_HEADER_HIGH.getValue(),
                        AppConfig.YAHOO_EOD_HEADER_LOW.getValue(),
                        AppConfig.YAHOO_EOD_HEADER_CLOSE.getValue(),
                        AppConfig.YAHOO_EOD_HEADER_VOLUME.getValue(),
                        AppConfig.YAHOO_EOD_HEADER_ADJ_CLOSE.getValue(),
                        AppConfig.YAHOO_EOD_HEADER_RSI.getValue())
        );
        int count = pLength;
        for(Bar bar: pItem.getBars()) {
            if (--count < 0) {
                break;
            }
            System.out.print(fmt.format(bar.getDate()) + "\t");
            System.out.print(String.format("%1$.2f", bar.getOpen()) + "\t");
            System.out.print(String.format("%1$.2f", bar.getHigh()) + "\t");
            System.out.print(String.format("%1$.2f", bar.getLow()) + "\t");
            System.out.print(String.format("%1$.2f", bar.getClose()) + "\t");
            System.out.print(String.format("%1$6d", bar.getVolume()) + "\t");
            System.out.print(String.format("%1$.2f", bar.getAdjClose()) + "\t\t");
            System.out.print(String.format("%1$.2f", bar.getRsi()) + "\n");
        }
    }

    private static String generateItem(Item pItem, Integer pLength) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("\n%1$s - %2$s (%3$s) - %4$d Bars\n", pItem.getSymbol(), pItem.getName(), pItem.getExchange(), pItem.getBars().size()));
        sb.append(
                String.format("%1$s\t\t%2$s\t%3$s\t%4$s\t\t%5$s\t%6$s\t%7$s\t%8$s\n",
                        AppConfig.YAHOO_EOD_HEADER_DATE.getValue(),
                        AppConfig.YAHOO_EOD_HEADER_OPEN.getValue(),
                        AppConfig.YAHOO_EOD_HEADER_HIGH.getValue(),
                        AppConfig.YAHOO_EOD_HEADER_LOW.getValue(),
                        AppConfig.YAHOO_EOD_HEADER_CLOSE.getValue(),
                        AppConfig.YAHOO_EOD_HEADER_VOLUME.getValue(),
                        AppConfig.YAHOO_EOD_HEADER_ADJ_CLOSE.getValue(),
                        AppConfig.YAHOO_EOD_HEADER_RSI.getValue())
        );
        int count = pLength;
        for(Bar bar: pItem.getBars()) {
            if (--count < 0) {
                break;
            }
            sb.append(fmt.format(bar.getDate())+"\t");
            sb.append(String.format("%1$.2f", bar.getOpen()) + "\t");
            sb.append(String.format("%1$.2f", bar.getHigh()) + "\t");
            sb.append(String.format("%1$.2f", bar.getLow()) + "\t");
            sb.append(String.format("%1$.2f", bar.getClose()) + "\t");
            sb.append(String.format("%1$6d", bar.getVolume()) + "\t");
            sb.append(String.format("%1$.2f", bar.getAdjClose()) + "\t\t");
            sb.append(String.format("%1$.2f", bar.getRsi()) + "\n");
        }
        return sb.toString();
    }

    private static String generateHtmlItem(Item pItem, Integer pLength) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("<TR colspan=\"8\"><TH>%1$s - %2$s (%3$s) - %4$d Bars</TH></TR>\n", pItem.getSymbol(), pItem.getName(), pItem.getExchange(), pItem.getBars().size()));
        sb.append("<TR>");
        sb.append(
                String.format("<TH>%1$s</TH><TH>%2$s</TH><TH>%3$s</TH><TH>%4$s</TH><TH>%5$s</TH><TH>%6$s</TH><TH>%7$s</TH><TH>%8$s</TH>",
                        AppConfig.YAHOO_EOD_HEADER_DATE.getValue(),
                        AppConfig.YAHOO_EOD_HEADER_OPEN.getValue(),
                        AppConfig.YAHOO_EOD_HEADER_HIGH.getValue(),
                        AppConfig.YAHOO_EOD_HEADER_LOW.getValue(),
                        AppConfig.YAHOO_EOD_HEADER_CLOSE.getValue(),
                        AppConfig.YAHOO_EOD_HEADER_VOLUME.getValue(),
                        AppConfig.YAHOO_EOD_HEADER_ADJ_CLOSE.getValue(),
                        AppConfig.YAHOO_EOD_HEADER_RSI.getValue())
        );
        sb.append("</TR>\n");

        int count = pLength;
        for(Bar bar: pItem.getBars()) {
            if (--count < 0) {
                break;
            }
            sb.append("<TR><TD>");
            sb.append(fmt.format(bar.getDate())+"</TD><TD>");
            sb.append(String.format("%1$.2f", bar.getOpen()) + "</TD><TD>");
            sb.append(String.format("%1$.2f", bar.getHigh()) + "</TD><TD>");
            sb.append(String.format("%1$.2f", bar.getLow()) + "</TD><TD>");
            sb.append(String.format("%1$.2f", bar.getClose()) + "</TD><TD>");
            sb.append(String.format("%1$6d", bar.getVolume()) + "</TD><TD>");
            sb.append(String.format("%1$.2f", bar.getAdjClose()) + "</TD><TD>");
            sb.append(String.format("%1$.2f", bar.getRsi()) + "</TD>");
            sb.append("</TR>\n");
        }
        return sb.toString();
    }

    private static List<WatchList> getWatchList() {
        List<WatchList> watchListList = new ArrayList<WatchList>();
        watchListList.add(new WatchList("RTN", "RAYTHEON CO COM NEW", "NYSE"));
        watchListList.add(new WatchList("TOL", "TOLL BROTHERS INC", "NYSE"));
        watchListList.add(new WatchList("BMI", "BADGER METER INC COM", "NYSE"));
        watchListList.add(new WatchList("OA", "ORBITAL ATK INC COM", "NYSE"));
        watchListList.add(new WatchList("LMT", "LOCKHEED MARTIN CORP COM", "NYSE"));
        watchListList.add(new WatchList("CACI", "CACI INTL INC CL A", "NYSE"));
        watchListList.add(new WatchList("VSTO", "VISTA OUTDOOR INC COM", "NYSE"));
        watchListList.add(new WatchList("SSNI", "SILVER SPRING NETWORKS INC COM", "NYSE"));
        watchListList.add(new WatchList("GD", "GENERAL DYNAMICS CORP COM", "NYSE"));
        watchListList.add(new WatchList("FSDAX", "FIDELITY?? SELECT DEFENSE & AERO PORT", "NYSE"));
        watchListList.add(new WatchList("TRMB", "TRIMBLE NAVIGATION LTD COM", "NYSE"));
        watchListList.add(new WatchList("HII", "HUNTINGTON INGALLS INDS INC", "NYSE"));
        watchListList.add(new WatchList("BHP", "BHP BILLITON LTD SPONSORED ADR", "NYSE"));
        watchListList.add(new WatchList("BGC", "GENERAL CABLE CORP NEW COM", "NYSE"));
        watchListList.add(new WatchList("PWR", "QUANTA SVCS INC COM", "NYSE"));
        return watchListList;
    }

}
