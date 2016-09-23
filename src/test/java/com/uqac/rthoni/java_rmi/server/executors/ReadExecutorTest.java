package com.uqac.rthoni.java_rmi.server.executors;

import com.uqac.rthoni.java_rmi.server.ServerApplication;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by robin on 9/16/16.
 */
public class ReadExecutorTest extends AbstractTest {

    @Test
    public void test1() throws Exception
    {
        ServerApplication app = getServer();
        String res = runCommands(app, "creation#com.uqac.rthoni.java_rmi.server.executors.TestDbo#test",
                "lecture#test#publicInt");
        Assert.assertEquals("42", res);
    }

    @Test
    public void test2() throws Exception
    {
        ServerApplication app = getServer();
        String res = runCommands(app, "creation#com.uqac.rthoni.java_rmi.server.executors.TestDbo#test",
                "lecture#test#privateInt");
        Assert.assertEquals("24", res);
    }

    @Test
    public void test3() throws Exception
    {
        ServerApplication app = getServer();
        String res = runCommands(app, "creation#com.uqac.rthoni.java_rmi.server.executors.TestDbo#test",
                "lecture#test#privateString");
        Assert.assertEquals("default_value", res);
    }

    @Test(expected = NoSuchMethodException.class)
    public void test4() throws Exception
    {
        ServerApplication app = getServer();
        runCommands(app, "creation#com.uqac.rthoni.java_rmi.server.executors.TestDbo#test",
                "lecture#test#aPrivateField");
    }

    @Test(expected = NullPointerException.class)
    public void test5() throws Exception
    {
        ServerApplication app = getServer();
        runCommands(app, "creation#com.uqac.rthoni.java_rmi.server.executors.TestDbo#test",
                "lecture#test2#privateString");
    }

    @Test
    public void test6() throws Exception
    {
        ServerApplication app = getServer();
        String res = runCommands(app, "creation#com.uqac.rthoni.java_rmi.server.executors.TestDbo#test",
                "lecture#test#publicFloat");
        Assert.assertEquals("0.42", res);
    }

    @Test
    public void test7() throws Exception
    {
        ServerApplication app = getServer();
        String res = runCommands(app, "creation#com.uqac.rthoni.java_rmi.server.executors.TestDbo#test",
                "lecture#test#publicDouble");
        Assert.assertEquals("0.125", res);
    }

    @Test
    public void test8() throws Exception
    {
        ServerApplication app = getServer();
        String res = runCommands(app, "creation#com.uqac.rthoni.java_rmi.server.executors.TestDbo#test",
                "lecture#test#publicBool");
        Assert.assertEquals("true", res);
    }
}