package dev.xionjames.gnip.util.log;

import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import dev.xionjames.gnip.util.Const;
import dev.xionjames.gnip.util.PropertyReader;

public class LogFormatter extends Formatter {
    private String logFormat;
    private String dateFormat;

    public LogFormatter() {
        PropertyReader prop = PropertyReader.getInstance();
        
        this.logFormat = prop.get(Const.PROP_SETTINGS_LOG_FORMAT);
        this.dateFormat = prop.get(Const.PROP_SETTINGS_LOG_FORMAT_DATE);

        if (this.logFormat == null) this.logFormat = Const.DEFAULT_LOG_FORMAT;
        if (this.dateFormat == null) this.dateFormat = Const.DEFAULT_LOG_DATE_FORMAT;
    }

    @Override
    public String format(LogRecord record) {
        String date = new SimpleDateFormat(this.dateFormat).format(record.getMillis());

        return this.logFormat.replace("{date}", date)
                             .replace("{severity}", record.getLevel().toString())
                             .replace("{class}", record.getSourceClassName())
                             .replace("{method}", record.getSourceMethodName())
                             .replace("{log}", record.getMessage()) + "\n";
    }


}