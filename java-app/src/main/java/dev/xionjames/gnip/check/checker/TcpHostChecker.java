package dev.xionjames.gnip.check.checker;

import java.util.HashMap;
import java.util.Map;

import dev.xionjames.gnip.util.Const;
import dev.xionjames.gnip.util.http.HttpResponse;
import dev.xionjames.gnip.util.http.HttpUtil;

public class TcpHostChecker extends HostChecker {
    private int requestTimeout;
    private int timeLimit;
    private String[] ports;
    private String url;

    public TcpHostChecker(String host) {
        super(host);
        this.checkerKey = Const.CHECKER_KEY_TCP;
    }

    @Override
    protected void initialize() {
        this.requestTimeout = Integer.valueOf(this.prop.get(Const.PROP_CHECK_TCP_TIMEOUT));
        this.timeLimit = Integer.valueOf(this.prop.get(Const.PROP_CHECK_TCP_TIMELIMIT));
        this.ports = this.prop.get(Const.PROP_CHECK_TCP_PORTS).split("\\s");
    }

    @Override
    public boolean check() {
        boolean ok = false;

        // check every port
        for (String port : ports) {
            this.url = formatUrl(this.getHost(), port);
            HttpResponse result = HttpUtil.isReachable(this.url, requestTimeout);
            
            this.setCheckResult(result == null ? null : result.toString());

            if (result != null) {
                // check time limit
                ok = result.getResponseTime() <= this.timeLimit;
            }
        }

        return ok;
    }

    private static String formatUrl(String url, String port){
        Map<String, String> formats  = new HashMap<String, String>() {{
            put("80", "http://%s/");
            put("443", "https://%s/");
        }};

        return formats.containsKey(port) ? String.format(formats.get(port), url) : url;
    }
    
    @Override
    protected String changeNullResult() {
        return String.format("URL: %s\n%s", this.url, super.changeNullResult()); 
    }

}