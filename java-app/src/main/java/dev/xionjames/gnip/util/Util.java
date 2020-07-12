package dev.xionjames.gnip.util;

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
}