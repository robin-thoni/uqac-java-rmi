package com.uqac.rthoni.java_rmi.common;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 * Created by robin on 9/15/16.
 */
public class Command implements Serializable {
    private String _commandName = "";
    private Vector<String> _arguments = new Vector<>();
    private transient String _result;

    public Command(String commandName)
    {
        setCommandName(commandName);
    }

    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(_commandName);
        if (!_arguments.isEmpty()) {
            stringBuilder.append("#");
            stringBuilder.append(_arguments.stream().collect(Collectors.joining("#")));
        }
        return stringBuilder.toString();
    }

    public static Command fromString(String str)
    {
        if (str == null || str.isEmpty()) {
            return null;
        }
        String[] parts = str.split("#");
        Command command = new Command(parts[0]);
        for (int i = 1; i < parts.length; ++i) {
            command.addArgument(parts[i]);
        }
        return command;
    }

    public void setCommandName(String commandName)
    {
        if (commandName == null || commandName.isEmpty()) {
            commandName = "Unknown";
        }
        _commandName = commandName;
    }

    public String getCommandName()
    {
        return _commandName;
    }

    public void addArgument(String argument)
    {
        if (argument == null) {
            argument = "";
        }
        _arguments.add(argument);
    }

    public int getArgumentCount()
    {
        return _arguments.size();
    }

    public String getArgument(int i)
    {
        return _arguments.get(i);
    }

    public Vector<String> getArgmumentAsList(int i)
    {
        return new Vector<String>(Arrays.asList(_arguments.get(i).split(",")));
    }

    public void setResult(String result)
    {
        _result = result;
    }

    public String getResult()
    {
        return _result;
    }
}
