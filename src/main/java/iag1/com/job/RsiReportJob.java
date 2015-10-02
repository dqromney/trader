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
import java.util.List;

/**
 * Relative Strength Index job.
 *
 * Created by dqromney on 10/2/15.
 */
public class RsiReportJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        DataService dataService = new DataService();
        StringBuilder sb = new StringBuilder();
        List<Bar> barList;
        Item item;
        sb.append("<!DOCTYPE HTML><HTML><HEADER></HEADER><BODY><TABLE style='width:100%; border: 1px solid black; border-collapse: collapse;'>");

        List<WatchList> watchList = null;
        try {
            watchList = iag1.com.repository.WatchList.getWatchList();
        } catch (IOException e) {
            throw new JobExecutionException("Unable to load watchlist", e);
        } catch (ParseException e) {
            throw new JobExecutionException("Unable to parse watchlist file", e);
        }

        for(WatchList watch: watchList) {
            try {
                barList = dataService.getAllHistory(watch.getSymbol(), SortOrder.ASC);
            } catch (IOException e) {
                throw new JobExecutionException("Unable to load history data", e);
            } catch (ParseException e) {
                throw new JobExecutionException("Unable to parse history data", e);
            }
            barList = Technical.rsi(barList, TechnicalEnums.RSI_PERIOD_AVERAGE_DEFAULT.getValue());
            item = new Item(watch, barList);
            System.out.print(Report.generateItem(item, 5));
            sb.append(Report.generateHtmlItem(item, 5));
        }
        sb.append("</TABLE></BODY></HTML>");
        // Email report to myself
        Email email = new Email();
        try {
            email.sendEmail("dqromney@gmail.com", "Daily Stock RSI Report", sb.toString());
        } catch (IOException e) {
            throw new JobExecutionException("Unable to send report email", e);
        }
    }

}
