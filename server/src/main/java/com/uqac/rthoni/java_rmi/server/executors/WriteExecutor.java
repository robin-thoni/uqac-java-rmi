package com.uqac.rthoni.java_rmi.server.executors;

import com.uqac.rthoni.java_rmi.common.Command;
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

        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            try {
                Method method = objClass.getDeclaredMethod(methodName, String.class);
                method.invoke(obj, value);
            }
            catch (NoSuchMethodException e2) {
                try {
                    Method method = objClass.getDeclaredMethod(methodName, int.class);
                    method.invoke(obj, Integer.parseInt(value));
                }
                catch (NoSuchMethodException e3) {
                    try {
                        Method method = objClass.getDeclaredMethod(methodName, float.class);
                        method.invoke(obj, Float.parseFloat(value));
                    }
                    catch (NoSuchMethodException e4) {
                        try {
                            Method method = objClass.getDeclaredMethod(methodName, boolean.class);
                            method.invoke(obj, Boolean.parseBoolean(value));
                        }
                        catch (NoSuchMethodException e5) {
                            throw new Exception("No such setter");
                        }
                    }
                }
            }
        }

        return null;
    }
}
