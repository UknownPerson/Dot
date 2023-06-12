package xyz.Dot.api;


import xyz.Dot.api.line.annotation.InjectTarget;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public final class Plugin {
    private final LinkedHashMap<String,Class<?>> classMap = new LinkedHashMap<>();

    public Plugin(File file) throws IOException {
        final URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{new URL("file:" + file.getAbsolutePath())});

        final JarFile jarFile = new JarFile(file);
        final Enumeration<JarEntry> entries = jarFile.entries();

        JarEntry entry;
        while (entries.hasMoreElements()) {
            entry = entries.nextElement();

            if (entry.getName().endsWith(".class")) {
                try {
                    final String name = entry.getName().split(".class")[0].replaceAll("/",".");
                    final Class<?> aClass = urlClassLoader.loadClass(name);
                    classMap.put(name,aClass);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        jarFile.close();

        for (Class<?> value : classMap.values()) {
            for (Method declaredMethod : value.getDeclaredMethods()) {
                if (!declaredMethod.isAccessible()) {
                    declaredMethod.setAccessible(true);
                }

                final InjectTarget annotation = declaredMethod.getDeclaredAnnotation(InjectTarget.class);

                if (annotation != null) {
                    try {
                        declaredMethod.invoke(null);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    public LinkedHashMap<String, Class<?>> getClassMap() {
        return classMap;
    }
}
