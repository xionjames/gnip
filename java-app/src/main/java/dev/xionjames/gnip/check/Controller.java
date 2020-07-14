package dev.xionjames.gnip.check;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

import dev.xionjames.gnip.check.checker.IcmpHostChecker;
import dev.xionjames.gnip.check.checker.TcpHostChecker;
import dev.xionjames.gnip.check.checker.TraceHostChecker;
import dev.xionjames.gnip.util.Const;
import dev.xionjames.gnip.util.PropertyReader;

/**
 * Controls checkers executions
 */
public class Controller {
    private final PropertyReader prop;
    private final Logger LOGGER = Logger.getLogger(getClass().getName());
    private static List<Class> checkers = Arrays.asList(
        IcmpHostChecker.class, 
        TcpHostChecker.class, 
        TraceHostChecker.class
    );


    public Controller() {
        this.prop = PropertyReader.getInstance();
    }

    public void runHostChecking() {
        String[] hosts = prop.get(Const.PROP_CHECK_HOSTS).split("\\s");

        System.out.println("Process starting...");
        System.out.println(String.format("Checking hosts: %s...", Arrays.toString(hosts)));
        System.out.println("See details in log file...");
        System.out.println();
        System.out.println("CTRL+C to cancel.");

        LOGGER.info("--------------------------------------------");
        LOGGER.info("Process starting...");
        LOGGER.info(String.format("Checking hosts: %s...", Arrays.toString(hosts)));

        CountDownLatch latch = new CountDownLatch(hosts.length);

        Arrays.asList(hosts).stream().forEach(host -> {
            // one thread per host
            new Thread(() -> {
                checkHost(host);
            }).start();
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
        }
    }

    /**
     * Launch every checker for one hosts
     * @param host
     */
    private static void checkHost(String host) {
        CountDownLatch latch = new CountDownLatch(checkers.size());
        checkers.stream().forEach( checker -> {
            try {
                // Start thread to controll it
                new Control(checker, host).start();
            } catch (Exception e) {
                return;
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
        }
    }
}