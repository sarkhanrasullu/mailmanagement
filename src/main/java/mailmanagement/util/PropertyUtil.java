package mailmanagement.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Sarkhan Rasullu :)
 */
public class PropertyUtil {

    private static final Logger LOG = Logger.getLogger(PropertyUtil.class.getName());

    public static String getProperty(String key, String filename) {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            File f = new File("conf/" + filename);

            input = new FileInputStream(f);
            if (input == null) {
                return null;
            }

            //load a properties file from class path, inside static method
            prop.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String result = prop.getProperty(key);
        if (result != null) {
            result = result.trim();
        }
        LOG.info("key:" + key + ",value:" + result);
        return result;
    }

    public static Integer getPropertyAsInt(String property, String config) {
        try {
            String paramValue = getProperty(property, config);
            if (StringUtils.isNoneBlank(paramValue)) {
                return Integer.parseInt(paramValue);
            }
        } catch (Exception ex) {
        }

        return null;
    }

}
