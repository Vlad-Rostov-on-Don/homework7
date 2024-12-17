package my.plugin;

import ru.sbt.Plugin;

public class MyPlugin implements Plugin {
    @Override
    public void doUsefull() {
        System.out.println("Плагин работает!");
    }
}