package com.uqac.rthoni.java_rmi.server.executors;

import com.uqac.rthoni.java_rmi.common.Command;
import com.uqac.rthoni.java_rmi.server.ServerApplication;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by robin on 9/16/16.
 */
public class ReadExecutor extends AbstractCommandExecutor {
    @Override
    public String getCommandName() {
        return "lecture";
    }

    @Override
    public String run(Command command, ServerApplication server) throws Exception {
        String id = command.getArgument(0);
        String fieldName = command.getArgument(1);
        String methodName = String.format("get%s%s", fieldName.substring(0, 1).toUpperCase(), fieldName.substring(1));

        Object obj = server.getObject(id);
        Class objClass = obj.getClass();
        Field field = objClass.getDeclaredField(fieldName);
        Object result;
        String stringResult;

        try {
            result = field.get(obj);
        } catch (IllegalAccessException e) {
            Method method = objClass.getDeclaredMethod(methodName);
            result = method.invoke(obj);
        }

        if (result != null) {
            stringResult = result.toString();
        }
        else {
            stringResult = "NULL";
        }

        return stringResult;
    }
}
