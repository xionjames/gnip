package dev.xionjames.gnip.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;

public class HttpResponse {
    private String url;
    private int responseCode;
    private String responseText;
    private long responseTime;

    public HttpResponse(String url, int code, String text, long responseTime) {
        this.url = url;
        this.responseCode = code;
        this.responseText = text;
        this.responseTime = responseTime;
    }

    public static HttpResponse valueOf(HttpURLConnection con, String url, long startTime) {
        try {
            int status = con.getResponseCode();

            Reader streamReader = null;

            if (status > 299) {
                streamReader = new InputStreamReader(con.getErrorStream());
            } else {
                streamReader = new InputStreamReader(con.getInputStream());
            }

            BufferedReader in = new BufferedReader(streamReader);
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            return new HttpResponse(url, status, content.toString(), System.currentTimeMillis() - startTime);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* GETTERS */
    
    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseText() {
        return responseText;
    }

    public long getResponseTime() {
        return responseTime;
    }

    @Override
    public String toString() {
        StringBuffer format = new StringBuffer();
        format.append("URL: %s\n")
              .append("HTTP status: %d\n")
              .append("Response time: %d ms");

        return String.format(format.toString(), this.url, this.responseCode, this.responseTime);
    }
}