package com.uqac.rthoni.java_rmi.server;

import com.uqac.rthoni.java_rmi.server.executors.AbstractCommandExecutor;
import com.uqac.rthoni.java_rmi.common.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Created by robin on 9/15/16.
 */
public class ServerApplication {

    private Vector<AbstractCommandExecutor> _executors = new Vector<>();

    private HashMap<String, Object> _objects = new HashMap<>();

    private Vector<ClassLoader> _loaders = new Vector<>();

    private String _sourceDir;

    private String _classDir;

    private String _logFile;

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

    public void addObject(String id, Object obj)
    {
        _objects.put(id, obj);
    }

    public Object getObject(String id)
    {
        return _objects.get(id);
    }

    public void addClassLoader(ClassLoader loader)
    {
        _loaders.add(loader);
    }

    public List<ClassLoader> getClassLoaders()
    {
        return _loaders;
    }

    public String getSourceDir() {
        return _sourceDir;
    }

    public String getClassDir() {
        return _classDir;
    }

    public String getLogFile() {
        return _logFile;
    }

    public void run(int port, String sourceDir, String classDir, String logFile)
    {
        _sourceDir = sourceDir;
        _classDir = classDir;
        _logFile = logFile;
        try {
            loadExecutors();
        } catch (ClassNotFoundException e) {
            System.err.format("Failed to load executors: %s\n", e.getMessage());
            System.exit(1);
        }

        String host = "0.0.0.0";
        InetAddress ip = null;
        try {
            ip = InetAddress.getByName(host);
        }
        catch (Exception e) {
        }
        if (ip == null) {
            System.err.format("Failed to resolve '%s'\n", host);
            System.exit(2);
        }
        String ipString = ipToString(ip);

        try {
            ServerSocket serverSocket = new ServerSocket(port, 1, ip);
            System.out.format("Listening for clients on %s:%d...\n", ipString, port);
            runServer(serverSocket);
        } catch (IOException e) {
            System.err.format("Failed to listen on %s:%d: %s\n", ipString, port, e.getMessage());
            System.exit(3);
        }
    }

    public void loadExecutors() throws ClassNotFoundException
    {
        _executors.clear();
        Vector<Class> classes = AbstractCommandExecutor.getAllExecutors();
        for (Class c : classes) {
            try {
                AbstractCommandExecutor executor = (AbstractCommandExecutor)c.newInstance();
                _executors.add(executor);
            } catch (Exception e) {
                System.err.format("Failed to load executor '%s': %s\n", c.getName(), e.getMessage());
            }
        }
    }

    public void runServer(ServerSocket serverSocket)
    {
        while (true) {
            Socket client = null;
            try {
                client = serverSocket.accept();
            }
            catch (Exception e) {
                System.err.format("Failed to accept client: %s\n", e.getMessage());
            }
            if (client != null) {
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
    }

    public void handleClient(Socket client)
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

            handleCommand(command, client);
        }
    }

    public AbstractCommandExecutor getExecutor(Command command)
    {
        for (AbstractCommandExecutor executor : _executors) {
            if (executor.getCommandName().equals(command.getCommandName())) {
                return executor;
            }
        }
        return null;
    }

    public void handleCommand(Command command, Socket client)
    {
        String data;
        AbstractCommandExecutor executor = getExecutor(command);
        if (executor == null) {
            data = String.format("Unknown command '%s'", command.getCommandName());
            System.err.println(data);
        }
        else {
            try {
                data = executor.run(command, this);
                if (data == null) {
                    data = "OK";
                }
            } catch (Exception e) {
                data = String.format("Error when handling command: %s %s", e.getClass().getName(), e.getMessage());
                System.err.println(data);
            }
        }

        try {
            client.getOutputStream().write(String.format("%s\n", data).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
