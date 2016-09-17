package com.uqac.rthoni.java_rmi.server.executors;

import com.uqac.rthoni.java_rmi.common.Command;
import com.uqac.rthoni.java_rmi.common.ReflectionUtil;
import com.uqac.rthoni.java_rmi.server.ServerApplication;

/**
 * Created by robin on 9/16/16.
 */
public class NewInstanceExecutor extends AbstractCommandExecutor {
    @Override
    public String getCommandName() {
        return "creation";
    }

    @Override
    public String run(Command command, ServerApplication server) throws Exception {
        String className = command.getArgument(0, false);
        String id = command.getArgument(1, false);
        Object obj = ReflectionUtil.newInstance(className, server.getClassLoaders());
//        Class c = Class.forName(className);
//        Object obj = c.newInstance();
        server.addObject(id, obj);
        return null;
    }
}
