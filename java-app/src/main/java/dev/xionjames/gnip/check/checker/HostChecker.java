package dev.xionjames.gnip.check.checker;

import dev.xionjames.gnip.report.IssueReporter;
import dev.xionjames.gnip.util.CacheManager;
import dev.xionjames.gnip.util.Const;
import dev.xionjames.gnip.util.PropertyReader;

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
    protected boolean reportOnFail = true;
    protected PropertyReader prop;

    public HostChecker(String host) {
        this.host = host;
        this.status = Status.NONE;
        this.checkResult = null;
        this.checkerKey = "base";

        this.prop = PropertyReader.getInstance();
    }

    @Override
    public void run() {
        if (this.host == null) {
            this.status = Status.ERROR;
            this.checkResult = "Host cannot be null";
            return;
        }

        initialize();

        this.status = Status.RUNNING;
        if (this.check() && this.validateResult()) {
            this.status = Status.OK;
        } else {
            this.status = Status.ERROR;
            if (this.reportOnFail) {
                IssueReporter.report(this.host);
            }
        }
    }

    /**
     * Implements initialization for every subclass
     */
    protected abstract void initialize();

    /**
     * Implements business logic to check the host.
     * If check process is ok -> return true
     * 
     * @return
     */
    protected abstract boolean check();

    /**
     * Implements result checking
     * @return
     */
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

    protected void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
        CacheManager cache = CacheManager.getInstance();
        cache.put(
            String.format("%s/%s", this.host, this.checkerKey), 
            this.checkResult != null ? this.checkResult : changeNullResult()
        );
    }

    protected String changeNullResult() {
        return this.prop.get( String.format(Const.PROP_CHECK_ALL_NULLERROR, this.checkerKey) );
    }
    

}