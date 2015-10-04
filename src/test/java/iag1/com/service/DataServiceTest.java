package iag1.com.service;

import iag1.com.model.Bar;
import iag1.com.types.SortOrder;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Data Service tets.
 *
 * Created by dqromney on 10/4/15.
 */
public class DataServiceTest extends TestCase {

    DataService dataService;
    private static String testSymbol = "OA";

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DataServiceTest( String testName )
    {
        super( testName );
        dataService = new DataService();
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( DataServiceTest.class );
    }

    /**
     * getAllHistory Test.
     */
    public void testGetAllHistory() throws IOException, ParseException {
        List<Bar> barList = dataService.getAllHistory(testSymbol, SortOrder.ASC);
        assertTrue( "Should be greater than zero size", barList.size() > 0 );
    }

    /**
     * getFromHistory Test.
     */
    public void testGetFromHistory() throws IOException, ParseException {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -3);
        Date fromDate = cal.getTime();
        List<Bar> barList = dataService.getHistoryFromDate(testSymbol, fromDate, SortOrder.ASC);
        assertTrue( "Should be less than 3 days of data", barList.size() < 3 );
    }

    /**
     * getFromHistory Test.
     */
    public void testGetFromToHistory() throws IOException, ParseException {
        Calendar cal = Calendar.getInstance();
        // From date
        cal.set(Calendar.YEAR, 2015);
        cal.set(Calendar.MONTH, Calendar.OCTOBER);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date fromDate = cal.getTime();
        // To date
        cal.set(Calendar.YEAR, 2015);
        cal.set(Calendar.MONTH, Calendar.OCTOBER);
        cal.set(Calendar.DAY_OF_MONTH, 2);
        Date toDate = cal.getTime();
        // Get the bars
        List<Bar> barList = dataService.getHistoryFromToDate(testSymbol, fromDate, toDate, SortOrder.ASC);
        assertTrue( "Should be only 2 day of data", barList.size() == 2 );
    }

}
