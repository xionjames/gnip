package dev.xionjames.gnip.check;

import dev.xionjames.gnip.util.CacheManager;

/**
 * Base class to check process
 */
public abstract class HostChecker implements Runnable {
    public enum Status {
        NONE,
        RUNNING,
        OK,
        ERROR
    }

    private String host;
    private Status status;
    private String checkResult;
    protected String checkerKey;

    public HostChecker(String host) {
        this.host = host;
        this.status = Status.NONE;
        this.checkResult = null;
        this.checkerKey = "base";
    }

    @Override
    public void run() {
        if (this.host == null) {
            this.status = Status.ERROR;
            this.checkResult = "Host cannot be null";
            return;
        }

        this.status = Status.RUNNING;
        if (this.check() && this.validateResult()) {
            this.status = Status.OK;
        } else {
            this.status = Status.ERROR;
        }
    }

    /**
     * Implements business logic to check the host.
     * If check process is ok -> return true
     * 
     * @return
     */
    protected abstract boolean check();

    protected boolean validateResult() {
        return this.checkResult != null;
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
        CacheManager cache = CacheManager.getInstance();
        cache.put(
            String.format("%s/%s", this.host, this.checkerKey), 
            this.checkResult
        );
    }
    

}