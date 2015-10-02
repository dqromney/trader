package iag1.com;

import iag1.com.analytics.Technical;
import iag1.com.job.RsiReportJob;
import iag1.com.model.Bar;
import iag1.com.model.Item;
import iag1.com.service.DataService;
import iag1.com.types.SortOrder;
import iag1.com.types.TechnicalEnums;
import iag1.com.utils.Email;
import iag1.com.utils.Report;
import iag1.com.utils.WatchList;
import jargs.gnu.CmdLineParser;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;

/**
 * Main driver class.
 *
 * Cron Format...
 *
 *  * * * * * * *
 *  | | | | | |
 *  | | | | | +-- Year              (range: 1900-3000)
 *  | | | | +---- Day of the Week   (range: 1-7, 1 standing for Monday)
 *  | | | +------ Month of the Year (range: 1-12)
 *  | | +-------- Day of the Month  (range: 1-31)
 *  | +---------- Hour              (range: 0-23)
 *  +------------ Minute            (range: 0-59)
 */
 //  Any of these 6 fields may be an asterisk (*). This would mean the entire range of possible values, i.e.
 //  each minute, each hour, etc. In the first four fields, nnCron users can also use "nonstandard" character ?
 //  (question mark), described here.
 //
 //  Any field may contain a list of values separated by commas, (e.g. 1,3,7) or a range of values (two integers
 //  separated by a hyphen, e.g. 1-5).
 //
 //  After an asterisk (*) or a range of values, you can use character / to specify that values are repeated over and
 //  over with a certain interval between them. For example, you can write '0-23/2' in Hour field to specify that some
 //  action should be performed every two hours (it will have the same effect as '0,2,4,6,8,10,12,14,16,18,20,22');
 //  value '*/4' in Minute field means that the action should be performed every 4 minutes, '1-30/3' means
 //  the same as '1,4,7,10,13,16,19,22,25,28'.
 //
 //  In Month and Day of Week fields, you can use names of months or days of weeks abbreviated to first three
 //  letters ('Jan,Feb,...,Dec' or 'Mon,Tue,...,Sun') instead of their numeric values.
public class Main {

    public static void main(String[] args) throws ParseException, IOException, SchedulerException {
        Main main = new Main();
        main.initialize(args);
        main.registerJobs();
    }

    private void registerJobs() throws SchedulerException {
        // Define the RSI Report job
        JobDetail job = JobBuilder.newJob(RsiReportJob.class).withIdentity("rsiReportJob", "reports").build();
        // Trigger the job to run on the next round minute
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("rsiReport", "reports")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(300).repeatForever())
                .build();
        // Schedule the job
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, trigger);
    }

    /**
     * Email RSI Report.
     *
     * @throws IOException
     * @throws ParseException
     */
    private void emailRsiReport() throws IOException, ParseException {
        DataService dataService = new DataService();
        StringBuilder sb = new StringBuilder();
        List<Bar> barList;
        Item item;
        sb.append("<!DOCTYPE HTML><HTML><HEADER></HEADER><BODY><TABLE style='width:100%; border: 1px solid black; border-collapse: collapse;'>");
        for(WatchList watch: iag1.com.repository.WatchList.getWatchList()) {
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
//    private static List<WatchList> getWatchList() throws IOException, ParseException {
//        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
//        List<WatchList> watchList = new ArrayList<WatchList>();
//        ReadConfig readConfig = new ReadConfig("watchList.properties");
//        Properties props = readConfig.getPropValues();
//        String value;
//        for(int i = 1; i < 1000; i++) {
//            String key = String.format("watch.list.%1$03d", i);
//            value = (String)props.get(key);
//            if (value != null) {
//                // TODO Clean this up with opencsv
//                String[] items = StringUtils.split(value, "|");
//                // # Watch List (Symbol|Name|Exchange|EWR Issue Date[YYYY-MM-DD]|EWR Risk Level|EWR Profit Potential| EWR Payoff Period in years)
//                WatchList wlItem = null;
//                if(items.length == 3) {
//                    wlItem = new WatchList.WatchListBuilder(items[0], items[1], items[2]).build();
//                } else {
//                    wlItem = new WatchList.WatchListBuilder(items[0], items[1], items[2])
//                            .ewrIssueDate(items[3] == null ? null : fmt.parse(items[3]))
//                            .ewrRiskLevel(new Double(items[4]))
//                            .ewrProfitPotential(new Double(items[5]))
//                            .ewrPayoffPeriod(new Double(items[6]))
//                            .build();
//                }
//                watchList.add(wlItem);
//            } else {
//                break;
//            }
//        }
//        return watchList;
//    }

}
