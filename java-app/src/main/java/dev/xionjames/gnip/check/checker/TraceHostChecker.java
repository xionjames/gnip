package dev.xionjames.gnip.check.checker;

import dev.xionjames.gnip.util.Const;
import dev.xionjames.gnip.util.process.ProcessUtil;

public class TraceHostChecker extends HostChecker {
    private String command;

    public TraceHostChecker(String host) {
        super(host);
        this.checkerKey = Const.CHECKER_KEY_TRACE;
        this.reportOnFail = false;
    }

    @Override
    protected void initialize() {
        this.command = this.prop.get(Const.PROP_CHECK_TRACE_COMMAND).replace("{host}", this.getHost());
    }

    @Override
    public boolean check() {
        LOGGER.fine(String.format("Thread %d> run command: %s", this.getId(), this.command));
        
        String result = ProcessUtil.runProcess(this.command);
        
        LOGGER.finer(String.format("Thread %d> command result: %s", this.getId(), result));

        this.setCheckResult(result);

        return result != null;
    }

}