package dev.xionjames.gnip;

import java.util.logging.Level;
import java.util.logging.Logger;

import dev.xionjames.gnip.util.http.HttpResponse;
import dev.xionjames.gnip.util.http.HttpUtil;
import dev.xionjames.gnip.util.log.LogConfig;
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
        testHttp();


        //App.main(new String[] {});
       
        assertTrue( true );
    }

    public void testHttp() {
        HttpResponse response = HttpUtil.sendGetRequest("https://jasmin.com/", 5000);
        assertNotNull(response);

        System.out.println("Response: " + response.getResponseCode());


        response = HttpUtil.sendPostRequest("https://postman-echo.com/post", "{\"a\": 123}", 5000);
        assertNotNull(response);

        System.out.println("Response: " + response.getResponseCode());
        System.out.println("Response Text: " + response.getResponseText());
        
    }
}
