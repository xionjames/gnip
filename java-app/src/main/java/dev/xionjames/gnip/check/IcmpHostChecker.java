package dev.xionjames.gnip.check;

import dev.xionjames.gnip.util.Const;
import dev.xionjames.gnip.util.PropertyReader;
import dev.xionjames.gnip.util.process.ProcessUtil;

public class IcmpHostChecker extends HostChecker {
    private String command;

    public IcmpHostChecker(String host) {
        super(host);
        this.checkerKey = Const.CHECKER_KEY_ICMP;
    }

    @Override
    protected void initialize() {
        this.command = this.prop.get(Const.PROP_CHECK_ICMP_COMMAND);
    }

    @Override
    public boolean check() {
        String result = ProcessUtil.runProcess(this.command);
        if (result != null) {
            this.setCheckResult(result);
            return true;
        }

        return false;
    }

}