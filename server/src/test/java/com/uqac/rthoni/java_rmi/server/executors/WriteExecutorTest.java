package com.uqac.rthoni.java_rmi.server.executors;

import com.uqac.rthoni.java_rmi.server.ServerApplication;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by robin on 9/16/16.
 */
public class WriteExecutorTest extends AbstractTest {

    @Test
    public void test1() throws Exception
    {
        ServerApplication app = getServer();
        String res = runCommands(app, "creation#com.uqac.rthoni.java_rmi.server.executors.TestDbo#test",
                "ecriture#test#publicInt#24",
                "lecture#test#publicInt");
        assertEquals(res, "24");
        Object obj = app.getObject("test");
        assertNotNull(obj);
        assertEquals(obj.toString(), "privateString=default_value, privateInt=24, publicInt=24, aPrivateField=0, publicFloat=0.420000, publicDouble=0.125000, publicBool=true");
    }

    @Test
    public void test2() throws Exception
    {
        ServerApplication app = getServer();
        String res = runCommands(app, "creation#com.uqac.rthoni.java_rmi.server.executors.TestDbo#test",
                "ecriture#test#privateInt#42",
                "lecture#test#privateInt");
        assertEquals(res, "42");
        Object obj = app.getObject("test");
        assertNotNull(obj);
        assertEquals(obj.toString(), "privateString=default_value, privateInt=42, publicInt=42, aPrivateField=0, publicFloat=0.420000, publicDouble=0.125000, publicBool=true");
    }

    @Test
    public void test3() throws Exception
    {
        ServerApplication app = getServer();
        String res = runCommands(app, "creation#com.uqac.rthoni.java_rmi.server.executors.TestDbo#test",
                "ecriture#test#privateString#a_value",
                "lecture#test#privateString");
        assertEquals(res, "a_value");
        Object obj = app.getObject("test");
        assertNotNull(obj);
        assertEquals(obj.toString(), "privateString=a_value, privateInt=24, publicInt=42, aPrivateField=0, publicFloat=0.420000, publicDouble=0.125000, publicBool=true");
    }

    @Test
    public void test4() throws Exception
    {
        ServerApplication app = getServer();
        String res = runCommands(app, "creation#com.uqac.rthoni.java_rmi.server.executors.TestDbo#test",
                "ecriture#test#publicFloat#4242.5",
                "lecture#test#publicFloat");
        assertEquals(res, "4242.5");
        Object obj = app.getObject("test");
        assertNotNull(obj);
        assertEquals(obj.toString(), "privateString=default_value, privateInt=24, publicInt=42, aPrivateField=0, publicFloat=4242.500000, publicDouble=0.125000, publicBool=true");
    }

    @Test
    public void test5() throws Exception
    {
        ServerApplication app = getServer();
        String res = runCommands(app, "creation#com.uqac.rthoni.java_rmi.server.executors.TestDbo#test",
                "ecriture#test#publicBool#false",
                "lecture#test#publicBool");
        assertEquals(res, "false");
        Object obj = app.getObject("test");
        assertNotNull(obj);
        assertEquals(obj.toString(), "privateString=default_value, privateInt=24, publicInt=42, aPrivateField=0, publicFloat=0.420000, publicDouble=0.125000, publicBool=false");
    }
}