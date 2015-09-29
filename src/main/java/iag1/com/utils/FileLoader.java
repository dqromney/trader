package iag1.com.utils;

import au.com.bytecode.opencsv.CSVReader;
import iag1.com.model.Bar;
import iag1.com.types.AppConfig;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * File Loader.
 *
 * Created by dqromney on 9/15/15.
 */
public class FileLoader {

    final String DEFAULT_FILE_SEPARATOR = System.getProperty("file.separator");
    CSVReader csvReader;
    String exportDir = System.getProperty("user.dir");
    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Read from local file.
     *
     * @param pImportDir the {@link String} directory where to import
     * @param pFilename the {@link String} filename in import directory to read
     * @throws FileNotFoundException
     */
    public FileLoader(String pImportDir, String pFilename) throws FileNotFoundException {
        csvReader = new CSVReader(new FileReader(pImportDir + '/' + pFilename), ',','"',0);
    }

    /**
     * Read from a stream reader.
     *
     * @param pReader the {@link Reader} object
     * @throws FileNotFoundException
     */
    public FileLoader(Reader pReader) throws FileNotFoundException {
        csvReader = new CSVReader(pReader, ',', '"', 0);
    }

    /**
     * Read from file or stream the delimited data into a price bar.
     *
     * @return a list of {@link Bar} objects
     * @throws IOException
     * @throws ParseException
     */
    public List<Bar> readBars() throws IOException, ParseException {
        Bar bar;
        List<Bar> barList = new ArrayList<Bar>();
        List<String> headerList = Arrays.asList(csvReader.readNext());
        String [] nextLine;
        while ((nextLine = csvReader.readNext()) != null) {
            bar = new Bar();
            bar.setDate(fmt.parse(nextLine[headerList.indexOf(AppConfig.YAHOO_EOD_HEADER_DATE.getValue())]));
            bar.setOpen(new Double(nextLine[headerList.indexOf(AppConfig.YAHOO_EOD_HEADER_OPEN.getValue())]));
            bar.setHigh(new Double(nextLine[headerList.indexOf(AppConfig.YAHOO_EOD_HEADER_HIGH.getValue())]));
            bar.setLow(new Double(nextLine[headerList.indexOf(AppConfig.YAHOO_EOD_HEADER_LOW.getValue())]));
            bar.setClose(new Double(nextLine[headerList.indexOf(AppConfig.YAHOO_EOD_HEADER_CLOSE.getValue())]));
            bar.setVolume(new Long(nextLine[headerList.indexOf(AppConfig.YAHOO_EOD_HEADER_VOLUME.getValue())]));
            bar.setAdjClose(new Double(nextLine[headerList.indexOf(AppConfig.YAHOO_EOD_HEADER_ADJ_CLOSE.getValue())]));
            barList.add(bar);
            // System.out.println(bar.toString());
        }
        return barList;
    }

}
