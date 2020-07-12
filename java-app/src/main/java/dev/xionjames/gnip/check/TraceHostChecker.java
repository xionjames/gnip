package dev.xionjames.gnip.check;

import dev.xionjames.gnip.util.Const;
import dev.xionjames.gnip.util.PropertyReader;
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
        this.command = this.prop.get(Const.PROP_CHECK_TRACE_COMMAND);
    }

    @Override
    public boolean check() {
        String result = ProcessUtil.runProcess(this.command);
        this.setCheckResult(result);

        return result != null;
    }

}