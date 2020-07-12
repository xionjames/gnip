package dev.xionjames.gnip.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;

public class HttpResponse {
    private int responseCode;
    private String responseText;

    public HttpResponse(int code, String text) {
        this.responseCode = code;
        this.responseText = text;
    }

    public static HttpResponse valueOf(HttpURLConnection con) {
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

            return new HttpResponse(status, content.toString());
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
}