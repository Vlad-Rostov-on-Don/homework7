package ru.sbt;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class PluginManager {
    private final String pluginRootDirectory;

    public PluginManager(String pluginRootDirectory) {
        this.pluginRootDirectory = pluginRootDirectory;
    }

    public Plugin load(String pluginName, String pluginClassName) throws Exception {

        File pluginDir = new File(pluginRootDirectory, pluginName);
        if (!pluginDir.exists() || !pluginDir.isDirectory()) {
            throw new IllegalArgumentException("Каталог плагинов не найден: " + pluginDir.getAbsolutePath());
        }

        URL[] urls = {pluginDir.toURI().toURL()};
        PluginClassLoader classLoader = new PluginClassLoader(urls);

        Class<?> pluginClass = classLoader.loadClass(pluginClassName);
        Object pluginInstance = pluginClass.getDeclaredConstructor().newInstance();

        if (!(pluginInstance instanceof Plugin)) {
            throw new IllegalArgumentException("Класс " + pluginClassName + " не реализован интерфейс плагина");
        }

        return (Plugin) pluginInstance;
    }

    private static class PluginClassLoader extends URLClassLoader {
        public PluginClassLoader(URL[] urls) {
            super(urls, null);
        }

        @Override
        protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            Class<?> klass = findLoadedClass(name);
            if (klass == null) {
                try {
                    klass = findClass(name);
                } catch (ClassNotFoundException e) {
                }
            }
            if (klass == null) {
                klass = getSystemClassLoader().loadClass(name);
            }
            if (resolve) {
                resolveClass(klass);
            }
            return klass;
        }
    }
}