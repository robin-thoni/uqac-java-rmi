package com.uqac.rthoni.java_rmi.common;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * Created by robin on 9/16/16.
 */
public class ReflectionUtil {

    public static Vector<Class> getClassesForPackage(String pckgname)
            throws ClassNotFoundException {

        Vector<Class> classes = new Vector<>();
        ArrayList<File> directories = new ArrayList<>();
        try {
            ClassLoader cld = Thread.currentThread().getContextClassLoader();

            Enumeration<URL> resources = cld.getResources(pckgname.replace('.', '/'));
            while (resources.hasMoreElements()) {
                URL res = resources.nextElement();
                if (res.getProtocol().equalsIgnoreCase("jar")){
                    JarURLConnection conn = (JarURLConnection) res.openConnection();
                    JarFile jar = conn.getJarFile();
                    for (JarEntry e: Collections.list(jar.entries())){

                        if(e.getName().startsWith(pckgname.replace('.', '/'))
                                && e.getName().endsWith(".class") && !e.getName().contains("$")){
                            String className = e.getName().replace("/",".").substring(0,e.getName().length() - 6);
                            classes.add(Class.forName(className));
                        }
                    }
                }else
                    directories.add(new File(URLDecoder.decode(res.getPath(), "UTF-8")));
            }
        } catch (Exception e) {
            throw new ClassNotFoundException(String.format("Failed to get jar info: %s", e.getMessage()));
        }

        // For every directory identified capture all the .class files
        for (File directory : directories) {
            if (directory.exists()) {
                // Get the list of the files contained in the package
                String[] files = directory.list();
                for (String file : files) {
                    // we are only interested in .class files
                    if (file.endsWith(".class")) {
                        // removes the .class extension
                        classes.add(Class.forName(pckgname + '.' + file.substring(0, file.length() - 6)));
                    }
                }
            } else {
                throw new ClassNotFoundException(String.format("Failed to get class file for %s", directory.getName()));
            }
        }
        return classes;
    }

    public static Vector<Class> getClassesOfSuperClass(String thePackage, Class superClass)
            throws ClassNotFoundException {
        Vector<Class> classList = new Vector<>();
        classList.addAll(getClassesForPackage(thePackage).stream()
                .filter(discovered -> Collections.singletonList(discovered.getSuperclass())
                        .contains(superClass)).collect(Collectors.toList()));

        return classList;
    }

    public static Object toObject(Class className, String value)
    {
        if (className == Boolean.class || className == boolean.class) {
            return Boolean.parseBoolean(value);
        }
        if (className == Byte.class || className == byte.class) {
            return Byte.parseByte(value);
        }
        if (className == Short.class || className == short.class) {
            return Short.parseShort(value);
        }
        if (className == Integer.class || className == int.class) {
            return Integer.parseInt(value);
        }
        if (className == Long.class || className == long.class) {
            return Long.parseLong(value);
        }
        if (className == Float.class || className == float.class) {
            return Float.parseFloat(value);
        }
        if (className == Double.class || className == double.class) {
            return Double.parseDouble(value);
        }
        return value;
    }

    public static Class getClass(String className, List<ClassLoader> loaders) throws ClassNotFoundException {
        if (className.equals("boolean")) {
            return boolean.class;
        }
        if (className.equals("byte")) {
            return byte.class;
        }
        if (className.equals("short")) {
            return short.class;
        }
        if (className.equals("int")) {
            return int.class;
        }
        if (className.equals("long")) {
            return long.class;
        }
        if (className.equals("float")) {
            return float.class;
        }
        if (className.equals("double")) {
            return double.class;
        }
        for (ClassLoader classLoader : loaders) {
            try {
                return (Class) Class.forName(className, true, classLoader);
            } catch (ClassNotFoundException e) {
            }
        }
        return Class.forName(className);
    }

    public static Object newInstance(String className, List<ClassLoader> loaders)
            throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        Class c = getClass(className, loaders);
        return c.newInstance();
    }
}
