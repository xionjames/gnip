package dev.xionjames.gnip.check.checker;

import java.util.logging.Logger;

import dev.xionjames.gnip.report.IssueReporter;
import dev.xionjames.gnip.util.CacheManager;
import dev.xionjames.gnip.util.Const;
import dev.xionjames.gnip.util.PropertyReader;

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
    protected String checkerKey;
    protected boolean reportOnFail = true;
    protected PropertyReader prop;
    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    public HostChecker(String host) {
        this.host = host;
        this.status = Status.NONE;
        this.checkResult = null;
        this.checkerKey = "base";

        this.prop = PropertyReader.getInstance();
    }

    @Override
    public void run() {
        LOGGER.info(String.format("Thread %d: %s checking started for %s", this.getId(), this.checkerKey, this.host));
        
        if (this.host == null) {
            this.status = Status.ERROR;
            this.checkResult = "Host cannot be null";
            return;
        }

        this.checkResult = null;
        initialize();

        this.status = Status.RUNNING;
        if (this.check() && this.validateResult()) {
            this.status = Status.OK;
            
            LOGGER.info(String.format("Thread %d: %s checking finished OK for %s", this.getId(), this.checkerKey, this.host));
        } else {
            this.status = Status.ERROR;

            LOGGER.info(String.format("Thread %d: %s checking finished ERROR for %s", this.getId(), this.checkerKey, this.host));

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

    public int getDelay() {
        return Integer.valueOf(
            prop.get( String.format(Const.PROP_CHECK_ALL_DELAY, this.checkerKey) )
        );
    }
    

}