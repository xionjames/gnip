package dev.xionjames.gnip;

import com.google.gson.Gson;

import dev.xionjames.gnip.report.IssueReporter;
import dev.xionjames.gnip.report.Message;
import dev.xionjames.gnip.util.CacheManager;
import dev.xionjames.gnip.util.Const;
import dev.xionjames.gnip.util.Util;
import dev.xionjames.gnip.util.http.HttpResponse;
import dev.xionjames.gnip.util.http.HttpUtil;
import dev.xionjames.gnip.util.log.LogConfig;
import dev.xionjames.gnip.util.process.ProcessUtil;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        LogConfig.initialize();
        App.main(new String[] { "-d" });
        assertTrue( true );
    }

    public void notestHttp() {
        System.out.println("******* HTTP ********");

        HttpResponse response = HttpUtil.sendGetRequest("https://jasmin.com/", 5000);
        assertNotNull(response);

        System.out.println("Response: " + response.getResponseCode());


        response = HttpUtil.sendPostRequest("https://postman-echo.com/post", "{\"a\": 123}", 5000);
        assertNotNull(response);

        System.out.println("Response: " + response.getResponseCode());
        System.out.println("Response Text: " + response.getResponseText());
        
    }

    public void notestProcess() {
        System.out.println("******* PROCESS ********");

        String response = ProcessUtil.runProcess("ping -c 5 localhost");
        assertNotNull(response);
        System.out.println("Process Response: " + response);
    }

    public void notestJson() {
        System.out.println("******* JSON ********");
        
        Message msg = new Message("google.com", "Result ICMP", "Result TCP", "Result Trace");
        Gson gson = new Gson();
        String json = gson.toJson(msg);

        assertNotNull(json);

        System.out.println(json);
    }

    public void notestCacheAndReport() throws InterruptedException {
        System.out.println("******* CACHE AND REPORT ********");

        CacheManager cache = CacheManager.getInstance();
        cache.put("google.com/" + Const.CHECKER_KEY_ICMP, "ICMP Result 1");
        cache.put("google.com/" + Const.CHECKER_KEY_TCP, "TCP Result 1");
        cache.put("google.com/" + Const.CHECKER_KEY_TRACE, "TRACE Result 1");

        IssueReporter.report("google.com");
        Thread.currentThread().sleep(2000);
    }

    public void notestExtract() {
        System.out.println("******* EXTRACT ********");

        String source = "5 packets transmitted, 0 received, 100% packet loss, time 4112ms";
        String extracted = Util.extract(source, "[0-9]+% packet loss");
        
        assertNotNull(extracted);
        System.out.println(extracted);

        source = "ping: jasmin.come: Name or service not known";
        extracted = Util.extract(source, "Name or service not known");
        
        assertNotNull(extracted);
        System.out.println(extracted);
    }    
}
