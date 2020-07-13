package dev.xionjames.gnip;

import dev.xionjames.gnip.check.Controller;
import dev.xionjames.gnip.util.PropertyReader;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        if (args.length == 0) {
            showHelp();
            System.exit(1);
        }
 
        processArgs(args);
    }

    public static void showHelp() {

    }

    public static void processArgs(String[] args) {
        PropertyReader prop;

        switch(args[0]) {
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
        Controller controller = new Controller();
        controller.runHostChecking();
    }

    public static void generateExampleProperties() {
        
    }
}
