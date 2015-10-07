package iag1.com;

import iag1.com.job.RsiReportJob;
import jargs.gnu.CmdLineParser;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.text.ParseException;
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

    /**
     * Register cron-job jobs.
     *
     * @throws SchedulerException if there is a problem.
     */
    private void registerJobs() throws SchedulerException {
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        RegisterJob registerJob = new RegisterJob().invoke(
                "rsiReport",
                "reports",
                "0 0 * * 1-5 ?", //Each Mon-Fri at midnight
                // "0 0/1 * * * ?", // Every 1 minutes
                "Retrieves wish-list data, calculates RSI, and emails report.");
        JobDetail job = registerJob.getJob();
        Trigger trigger = registerJob.getTrigger();

        // Schedule the job
        scheduler.start();
        scheduler.scheduleJob(job, trigger);
    }

    /**
     * Initialize the application.
     *
     * @param args the {@link String[]}commend line arguments.
     */
    private void initialize(String[] args) {
        doArgs(args);
    }

    /**
     * Process the command-line arguments.
     *
     * @param args the {@link String[]}commend line arguments.
     */
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

    /**
     * Print application command-line usage.
     */
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
     * Register a job.
     */
    private class RegisterJob {
        private JobDetail job;
        private Trigger trigger;

        public JobDetail getJob() {
            return job;
        }

        public Trigger getTrigger() {
            return trigger;
        }

        public RegisterJob invoke(String pName, String pGroup, String pCronPattern, String pDescription) {
            // Define the RSI Report job
            job = JobBuilder.newJob(RsiReportJob.class).withIdentity(pName, pGroup).build();
            // Trigger the job to run on the next round minute
            trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity(pName, pGroup)
                    // .withSchedule(CronScheduleBuilder.cronSchedule("0/50 * * * * ?")) // Each 50 seconds
                    .withSchedule(CronScheduleBuilder.cronSchedule(pCronPattern))
                    .withDescription(pDescription)
                    .usingJobData("sendEmail", Boolean.TRUE)
                    .build();
            return this;
        }
    }
}
