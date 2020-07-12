package dev.xionjames.gnip.report;

import dev.xionjames.gnip.util.Const;

public class Message {
    private String host;
    private String icmp_ping;
    private String tcp_ping;
    private String trace;

    public Message(String host, String icmp, String tcp, String trace) {
        this.host = host;
        this.icmp_ping = icmp == null ? Const.DEFAULT_CHECK_RESULT : icmp;
        this.tcp_ping = tcp == null ? Const.DEFAULT_CHECK_RESULT : tcp;
        this.trace = trace == null ? Const.DEFAULT_CHECK_RESULT : trace;
    }
}