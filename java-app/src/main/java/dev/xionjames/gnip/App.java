package dev.xionjames.gnip;

import dev.xionjames.gnip.util.PropertyReader;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        System.out.println("ARGS: " + args.length);

        PropertyReader prop = args.length > 0 
                            ? PropertyReader.getInstance(args[0]) 
                            : PropertyReader.getInstance();
        
        System.out.println( "Leyendo: " + prop.get("gnip.hosts") );
    }
}
