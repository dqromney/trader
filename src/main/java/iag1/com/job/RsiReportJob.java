package iag1.com.job;

import iag1.com.analytics.Technical;
import iag1.com.model.Bar;
import iag1.com.model.Item;
import iag1.com.service.DataService;
import iag1.com.types.SortOrder;
import iag1.com.types.TechnicalEnums;
import iag1.com.utils.Email;
import iag1.com.utils.Report;
import iag1.com.utils.WatchList;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Relative Strength Index job.
 *
 * Created by dqromney on 10/2/15.
 */
public class RsiReportJob implements Job {

    private Boolean sendEmail = Boolean.FALSE;
    private static Integer SHOW_ROW_HISTORY_COUNT = 5;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Logger logger = Logger.getLogger(RsiReportJob.class.getName());
        logger.info(String.format("%1$s started, email enabled: [%2$s]",
                RsiReportJob.class.getSimpleName(),
                getSendEmail()));

        DataService dataService = new DataService();
        StringBuilder sb = new StringBuilder();
        List<Bar> barList;
        Item item;
        sb.append("<!DOCTYPE HTML><HTML><HEADER></HEADER><BODY>")
                .append("<TABLE style='width:100%; border: 1px solid black; border-collapse: collapse;'>");
        // 14 day RSI + 5 (visible history) + 5 (extra history) + 3 days extra due to weekends and holidays.
        Integer requiredDays = TechnicalEnums.RSI_PERIOD_AVERAGE_DEFAULT.getValue() + (2 * SHOW_ROW_HISTORY_COUNT) + 3;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, (-1 * requiredDays));
        Date fromDate = cal.getTime();

        List<WatchList> watchList;
        try {
            watchList = iag1.com.repository.WatchList.getWatchList();
        } catch (IOException e) {
            throw new JobExecutionException("Unable to load watchlist", e);
        } catch (ParseException e) {
            throw new JobExecutionException("Unable to parse watchlist file", e);
        }

        for(WatchList watch: watchList) {
            try {
                barList = dataService.getHistoryFromDate(watch.getSymbol(), fromDate, SortOrder.ASC);
            } catch (IOException e) {
                throw new JobExecutionException("Unable to load history data", e);
            } catch (ParseException e) {
                throw new JobExecutionException("Unable to parse history data", e);
            }
            barList = Technical.rsi(barList, TechnicalEnums.RSI_PERIOD_AVERAGE_DEFAULT.getValue());
            item = new Item(watch, barList);
            System.out.print(".");
            System.out.print(Report.generateItem(item, SHOW_ROW_HISTORY_COUNT));
            sb.append(Report.generateHtmlItem(item, SHOW_ROW_HISTORY_COUNT));
        }
        System.out.println();
        sb.append("</TABLE></BODY></HTML>");

        if(sendEmail) {
            // Email report to myself
            Email email = new Email();
            try {
                email.sendEmail("dqromney@gmail.com", "Daily Stock RSI Report", sb.toString());
            } catch (IOException e) {
                throw new JobExecutionException("Unable to send report email", e);
            }
        }
        logger.info(String.format("%1$s ended, email enabled: [%2$s]",
                RsiReportJob.class.getSimpleName(),
                getSendEmail()));
    }

    // ----------------------------------------------------------------
    // Access methods - set with .usingJobData(key, value);
    // ----------------------------------------------------------------
    public Boolean getSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(Boolean sendEmail) {
        this.sendEmail = sendEmail;
    }
}
