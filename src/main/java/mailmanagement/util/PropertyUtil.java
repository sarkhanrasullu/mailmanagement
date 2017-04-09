package mailmanagement.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Sarkhan Rasullu
 */
public class PropertyUtil {

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
        System.out.println("key:" + key + ",value:" + result);
        return result;
    }

    public static long getPropertyAsLong(String property, String config, long default_) {
        long val = default_;
        try {
            String paramValue = getProperty(property, config);
            val = Integer.parseInt(paramValue);
        } catch (Exception ex) {
            val = default_;
        }

        return val;
    }

    public static int getPropertyAsInt(String property, String config, int default_) {
        int val = default_;
        try {
            String paramValue = getProperty(property, config);
            val = Integer.parseInt(paramValue);
        } catch (Exception ex) {
            val = default_;
        }

        return val;
    }

    public static boolean getPropertyAsBoolean(String property, String config) {
        boolean val = false;
        try {
            String paramValue = getProperty(property, config);
            val = Boolean.parseBoolean(paramValue);
        } catch (Exception ex) {

        }

        return val;
    }

    public static String getPropertyAsString(String property, String config, String def) {
        String paramValue = getProperty(property, config);
        if (paramValue == null) {
            return def;
        }
        return paramValue;
    }
}
