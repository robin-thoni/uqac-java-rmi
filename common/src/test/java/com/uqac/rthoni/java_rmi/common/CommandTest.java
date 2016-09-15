package com.uqac.rthoni.java_rmi.common;

import com.sun.corba.se.impl.orbutil.ObjectWriter;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import static org.junit.Assert.*;

/**
 * Created by robin on 9/15/16.
 */
public class CommandTest {

    private String commandToString(Command command) throws Exception
    {
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
//        objectOutputStream.writeObject(command);
//        objectOutputStream.close();
//        return byteArrayOutputStream.toString("UTF-8");
        return command.toString();
    }

    @Test
    public void testCommandSerializeNull() throws Exception
    {
        Command c = new Command(null);
        String result = commandToString(c);
        assertEquals(result, "Unknown");
    }

    @Test
    public void testCommandSerializeEmpty() throws Exception
    {
        Command c = new Command("");
        String result = commandToString(c);
        assertEquals(result, "Unknown");
    }

    @Test
    public void testCommandSerializeName() throws Exception
    {
        Command c = new Command("Test");
        String result = commandToString(c);
        assertEquals(result, "Test");
    }

    @Test
    public void testCommandSerializeArgument() throws Exception
    {
        Command c = new Command("Test");
        c.addArgument("an_argument");
        String result = commandToString(c);
        assertEquals(result, "Test#an_argument");
    }

    @Test
    public void testCommandSerializeArguments() throws Exception
    {
        Command c = new Command("Test");
        c.addArgument("an_argument");
        c.addArgument("an_argument2");
        String result = commandToString(c);
        assertEquals(result, "Test#an_argument#an_argument2");
    }

    @Test
    public void testCommandDeserializeNull() throws Exception
    {
        Command c = Command.fromString(null);
        assertNull(c);
    }

    @Test
    public void testCommandDeserializeEmpty() throws Exception
    {
        Command c = Command.fromString("");
        assertNull(c);
    }

    @Test
    public void testCommandDeserializeName() throws Exception
    {
        Command c = Command.fromString("Test");
        assertNotNull(c);
        assertEquals(c.getCommandName(), "Test");
        assertEquals(c.getArgumentCount(), 0);
    }

    @Test
    public void testCommandDeserializeArgument() throws Exception
    {
        Command c = Command.fromString("Test#an_argument");
        assertNotNull(c);
        assertEquals(c.getCommandName(), "Test");
        assertEquals(c.getArgumentCount(), 1);
        assertEquals(c.getArgument(0), "an_argument");
    }

    @Test
    public void testCommandDeserializeArguments() throws Exception
    {
        Command c = Command.fromString("Test#an_argument#an_argument2");
        assertNotNull(c);
        assertEquals(c.getCommandName(), "Test");
        assertEquals(c.getArgumentCount(), 2);
        assertEquals(c.getArgument(0), "an_argument");
        assertEquals(c.getArgument(1), "an_argument2");
    }
}