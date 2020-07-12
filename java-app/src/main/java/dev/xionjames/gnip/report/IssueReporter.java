package dev.xionjames.gnip.report;

import java.util.UUID;
import java.util.logging.Logger;

import com.google.gson.Gson;

import dev.xionjames.gnip.util.CacheManager;
import dev.xionjames.gnip.util.Const;
import dev.xionjames.gnip.util.PropertyReader;
import dev.xionjames.gnip.util.http.HttpResponse;
import dev.xionjames.gnip.util.http.HttpUtil;

public class IssueReporter {
    private static final Logger LOGGER = Logger.getLogger(IssueReporter.class.getName());

    public static void report(String host) {
        Message message = obtainMessage(host);
        String json = new Gson().toJson(message);

        sendReport(json);
    }

    private static Message obtainMessage(String host) {
        // get cache values
        CacheManager cache = CacheManager.getInstance();

        final String icmp = (String) cache.get(String.format("%s/%s", host, Const.CHECKER_KEY_ICMP));
        final String tcp = (String) cache.get(String.format("%s/%s", host, Const.CHECKER_KEY_TCP));
        final String trace = (String) cache.get(String.format("%s/%s", host, Const.CHECKER_KEY_TRACE));

        return new Message(host, icmp, tcp, trace);
    }

    private static void sendReport(String jsonMessage) {
        final PropertyReader prop = PropertyReader.getInstance();
        final int timeout = Integer.valueOf(prop.get(Const.PROP_REPORT_TIMEOUT));
        final String url = prop.get(Const.PROP_REPORT_URL);

        final UUID uuid = UUID.randomUUID();

        LOGGER.info(String.format("Sending report (%s): %s", uuid.toString(), jsonMessage));

        new Thread() {

            @Override
            public void run() {
                HttpResponse response = HttpUtil.sendPostRequest(url, jsonMessage, timeout);
                LOGGER.info(String.format("Report (%s) response status code: %d", uuid.toString(), response.getResponseCode()));
            }
        }.start();

    }
}