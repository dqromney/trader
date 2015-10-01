package iag1.com;

import at.chrl.nutils.cron.CronService;
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
import jargs.gnu.CmdLineParser;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

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

        Main main = new Main();
        main.initialize(args);
        // main.execute();
        CronService.getInstance().schedule(() -> {
            try {
                main.execute();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }, "*/50 * * * * ?");

    }

    private void execute() throws IOException, ParseException {
        DataService dataService = new DataService();
        StringBuilder sb = new StringBuilder();
        List<Bar> barList;
        Item item;
        sb.append("<!DOCTYPE HTML><HTML><HEADER></HEADER><BODY><TABLE style='width:100%; border: 1px solid black; border-collapse: collapse;'>");
        for(WatchList watch: getWatchList()) {
            barList = dataService.getAllHistory(watch.getSymbol(), SortOrder.ASC);
            barList = Technical.rsi(barList, TechnicalEnums.RSI_PERIOD_AVERAGE_DEFAULT.getValue());
            item = new Item(watch, barList);
            System.out.print(Report.generateItem(item, 5));
            sb.append(Report.generateHtmlItem(item, 5));
        }
        sb.append("</TABLE></BODY></HTML>");
        // Email report to myself
        Email email = new Email();
        email.sendEmail("dqromney@gmail.com", "Daily Stock RSI Report", sb.toString());
    }

    private void initialize(String[] args) {
        doArgs(args);
    }

    private void doArgs(String[] args) {
        CmdLineParser parser = new CmdLineParser();
        CmdLineParser.Option option = parser.addStringOption('w', "watchlist");

        try{
            parser.parse(args);
        } catch(CmdLineParser.OptionException oe) {
            printUsage();
            System.exit(2);
        }
    }

    private void printUsage() {
        StringBuilder loggingLevels = new StringBuilder();
        loggingLevels.append(Level.ALL).append(" | ")
                .append(Level.INFO).append(" | ")
                .append(Level.WARNING).append(" | ")
                .append(Level.FINE).append(" | ")
                .append(Level.FINER).append(" | ")
                .append(Level.FINEST).append(" | ")
                .append(Level.OFF);
        System.err.println(
                String.format("Usage java [-w -wishlist] [-l --loggingLevel <" + loggingLevels + ">]\n"));
    }

    /**
     * Gets a watch list of stocks.
     *
     * @return a list of {@link WatchList} objects
     * @throws IOException
     */
    private static List<WatchList> getWatchList() throws IOException, ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        List<WatchList> watchList = new ArrayList<WatchList>();
        ReadConfig readConfig = new ReadConfig("watchList.properties");
        Properties props = readConfig.getPropValues();
        String value;
        for(int i = 1; i < 1000; i++) {
            String key = String.format("watch.list.%1$03d", i);
            value = (String)props.get(key);
            if (value != null) {
                // TODO Clean this up with opencsv
                String[] items = StringUtils.split(value, "|");
                // # Watch List (Symbol|Name|Exchange|EWR Issue Date[YYYY-MM-DD]|EWR Risk Level|EWR Profit Potential| EWR Payoff Period in years)
                WatchList wlItem = null;
                if(items.length == 3) {
                    wlItem = new WatchList.WatchListBuilder(items[0], items[1], items[2]).build();
                } else {
                    wlItem = new WatchList.WatchListBuilder(items[0], items[1], items[2])
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
