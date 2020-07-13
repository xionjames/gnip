package dev.xionjames.gnip.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    public static String extract(String source, String pattern) {
        Pattern patternObj = Pattern.compile(pattern);
        Matcher matcher = patternObj.matcher(source);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return null;
    }

    public static String readAll(InputStream is) {
        try {
            StringBuilder textBuilder = new StringBuilder();
            try (Reader reader = new BufferedReader(
                    new InputStreamReader(is, Charset.forName(StandardCharsets.UTF_8.name())))) {
                int c = 0;
                while ((c = reader.read()) != -1) {
                    textBuilder.append((char) c);
                }
            }
            return textBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}