package iag1.com.utils;

import iag1.com.model.Bar;
import iag1.com.model.Item;
import iag1.com.types.AppConfig;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Report class.
 *
 * Created by dqromney on 9/28/15.
 */
public class Report {

    public static String generateItem(Item pItem, Integer pLength) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder sb = new StringBuilder();

        // Sort by date in ascending order
        pItem.getBars().sort(new Comparator<Bar>() {
            public int compare(Bar o1, Bar o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
        WatchList watchList = pItem.getWatchList();

        sb.append(String.format("\n%1$s - %2$s (%3$s) - %4$d Bars\n", pItem.getSymbol(), pItem.getName(), pItem.getExchange(), pItem.getBars().size()));
        sb.append(String.format("EWR Issue: %1tY-%1$tm \nProfit Potential: %2$1.0f%% \nRisk Level[1(safest)-5(not safe)]: %3$1.2f \nPayoff Period (years): %4$1.2f\n",
                watchList.getEwrIssueDate() == null ? new Date() : watchList.getEwrIssueDate(),
                watchList.getEwrProfitPotential() == null ? 0.0 : watchList.getEwrProfitPotential(),
                watchList.getEwrRiskLevel() == null ? 0.0 : watchList.getEwrRiskLevel(),
                watchList.getEwrPayoffPeriod() == null ? 0.0 : watchList.getEwrPayoffPeriod()));
        sb.append(
                String.format("%1$s\t\t%2$s\t%3$s\t%4$s\t\t%5$s\t%6$s\t%7$s\t%8$s\t\t%9$s\n",
                        AppConfig.YAHOO_EOD_HEADER_DATE.getValue(),
                        AppConfig.YAHOO_EOD_HEADER_OPEN.getValue(),
                        AppConfig.YAHOO_EOD_HEADER_HIGH.getValue(),
                        AppConfig.YAHOO_EOD_HEADER_LOW.getValue(),
                        AppConfig.YAHOO_EOD_HEADER_CLOSE.getValue(),
                        AppConfig.YAHOO_EOD_HEADER_VOLUME.getValue(),
                        AppConfig.YAHOO_EOD_HEADER_ADJ_CLOSE.getValue(),
                        AppConfig.YAHOO_EOD_HEADER_RSI.getValue(),
                        AppConfig.YAHOO_EOD_HEADER_SMA.getValue())
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
            sb.append(String.format("%1$.2f", bar.getRsi()) + "\t");
            sb.append(String.format("%1$.2f", bar.getSma()) + "\n");
        }
        return sb.toString();
    }

    public static String generateHtmlItem(Item pItem, Integer pLength) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder sb = new StringBuilder();

        // Sort by date in ascending order
        pItem.getBars().sort(new Comparator<Bar>() {
            public int compare(Bar o1, Bar o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
        WatchList watchList = pItem.getWatchList();

        sb.append(String.format("<TR align='left'><TH colspan='9' >%1$s - %2$s (%3$s) - %4$d Bars</TH></TR>\n",
                pItem.getSymbol(), pItem.getName(), pItem.getExchange(), pItem.getBars().size()));
        sb.append(String.format("<TR align='left'><TH colspan='9' >EWR Issue: %1tY-%1$tm Profit Potential: %2$1.0f%% Risk Level[1(safest)-5]: %3$1.2f Payoff Period (years): %4$1.2f</TH></TR>",
                watchList.getEwrIssueDate() == null ? new Date() : watchList.getEwrIssueDate(),
                watchList.getEwrProfitPotential() == null ? 0.0 : watchList.getEwrProfitPotential(),
                watchList.getEwrRiskLevel() == null ? 0.0 : watchList.getEwrRiskLevel(),
                watchList.getEwrPayoffPeriod() == null ? 0.0 : watchList.getEwrPayoffPeriod()));
        sb.append("<TR style='border: 1px solid black; border-collapse: collapse;'>");
        sb.append(
                String.format("<TH>%1$s</TH><TH>%2$s</TH><TH>%3$s</TH><TH>%4$s</TH><TH>%5$s</TH><TH>%6$s</TH><TH>%7$s</TH><TH>%8$s</TH><TH>%9$s</TH>",
                        AppConfig.YAHOO_EOD_HEADER_DATE.getValue(),
                        AppConfig.YAHOO_EOD_HEADER_OPEN.getValue(),
                        AppConfig.YAHOO_EOD_HEADER_HIGH.getValue(),
                        AppConfig.YAHOO_EOD_HEADER_LOW.getValue(),
                        AppConfig.YAHOO_EOD_HEADER_CLOSE.getValue(),
                        AppConfig.YAHOO_EOD_HEADER_VOLUME.getValue(),
                        AppConfig.YAHOO_EOD_HEADER_ADJ_CLOSE.getValue(),
                        AppConfig.YAHOO_EOD_HEADER_RSI.getValue(),
                        AppConfig.YAHOO_EOD_HEADER_SMA.getValue())
        );
        sb.append("</TR>\n");

        int count = pLength;
        Double previousSma = 0.0;
        for(Bar bar: pItem.getBars()) {
            if (--count < 0) {
                break;
            }
            sb.append("<TR style='border: 1px solid black; border-collapse: collapse;'>");
            sb.append(String.format("<TD>%1$s</TD>", fmt.format(bar.getDate())));
            sb.append(String.format("<TD align='right'>%1$.2f</TD>", bar.getOpen()));
            sb.append(String.format("<TD align='right'>%1$.2f</TD>", bar.getHigh()));
            sb.append(String.format("<TD align='right'>%1$.2f</TD>", bar.getLow()));
            sb.append(String.format("<TD align='right'>%1$.2f</TD>", bar.getClose()));
            sb.append(String.format("<TD align='right'>%1$6d</TD>", bar.getVolume()));
            sb.append(String.format("<TD align='right'>%1$.2f</TD>", bar.getAdjClose()));
            sb.append(String.format("<TD align='right' style='color:%1$s;'>%2$.2f</TD>",
                    bar.getRsi() < 40.0 ? "red" : bar.getRsi() > 60.0 ? "blue" : "black", bar.getRsi()));
            sb.append(String.format("<TD align='right' style='color:%1$s;'>%2$.2f</TD>",
                    bar.getSma() >= previousSma ? "red" : bar.getSma() < previousSma ? "blue" : "black", bar.getSma()));
            sb.append("</TR>\n");
            previousSma = bar.getSma();
        }
        return sb.toString();
    }
}
