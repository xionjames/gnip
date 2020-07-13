package dev.xionjames.gnip.check;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

import dev.xionjames.gnip.check.checker.HostChecker;
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
    private Class[] checkers = new Class[] { IcmpHostChecker.class, TcpHostChecker.class, TraceHostChecker.class };

    public Controller() {
        this.prop = PropertyReader.getInstance();
    }

    public void runHostChecking() {
        String[] hosts = prop.get(Const.PROP_CHECK_HOSTS).split("\\s");

        System.out.println(String.format("Checking hosts: %s...", Arrays.toString(hosts)));
        System.out.println("CTRL+C to cancel");

        LOGGER.info(String.format("Checking hosts: %s...", Arrays.toString(hosts)));

        CountDownLatch latch = new CountDownLatch(hosts.length);
        for (final String host : hosts) {
            // one thread per host
            new Thread(() -> {
                checkHost(host);
            }).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void checkHost(String host) {
        CountDownLatch latch = new CountDownLatch(checkers.length);
        for (int i = 0; i < checkers.length; i++) {
            try {
                LOGGER.info("Using " + checkers[i].getSimpleName() + " for " + host);

                // Start thread to controll it
                new Control(checkers[i], host).start();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info("Host checking finished for " + host);        
    }
}