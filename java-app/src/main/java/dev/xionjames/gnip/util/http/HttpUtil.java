package dev.xionjames.gnip.util.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtil {
    public static HttpResponse sendGetRequest(String url, int timeout) {
        HttpURLConnection connection = openConnection(url, "GET", timeout);
        HttpResponse response = HttpResponse.valueOf(connection);

        if (connection != null) {
            connection.disconnect();
        }

        return response;
    }

    public static HttpResponse sendPostRequest(String url, String message, int timeout) {
        HttpURLConnection connection = openConnection(url, "POST", timeout);
        HttpResponse response = null;

        if (writeData(connection, message)) {
            response = HttpResponse.valueOf(connection);
        };

        if (connection != null) {
            connection.disconnect();
        }

        return response;
    }

    private static HttpURLConnection openConnection(String url, String method, int timeout) {
        HttpURLConnection connection = null;
        try {
            URL urlObj = new URL(url);
            connection = (HttpURLConnection) urlObj.openConnection();

            connection.setRequestMethod(method);
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return connection;
    }

    private static boolean writeData(HttpURLConnection connection, String data) {
        connection.setRequestProperty("Content-Type", "application/json");
        
        connection.setDoOutput(true);
        
        try {
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(data);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}