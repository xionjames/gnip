package dev.xionjames.gnip.check;

import dev.xionjames.gnip.util.Const;

public class TraceHostChecker extends HostChecker {

    public TraceHostChecker(String host) {
        super(host);
        this.checkerKey = Const.CHECKER_KEY_TRACE;
    }

    @Override
    public boolean check() {
        // TODO Auto-generated method stub
        return false;
    }

}