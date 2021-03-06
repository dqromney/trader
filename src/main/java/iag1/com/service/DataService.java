package iag1.com.service;

import iag1.com.model.Bar;
import iag1.com.repository.EndOfDay;
import iag1.com.types.SortOrder;

import java.io.IOException;
import java.text.ParseException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Data Service implementation.
 *
 * Created by dqromney on 9/24/15.
 */
public class DataService implements IDataService {
    EndOfDay endOfDay = null;

    public DataService() {
        endOfDay = new EndOfDay();
    }

    public List<Bar> getAllHistory(String pSymbol, SortOrder pDateSortOrder) throws IOException, ParseException {
        List<Bar> barList = endOfDay.all(pSymbol);
        if (pDateSortOrder.equals(SortOrder.DESC)) {
            // Sort by date in descending order
            barList.sort(new Comparator<Bar>() {
                public int compare(Bar o2, Bar o1) {
                    return o1.getDate().compareTo(o2.getDate());
                }
            });
        } else {
            // Sort by date in ascending order
            barList.sort(new Comparator<Bar>() {
                public int compare(Bar o1, Bar o2) {
                    return o1.getDate().compareTo(o2.getDate());
                }
            });
        }

        return barList;
    }

    public List<Bar> getHistoryFromDate(String pSymbol, Date pFromDate, SortOrder pDateSortOrder) throws IOException, ParseException {
        List<Bar> barList = endOfDay.fromDate(pSymbol, pFromDate);
        if (pDateSortOrder.equals(SortOrder.DESC)) {
            // Sort by date in descending order
            barList.sort(new Comparator<Bar>() {
                public int compare(Bar o2, Bar o1) {
                    return o1.getDate().compareTo(o2.getDate());
                }
            });
        } else {
            // Sort by date in ascending order
            barList.sort(new Comparator<Bar>() {
                public int compare(Bar o1, Bar o2) {
                    return o1.getDate().compareTo(o2.getDate());
                }
            });
        }

        return barList;
    }

    public List<Bar> getHistoryFromToDate(String pSymbol, Date pFromDate, Date pToDate, SortOrder pDateSortOrder) throws IOException, ParseException {
        List<Bar> barList = endOfDay.fromToDate(pSymbol, pFromDate, pToDate);
        if (pDateSortOrder.equals(SortOrder.DESC)) {
            // Sort by date in descending order
            barList.sort(new Comparator<Bar>() {
                public int compare(Bar o2, Bar o1) {
                    return o1.getDate().compareTo(o2.getDate());
                }
            });
        } else {
            // Sort by date in ascending order
            barList.sort(new Comparator<Bar>() {
                public int compare(Bar o1, Bar o2) {
                    return o1.getDate().compareTo(o2.getDate());
                }
            });
        }

        return barList;
    }

}
