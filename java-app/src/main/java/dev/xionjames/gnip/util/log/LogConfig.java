package dev.xionjames.gnip.util.log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import dev.xionjames.gnip.util.Const;
import dev.xionjames.gnip.util.PropertyReader;

public class LogConfig {
    private static final Logger mainLogger = Logger.getLogger("dev.xionjames.gnip.check");
    private static final Logger mainLogger2 = Logger.getLogger("dev.xionjames.gnip.check.checker");
    private static final Logger reportLogger = Logger.getLogger("dev.xionjames.gnip.report");

    public static boolean initialize() {
        PropertyReader prop = PropertyReader.getInstance();

        try {
            // formatter
            LogFormatter formatter = new LogFormatter();

            // handlers
            FileHandler mainHandler = new FileHandler(prop.get(Const.PROP_LOG_FILENAME), true);
            FileHandler reportHandler = new FileHandler(prop.get(Const.PROP_REPORT_LOG_FILENAME), true);
            
            mainHandler.setFormatter(formatter);
            reportHandler.setFormatter(formatter);

            // severity
            mainHandler.setLevel(Level.parse(prop.get(Const.PROP_LOG_SEVERITY)));
            reportHandler.setLevel(Level.parse(prop.get(Const.PROP_REPORT_LOG_SEVERITY)));

            mainLogger.addHandler(mainHandler);
            mainLogger2.addHandler(mainHandler);
            reportLogger.addHandler(reportHandler);
        } catch(IOException ioe) {
            Logger.getLogger(LogConfig.class.getName()).log(Level.SEVERE, ioe.getMessage(), ioe);
            return false;
        }
        
        return true;
    }

}