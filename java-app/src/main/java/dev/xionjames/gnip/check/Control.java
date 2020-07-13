package dev.xionjames.gnip.check;

import dev.xionjames.gnip.check.checker.HostChecker;

/**
 * Controls delay and execution for one specific checking type
 */
public class Control extends Thread {
    Class checkerClass;
    String host;

    public Control(Class checkerClass, String host) {
        this.checkerClass = checkerClass;
        this.host = host;
    }

    @Override
    public void run() {
        
        while (true) {
            HostChecker checker = null;
            try {
                checker = (HostChecker) checkerClass.getConstructor(String.class).newInstance(host);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            int delay = checker.getDelay();
            if (!checker.isAlive()) {
                checker.start();
            }
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}