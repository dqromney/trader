package iag1.com;

import au.com.bytecode.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public FileLoader(String pImportDir, String pFilename) throws FileNotFoundException {
        csvReader = new CSVReader(new FileReader(pImportDir + '/' + pFilename), ',','"',0);
    }

    public FileLoader(Reader pReader) throws FileNotFoundException {
        csvReader = new CSVReader(pReader, ',', '"', 0);
    }

    public Item read() throws IOException, ParseException {
        Item item = new Item("UNKNOWN", "DESC");
        List<String> headerList = Arrays.asList(csvReader.readNext());
        String [] nextLine;
        while ((nextLine = csvReader.readNext()) != null) {
            // nextLine[] is an array of values from the line
            Bar bar = new Bar();
            // Date,Open,High,Low,Close,Volume,Adj Close

            bar.setDate(fmt.parse(nextLine[headerList.indexOf("Date")]));
            bar.setOpen(new Double(nextLine[headerList.indexOf("Open")]));
            bar.setHigh(new Double(nextLine[headerList.indexOf("High")]));
            bar.setLow(new Double(nextLine[headerList.indexOf("Low")]));
            bar.setClose(new Double(nextLine[headerList.indexOf("Close")]));
            bar.setVolume(new Long(nextLine[headerList.indexOf("Volume")]));
            bar.setAdjClose(new Double(nextLine[headerList.indexOf("Adj Close")]));
            item.getBars().add(bar);
            System.out.println(bar.toString());
        }
        return item;
    }
}
