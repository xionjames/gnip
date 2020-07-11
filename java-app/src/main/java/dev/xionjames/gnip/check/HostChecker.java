package dev.xionjames.gnip.check;

/**
 * Base class to check process
 */
public abstract class HostChecker extends Thread {
    public enum Status {
        NONE,
        RUNNING,
        OK,
        ERROR
    }

    private String host;
    private Status status;
    private String checkResult;

    public HostChecker(String host) {
        this.host = host;
        this.status = Status.NONE;
        this.checkResult = null;
    }

    /**
     * Implements business logic to check the host.
     * If check process is ok -> return true
     * 
     * @return
     */
    public abstract boolean check();


    @Override
    public void run() {
        if (this.host == null) {
            this.status = Status.ERROR;
            this.checkResult = "Host cannot be null";
            return;
        }

        this.status = Status.RUNNING;
        if (this.check()) {
            this.status = Status.OK;
        } else {
            this.status = Status.ERROR;
        }
    }

    /* SETTERS and GETTERS */
    
    public String getHost() {
        return host;
    }

    public Status getStatus() {
        return status;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

}