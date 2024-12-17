package ru.sbt;

public class Main {
    public static void main(String[] args) {
        try {
            PluginManager pluginManager = new PluginManager("plugins");

            Plugin plugin = pluginManager.load("MyPlugin", "my.plugin.MyPlugin");

            plugin.doUsefull();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}