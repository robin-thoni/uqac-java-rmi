package com.uqac.rthoni.java_rmi.server.executors;

import com.uqac.rthoni.java_rmi.common.Command;
import com.uqac.rthoni.java_rmi.common.ReflectionUtil;
import com.uqac.rthoni.java_rmi.server.ServerApplication;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by robin on 9/16/16.
 */
public class WriteExecutor extends AbstractCommandExecutor {
    @Override
    public String getCommandName() {
        return "ecriture";
    }

    @Override
    public String run(Command command, ServerApplication server) throws Exception {
        String id = command.getArgument(0);
        String fieldName = command.getArgument(1);
        String methodName = String.format("set%s%s", fieldName.substring(0, 1).toUpperCase(), fieldName.substring(1));
        String value = command.getArgument(2);

        Object obj = server.getObject(id);
        Class objClass = obj.getClass();
        Field field = objClass.getDeclaredField(fieldName);
        Object typedValue = ReflectionUtil.toObject(field.getDeclaringClass(), value);

        try {
            field.set(obj, typedValue);
        } catch (IllegalAccessException e) {
            Method method = objClass.getDeclaredMethod(methodName, typedValue.getClass());
            method.invoke(obj, typedValue);
        }

        return null;
    }
}
