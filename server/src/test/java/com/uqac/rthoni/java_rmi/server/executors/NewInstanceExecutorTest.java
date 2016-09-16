package com.uqac.rthoni.java_rmi.server.executors;

import com.uqac.rthoni.java_rmi.common.Command;
import com.uqac.rthoni.java_rmi.server.ServerApplication;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by robin on 9/16/16.
 */
public class NewInstanceExecutorTest extends AbstractTest {

    @Test
    public void test1() throws Exception
    {
        ServerApplication app = getServer();
        String res = runCommand(app, "creation#com.uqac.rthoni.java_rmi.server.executors.TestDbo#test");
        assertNull(res);
        Object obj = app.getObject("test");
        assertNotNull(obj);
        assertEquals(obj.toString(), "privateString=default_value, privateInt=24, publicInt=42, aPrivateField=0, publicFloat=0.420000, publicDouble=0.125000, publicBool=true");
    }

    @Test
    public void test2() throws Exception
    {
        ServerApplication app = getServer();
        String res = runCommand(app, "creation#java.lang.String#mystr");
        assertNull(res);
        Object obj = app.getObject("mystr");
        assertNotNull(obj);
        assertEquals(obj.toString(), "");
    }

    @Test(expected = ClassNotFoundException.class)
    public void test3() throws Exception
    {
        ServerApplication app = getServer();
        runCommand(app, "creation#not_existing_class#myobj");
    }

}