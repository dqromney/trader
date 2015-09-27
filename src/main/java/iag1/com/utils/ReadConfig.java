package iag1.com.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Read Configuration file.
 *
 * Created by dqromney on 9/26/15.
 */
public class ReadConfig {

    private InputStream inputStream;

    public ReadConfig() {
    }

    public Properties getPropValues() throws IOException {
        Properties prop = null;
        try {
            prop = new Properties();
            String propFileName = "config.properties";
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            if(inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in classpath.");
            }
        }
        catch(Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
        return prop;
    }
}
