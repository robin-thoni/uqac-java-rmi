package com.uqac.rthoni.java_rmi.client;

import com.uqac.rthoni.java_rmi.common.Command;

import java.io.*;
import java.net.Socket;
import java.util.Vector;

/**
 * Created by robin on 9/15/16.
 */
public class ClientApplication {

    public Vector<Command> readInputFile(String file)
    {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            System.err.format("Failed to open input file %s: %s\n", file, e.getMessage());
            System.exit(1);
        }
        Vector<Command> lines = new Vector<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line;
             do {
                line = bufferedReader.readLine();
                if (line != null && !line.isEmpty()) {
                    Command command = Command.fromString(line);
                    if (command != null) {
                        lines.add(command);
                    }
                }
            } while (line != null);
        }
        catch (Exception e) {
            System.err.format("Failed to read input file %s: %s\n", file, e.getMessage());
            System.exit(2);
        }
        return lines;
    }

    public void run(String host, int port, String inputFile, String outputFile)
    {
        System.out.format("Reading input file %s...\n", inputFile);
        Vector<Command> commands = readInputFile(inputFile);
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(outputFile);
        } catch (Exception e) {
            System.err.format("Failed to open output file %s: %s\n", outputFile, e.getMessage());
            System.exit(3);
        }
        for (Command command : commands) {
            runCommand(command, host, port, outputStream);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
    }

    private void runCommand(Command command, String host, int port, OutputStream outputStream)
    {
        System.out.format("Running command %s...\n", command.getCommandName());
        System.out.format("Connecting to %s:%d...\n", host, port);
        Socket server = null;
        try {
            server = new Socket(host, port);
        }
        catch (Exception e) {
            System.err.format("Failed to connect to %s:%d: %s\n", host, port, e.getMessage());
            System.exit(4);
        }
        try {
            String str = String.format("%s\n", command.toString());
            server.getOutputStream().write(str.getBytes());
            server.getOutputStream().flush();
        } catch (IOException e) {
            System.err.format("Failed to send command: %s\n", e.getMessage());
            System.exit(5);
        }
        String output = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(server.getInputStream()));
            StringBuilder outputBuffer = new StringBuilder();
            String line;
            do {
                line = bufferedReader.readLine();
                if (line != null) {
                    outputBuffer.append(line);
                    outputBuffer.append("\n");
                }
            } while (line != null);
            output = outputBuffer.toString();
        }
        catch (Exception e) {
            System.err.format("Failed to read command output: %s\n", e.getMessage());
            System.exit(6);
        }

        try {
            outputStream.write(output.getBytes());
        } catch (IOException e) {
            System.err.format("Failed to write command output: %s\n", e.getMessage());
            System.exit(7);
        }

        try {
            server.close();
        } catch (IOException e) {
        }
    }
}
