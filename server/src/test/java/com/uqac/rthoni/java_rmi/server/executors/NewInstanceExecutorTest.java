package com.uqac.rthoni.java_rmi.server.executors;

import com.uqac.rthoni.java_rmi.common.Command;
import com.uqac.rthoni.java_rmi.server.ServerApplication;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by robin on 9/16/16.
 */
public class NewInstanceExecutorTest {

    public String runCommand(String str, ServerApplication app) throws Exception {
        Command command = Command.fromString(str);
        AbstractCommandExecutor executor = app.getExecutor(command);
        return executor.run(command, app);
    }

    public ServerApplication getServer() throws Exception {
        ServerApplication app = new ServerApplication();
        app.loadExecutors();
        return app;
    }

    @Test
    public void test1() throws Exception
    {
        ServerApplication app = getServer();
        String res = runCommand("creation#com.uqac.rthoni.java_rmi.server.TestDbo#test", app);
        assertNull(res);
        Object obj = app.getObject("test");
        assertNotNull(obj);
    }

    @Test
    public void test2() throws Exception
    {
        ServerApplication app = getServer();
        String res = runCommand("creation#java.lang.String#mystr", app);
        assertNull(res);
        Object obj = app.getObject("mystr");
        assertNotNull(obj);
        assertEquals(obj.toString(), "");
    }

    @Test(expected = ClassNotFoundException.class)
    public void test3() throws Exception
    {
        runCommand("creation#not_existing_class#myobj", getServer());
    }

}