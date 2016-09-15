package com.uqac.rthoni.com.java_rmi.server;

/**
 * Created by robin on 9/15/16.
 */
public class ServerApplication {

    public static void run(int port, String sourceDir, String classDir, String logFile)
    {
        String ip = "0.0.0.0";
        System.out.println(String.format("Listening for clients on %1s:%2d...", ip, port));
    }
}
