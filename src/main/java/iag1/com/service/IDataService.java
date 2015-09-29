package iag1.com.service;

import iag1.com.model.Bar;
import iag1.com.types.SortOrder;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Data Service interface.
 *
 * Created by dqromney on 9/24/15.
 */
public interface IDataService {
    List<Bar> getAllHistory(String pSymbol, SortOrder pSortOrder) throws IOException, ParseException;
}
