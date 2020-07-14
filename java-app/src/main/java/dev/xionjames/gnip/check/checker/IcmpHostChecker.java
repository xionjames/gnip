package dev.xionjames.gnip.check.checker;

import dev.xionjames.gnip.util.Const;
import dev.xionjames.gnip.util.Util;
import dev.xionjames.gnip.util.process.ProcessUtil;

public class IcmpHostChecker extends HostChecker {
    private String command;

    public IcmpHostChecker(String host) {
        super(host);
        this.checkerKey = Const.CHECKER_KEY_ICMP;
    }

    @Override
    protected void initialize() {
        this.command = this.prop.get(Const.PROP_CHECK_ICMP_COMMAND).replace("{host}", this.getHost());
    }

    @Override
    public boolean check() {
        LOGGER.fine(String.format("Thread %d> run command: %s", this.getId(), this.command));
        
        String result = ProcessUtil.runProcess(this.command);
       
        LOGGER.finer(String.format("Thread %d> command result: %s", this.getId(), result));

        this.setCheckResult(result);

        return result != null;
    }

    @Override
    protected boolean validateResult() {
        if (this.getCheckResult() == null) {
            return false;
        }

        String[] patternsError = new String[] {
            "Name or service not known",
            "unreachable"
        };

        String packetLoss = Util.extract(this.getCheckResult(), "[0-9]+% packet loss");
        if (packetLoss != null && !packetLoss.startsWith("0%")) {
            return false;
        }

        for (String pattern : patternsError) {
            if (Util.extract(this.getCheckResult(), pattern) != null) {
                return false;
            }
        }

        return true;
    }

}