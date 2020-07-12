package dev.xionjames.gnip.util.process;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class ProcessUtil {
    public static String runProcess(String command) {
        try {
            final Process process = Runtime.getRuntime().exec(command);

            StringBuffer response = new StringBuffer();
            Consumer<String> consumer = str -> response.append(str).append("\n");

            StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), consumer);
            Executors.newSingleThreadExecutor().submit(streamGobbler);
            int exitCode = process.waitFor();
            
            return exitCode == 0 ? response.toString() : null;
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}