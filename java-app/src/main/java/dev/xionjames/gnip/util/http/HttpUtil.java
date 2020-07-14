package dev.xionjames.gnip.util.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

public class HttpUtil {

    public static HttpResponse sendGetRequest(String url, int timeout) {
        HttpURLConnection connection = openConnection(url, "GET", timeout);

        long startTime = System.currentTimeMillis();
        HttpResponse response = HttpResponse.valueOf(connection, url, startTime);

        disconnect(connection);

        return response;
    }

    public static HttpResponse sendPostRequest(String url, String message, int timeout) {
        HttpURLConnection connection = openConnection(url, "POST", timeout);
        HttpResponse response = null;

        long startTime = System.currentTimeMillis();
        
        if (writeData(connection, message)) {
            response = HttpResponse.valueOf(connection, url, startTime);
        };

        disconnect(connection);

        return response;
    }

    public static HttpResponse isReachable(String url, int timeout) {
        HttpURLConnection connection = openConnection(url, "HEAD", timeout);
        
        try {
            long startTime = System.currentTimeMillis();
            int responseCode = connection.getResponseCode();

            return new HttpResponse(url, responseCode, null, System.currentTimeMillis() - startTime);
        } catch (UnknownHostException noInternetConnection) {
            return null;
        } catch (IOException e) {
            return null;
        }
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
        } catch (IOException e) {
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
            return false;
        }

        return true;
    }

    private static void disconnect(HttpURLConnection connection) {
        if (connection != null) {
            connection.disconnect();
        }
    }
}