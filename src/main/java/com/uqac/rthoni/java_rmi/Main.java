package com.uqac.rthoni.java_rmi;

import java.io.PrintStream;

/**
 * Created by robin on 9/15/16.
 */
public class Main {
    public static void usage(String name, boolean error)
    {
        PrintStream printStream = (error ? System.err : System.out);

        printStream.println("Usage:");
        printStream.println(String.format("%1s server port source_dir class_dir log_file", name));
        printStream.println("\tPut the program in server mode");
        printStream.println("\tport: the port to listen on [1-65535]");
        printStream.println("\tsource_dir: the folder to search for java files");
        printStream.println("\tclass_dir: the folder to build java files");
        printStream.println("\tlog_file: the file to log events");
        printStream.println(String.format("%1s client hostname port input_file output_file", name));
        printStream.println("\tPut the program in client mode");
        printStream.println("\thost: the remote server ip or hostname");
        printStream.println("\tport: the port to connect on [1-65535]");
        printStream.println("\tinput_file: the command file to use");
        printStream.println("\toutput_file: the file to output command results");
        printStream.println(String.format("%1s --help", name));
        printStream.println(String.format("%1s -h", name));
        printStream.println("\tPrint this help and exit");

        if (error) {
            System.exit(64);
        }
    }

    private static int getPort(String name, String str) {
        int port = 0;
        try {
            port = Integer.parseInt(str);
        } catch (Exception e) {
            usage(name, true);
        }
        if (port < 1 || port > 65535) {
            usage(name, true);
        }
        return port;
    }

    public static void main(String[] args)
    {
        String name = "java-rmi";
        for (String arg : args) {
            if (arg.equals("--help") || arg.equals("-h")) {
                usage(name, false);
            }
        }
        if (args.length != 5) {
            usage(name, true);
        }
        String mode = args[0];
        if (mode.equals("server")) {
            int port = getPort(name, args[1]);
            String sourceDir = args[2];
            String classDir = args[3];
            String logFile = args[4];

            System.out.println("Hello World! server");
        }
        else if (mode.equals("client")) {
            String host = args[1];
            int port = getPort(name, args[2]);
            String inputFile = args[3];
            String outputFile = args[4];

            System.out.println("Hello World! client");
        }
        else {
            usage(name, true);
        }
        System.exit(0);
    }
}
