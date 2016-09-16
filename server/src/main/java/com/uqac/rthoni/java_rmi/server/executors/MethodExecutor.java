package com.uqac.rthoni.java_rmi.server.executors;

import com.uqac.rthoni.java_rmi.common.Command;
import com.uqac.rthoni.java_rmi.common.ReflectionUtil;
import com.uqac.rthoni.java_rmi.server.ServerApplication;
import javafx.util.Pair;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by robin on 9/16/16.
 */
public class MethodExecutor extends AbstractCommandExecutor {
    @Override
    public String getCommandName() {
        return "fonction";
    }

    @Override
    public String run(Command command, ServerApplication server) throws Exception {
        String id = command.getArgument(0);
        String methodName = command.getArgument(1);
        Object obj = server.getObject(id);
        List<Pair<Class, String>> argClasses = command.getArgmumentAsList(2).stream().map(v -> {
            String[] split = v.split(":");
            try {
                return new Pair<>(ReflectionUtil.getClass(split[0]), split[1]);
            } catch (ClassNotFoundException e) {
                return null;
            }
        }).collect(Collectors.toList());
        List<Object> arguments = argClasses.stream().map(p -> {
            String value = p.getValue();
            if (value.startsWith("ID(") && value.endsWith(")")) {
                value = value.substring(3, value.length() - 2);
                return server.getObject(value);
            }
            return ReflectionUtil.toObject(p.getKey(), value);
        }).collect(Collectors.toList());
        List<Class> classes = argClasses.stream().map(Pair::getKey).collect(Collectors.toList());

        Method method = obj.getClass().getDeclaredMethod(methodName, classes.toArray(new Class[classes.size()]));
        Object result = method.invoke(obj, arguments.toArray());

        return (result == null ? "NULL" : result.toString());
    }
}
