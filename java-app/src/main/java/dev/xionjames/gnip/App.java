package dev.xionjames.gnip;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import dev.xionjames.gnip.check.Controller;
import dev.xionjames.gnip.util.Const;
import dev.xionjames.gnip.util.PropertyReader;
import dev.xionjames.gnip.util.Util;
import dev.xionjames.gnip.util.log.LogConfig;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        if (args.length == 0) {
            showHelp();
            System.exit(1);
        }

        processArgs(args);
    }

    public static void showHelp() {
        InputStream is = App.class.getClassLoader().getResourceAsStream(Const.HELP_FILE);
        System.out.println(Util.readAll(is));
    }

    public static void processArgs(String[] args) {
        PropertyReader prop;

        switch (args[0]) {
            case "-d":
                prop = PropertyReader.getInstance();
                executeCheck();
                break;

            case "-p":
                if (args.length == 1) {
                    System.out.println("Properties file missing.");
                    System.out.println("Usage: -p path/to/file");
                    System.exit(1);
                }

                prop = PropertyReader.getInstance(args[1]);
                executeCheck();
                break;

            case "-g":
                generateExampleProperties();
                break;

            case "-h":
                showHelp();
                break;

            default:
                System.out.println("Invalid option.");
                showHelp();
        }
    }

    public static void executeCheck() {
        LogConfig.initialize();

        Controller controller = new Controller();
        controller.runHostChecking();
    }

    public static void generateExampleProperties() {
        InputStream is = App.class.getClassLoader().getResourceAsStream(Const.DEFAULT_PROPERTY_FILE);
        try {
            FileWriter fw = new FileWriter(Const.EXAMPLE_PROPERTIES_FILE);
            fw.write(Util.readAll(is));
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
