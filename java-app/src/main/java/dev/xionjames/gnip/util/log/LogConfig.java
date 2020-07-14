package dev.xionjames.gnip.util.log;

import java.io.File;
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

            // initialize loggers
            initLogger(mainLogger, Level.parse(prop.get(Const.PROP_LOG_SEVERITY)), prop.get(Const.PROP_LOG_FILENAME), formatter);
            initLogger(reportLogger, Level.parse(prop.get(Const.PROP_REPORT_LOG_SEVERITY)), prop.get(Const.PROP_REPORT_LOG_FILENAME), formatter);
        } catch(IOException ioe) {
            Logger.getLogger(LogConfig.class.getName()).log(Level.SEVERE, ioe.getMessage(), ioe);
            return false;
        }
        
        return true;
    }

    private static void initLogger(Logger logger, Level level, String filename, LogFormatter formatter)
            throws SecurityException, IOException {
        
        // Create file handler
        ensureDirectory(filename);
        FileHandler handler = new FileHandler(filename, true);

        // formatter
        handler.setFormatter(formatter);

        // level
        handler.setLevel(level);

        // handler
        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
    }

    private static void ensureDirectory(String fileName) {
        String parentName = new File(fileName).getParent();
        if (parentName != null) {
            File parentDir = new File(parentName);
            // create the log dir if it does not exist
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }
        }
    }

}