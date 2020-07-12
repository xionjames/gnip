package dev.xionjames.gnip.util;

/**
 * Class to keep constants
 *   
 * @author xionjames
 */

public class Const {

    /* default values */
    public static final String DEFAULT_PROPERTY_FILE = "gnip.properties";
    public static final String DEFAULT_LOG_FORMAT = "{date} {severity} {class} {method} {log}";
    public static final String DEFAULT_LOG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_CHECK_RESULT = "No results yet";

    public static final String PROP_HOSTS = "gnip.hosts";

    public static final String PROP_LOG_FILENAME = "gnip.log.filename";
    public static final String PROP_LOG_SEVERITY = "gnip.log.severity";
    public static final String PROP_REPORT_LOG_FILENAME = "gnip.report.log.filename";
    public static final String PROP_REPORT_LOG_SEVERITY = "gnip.report.log.severity";
    
    public static final String PROP_LOG_FORMAT = "gnip.log.format";
    public static final String PROP_LOG_FORMAT_DATE = "gnip.log.format.date";
    
    public static final String PROP_REPORT_URL = "gnip.report.url";
    public static final String PROP_REPORT_TIMEOUT = "gnip.report.timeout";
    

    /* checker keys */
    public static final String CHECKER_KEY_ICMP = "icmp";
    public static final String CHECKER_KEY_TCP = "tcp";
    public static final String CHECKER_KEY_TRACE = "trace";

    
}
