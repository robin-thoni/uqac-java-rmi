package com.uqac.rthoni.java_rmi.server.executors;

import com.uqac.rthoni.java_rmi.common.Command;
import com.uqac.rthoni.java_rmi.server.ServerApplication;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 * Created by robin on 9/16/16.
 */
public class ClassBuilderExecutor extends AbstractCommandExecutor {
    @Override
    public String getCommandName() {
        return "compilation";
    }

    @Override
    public String run(Command command, ServerApplication server) throws Exception
    {
        Vector<String> files = command.getArgumentAsList(0, false);
        String classDir = command.getArgument(1, false);
        File classFile = new File(server.getClassDir(), classDir);
        classFile.mkdirs();

        List<String> sources = files.stream().map(f -> new File(server.getSourceDir(), f).getPath() ).collect(Collectors.toList());
        Vector<String> arguments = new Vector<>();
        arguments.add("-d");
        arguments.add(classFile.getPath());
        arguments.addAll(sources);

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        compiler.run(null, outputStream, outputStream, arguments.toArray(new String[arguments.size()]));

// Load and instantiate compiled class.
//        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { root.toURI().toURL() });
//        Class<?> cls = Class.forName("test.Test", true, classLoader); // Should print "hello".
//        Object instance = cls.newInstance(); // Should print "world".
//        System.out.println(instance); // Should print "test.Test@hashcode".

        return new String(outputStream.toByteArray(), StandardCharsets.UTF_8);
    }
}
