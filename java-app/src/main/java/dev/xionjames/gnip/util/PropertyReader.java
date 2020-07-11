package dev.xionjames.gnip.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class to read properties
 *   
 * @author xionjames
 */

public class PropertyReader {
    private static PropertyReader instance;
    private final Properties properties;
    

    public static PropertyReader getInstance() {
        if (instance == null) {
            instance = new PropertyReader(null);
        }

        return instance;
    }

    /**
     * Use to read a different properties file
     * @param propertiesFile
     * @return
     */
    public static PropertyReader getInstance(String propertiesFile) {
        if (instance == null) {
            instance = new PropertyReader(propertiesFile);
        }

        return instance;
    }

    public PropertyReader(String propertiesFile) {
        properties = new Properties();
        try {
            if (propertiesFile == null) {
                properties.load(getClass().getClassLoader().getResourceAsStream(Const.DEFAULT_PROPERTY_FILE));
            } else {
                properties.load(new FileInputStream(propertiesFile));
            }            
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public String get(String key) {
        return properties.getProperty(key);
    }
}