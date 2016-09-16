package com.uqac.rthoni.java_rmi.server.executors;

import com.uqac.rthoni.java_rmi.common.Command;
import com.uqac.rthoni.java_rmi.common.ReflectionUtil;

import java.util.Vector;

/**
 * Created by robin on 9/16/16.
 */
public abstract class AbstractCommandExecutor {

    public static Vector<Class> getAllExecutors() throws ClassNotFoundException
    {
        return ReflectionUtil.getClassesOfSuperClass(AbstractCommandExecutor.class.getPackage().getName(), AbstractCommandExecutor.class);
    }

    public abstract String getCommandName();

    public abstract String run(Command command) throws Exception;
}
