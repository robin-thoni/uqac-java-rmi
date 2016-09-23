package com.uqac.rthoni.java_rmi.server.executors;

import com.uqac.rthoni.java_rmi.server.ServerApplication;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by robin on 9/16/16.
 */
public class MethodExecutorTest extends AbstractTest {

    @Test
    public void test1() throws Exception
    {
        ServerApplication app = getServer();
        String res = runCommands(app, "creation#com.uqac.rthoni.java_rmi.server.executors.TestDbo#test",
                "fonction#test#getMyself#java.lang.String:a_value");
        Assert.assertEquals("a_value", res);
    }

    @Test
    public void test2() throws Exception
    {
        ServerApplication app = getServer();
        String res = runCommands(app, "creation#com.uqac.rthoni.java_rmi.server.executors.TestDbo#test",
                "fonction#test#uselessMethod#int:42,int:24,java.lang.String:a_value");
        Assert.assertEquals("NULL", res);
    }

    @Test
    public void test3() throws Exception
    {
        ServerApplication app = getServer();
        String res = runCommands(app, "creation#com.uqac.rthoni.java_rmi.server.executors.TestDbo#test",
                "creation#com.uqac.rthoni.java_rmi.server.executors.TestDbo#test2",
                "ecriture#test2#privateString#some_value",
                "fonction#test#getMyString#com.uqac.rthoni.java_rmi.server.executors.TestDbo:ID(test2)");
        Assert.assertEquals("some_value", res);
    }

    @Test
    public void test4() throws Exception
    {
        ServerApplication app = getServer();
        String res = runCommands(app, "creation#com.uqac.rthoni.java_rmi.server.executors.TestDbo#test",
                "creation#com.uqac.rthoni.java_rmi.server.executors.TestDbo#test2",
                "fonction#test2#setPrivateString#java.lang.String:some_value",
                "fonction#test#getMyString#com.uqac.rthoni.java_rmi.server.executors.TestDbo:ID(test2)");
        Assert.assertEquals("NULL\nsome_value", res);
    }

    @Test
    public void test5() throws Exception
    {
        ServerApplication app = getServer();
        String res = runCommands(app, "creation#com.uqac.rthoni.java_rmi.server.executors.TestDbo#test",
                "creation#com.uqac.rthoni.java_rmi.server.executors.TestDbo#test2",
                "fonction#test#setPrivateString#java.lang.String:some_value",
                "fonction#test#getPrivateString");
        Assert.assertEquals("NULL\nsome_value", res);
    }
}