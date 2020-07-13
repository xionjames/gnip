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

    public static final String PROP_LOG_FILENAME = "gnip.log.filename";
    public static final String PROP_LOG_SEVERITY = "gnip.log.severity";
    public static final String PROP_REPORT_LOG_FILENAME = "gnip.report.log.filename";
    public static final String PROP_REPORT_LOG_SEVERITY = "gnip.report.log.severity";
    
    public static final String PROP_LOG_FORMAT = "gnip.log.format";
    public static final String PROP_LOG_FORMAT_DATE = "gnip.log.format.date";
    
    public static final String PROP_REPORT_URL = "gnip.report.url";
    public static final String PROP_REPORT_TIMEOUT = "gnip.report.timeout";
    
    public static final String PROP_CHECK_HOSTS = "gnip.check.hosts";

    public static final String PROP_CHECK_ICMP_COMMAND = "gnip.check.icmp.command";

    public static final String PROP_CHECK_TCP_TIMEOUT = "gnip.check.tcp.timeout";
    public static final String PROP_CHECK_TCP_TIMELIMIT = "gnip.check.tcp.timelimit";
    public static final String PROP_CHECK_TCP_PORTS = "gnip.check.tcp.ports";

    public static final String PROP_CHECK_TRACE_COMMAND = "gnip.check.trace.command";

    public static final String PROP_CHECK_ALL_NULLERROR = "gnip.check.%s.nullerror";
    public static final String PROP_CHECK_ALL_DELAY = "gnip.check.%s.delay";

    /* checker keys */
    public static final String CHECKER_KEY_ICMP = "icmp";
    public static final String CHECKER_KEY_TCP = "tcp";
    public static final String CHECKER_KEY_TRACE = "trace";

    /* OTHER */
    public static final String HELP_FILE = "help.txt";
    public static final String EXAMPLE_PROPERTIES_FILE = "example.properties";
}
