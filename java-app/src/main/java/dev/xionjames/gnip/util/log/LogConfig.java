package dev.xionjames.gnip.util.log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import dev.xionjames.gnip.util.Const;
import dev.xionjames.gnip.util.PropertyReader;

public class LogConfig {
    private static final Logger mainLogger = Logger.getLogger("dev.xionjames.gnip.check");
    private static final Logger reportLogger = Logger.getLogger("dev.xionjames.gnip.report");

    public static boolean initialize() {
        PropertyReader prop = PropertyReader.getInstance();

        try {
            // formatter
            LogFormatter formatter = new LogFormatter();

            // handlers
            FileHandler mainHandler = new FileHandler(prop.get(Const.PROP_LOG_FILENAME), true);
            FileHandler apiHandler = new FileHandler(prop.get(Const.PROP_REPORT_LOG_FILENAME), true);
            
            mainHandler.setFormatter(formatter);
            apiHandler.setFormatter(formatter);

            // severity
            mainHandler.setLevel(Level.parse(prop.get(Const.PROP_LOG_SEVERITY)));
            mainHandler.setLevel(Level.parse(prop.get(Const.PROP_LOG_SEVERITY)));

            mainLogger.addHandler(mainHandler);
            reportLogger.addHandler(apiHandler);
        } catch(IOException ioe) {
            Logger.getLogger(LogConfig.class.getName()).log(Level.SEVERE, ioe.getMessage(), ioe);
            return false;
        }
        
        return true;
    }

}