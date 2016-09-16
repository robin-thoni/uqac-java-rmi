package com.uqac.rthoni.com.java_rmi.server;

import com.uqac.rthoni.java_rmi.common.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by robin on 9/15/16.
 */
public class ServerApplication {

    public static String ipToString(InetAddress ip) {
        String ipString = ip.toString();
        if (!ipString.isEmpty() && ipString.startsWith("/")) {
            ipString = ipString.substring(1);
        }
        return ipString;
    }

    public static String getFullIp(InetAddress ip) {
        try {
            InetAddress address = InetAddress.getByName(ip.toString().split("/")[1]);
            return String.format("%s/%s", address.getCanonicalHostName(), ipToString(ip));
        }
        catch (Exception e) {
            return ipToString(ip);
        }
    }

    public static void run(int port, String sourceDir, String classDir, String logFile)
    {
        String host = "0.0.0.0";
        InetAddress ip = null;
        try {
            ip = InetAddress.getByName(host);
        }
        catch (Exception e) {
        }
        if (ip == null) {
            System.err.format("Failed to resolve '%s'\n", host);
            System.exit(1);
        }
        String ipString = ipToString(ip);

        try {
            ServerSocket serverSocket = new ServerSocket(port, 1, ip);
            System.out.format("Listening for clients on %s:%d...\n", ipString, port);
            runServer(serverSocket);
        } catch (IOException e) {
            System.err.format("Failed to listen on %s:%d: %s\n", ipString, port, e.getMessage());
        }
    }

    public static void runServer(ServerSocket serverSocket)
    {
        boolean stop = false;
        while (!stop) {
            Socket client;
            try {
                client = serverSocket.accept();
            }
            catch (Exception e) {
                System.err.format("Failed to accept client: %s\n", e.getMessage());
                break;
            }
            String ipString = getFullIp(client.getInetAddress());
            System.out.format("New client: %s:%d\n", ipString, client.getPort());
            try {
                handleClient(client);
            } catch (Exception e) {
                System.out.format("Error when handling client: %s\n", e.getMessage());
            }
            try {
                client.close();
            } catch (Exception e) {
            }
            System.out.format("Client disconnected: %s:%d\n", ipString, client.getPort());
        }
    }

    private static void handleClient(Socket client)
    {
        String str;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            str = in.readLine();
        } catch (IOException e) {
            System.err.format("Failed to read from client: %s", e.getMessage());
            return;
        }
        Command command = Command.fromString(str);
        if (command == null) {
            System.err.format("Failed to deserialize command: %s\n", str);
        }
        else {
            System.out.format("Received command: %s\n", command.getCommandName());
        }
    }
}
