package com.uqac.rthoni.java_rmi.server.executors;

import com.uqac.rthoni.java_rmi.common.Command;
import com.uqac.rthoni.java_rmi.server.ServerApplication;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by robin on 9/16/16.
 */
public class ClassLoaderExecutor extends AbstractCommandExecutor {
    @Override
    public String getCommandName() {
        return "chargement";
    }

    @Override
    public String run(Command command, ServerApplication server) throws Exception {
        String className = command.getArgument(0, false);
        String classDir = command.getArgument(1, false);

        File root = new File(server.getClassDir(), classDir);
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { root.toURI().toURL() });
        server.addClassLoader(classLoader);
//        Class<?> cls = Class.forName(className, true, classLoader); // Should print "hello".
//        Object instance = cls.newInstance(); // Should print "world".
//        System.out.println(instance); // Should print "test.Test@hashcode".

        return null;
    }
}
