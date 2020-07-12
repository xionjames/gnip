package dev.xionjames.gnip.check;

import dev.xionjames.gnip.util.Const;

public class TcpHostChecker extends HostChecker {

    public TcpHostChecker(String host) {
        super(host);
        this.checkerKey = Const.CHECKER_KEY_TCP;
    }

    @Override
    public boolean check() {
        // TODO Auto-generated method stub
        return false;
    }

}