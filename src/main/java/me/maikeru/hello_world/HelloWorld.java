package me.maikeru.hello_world;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class HelloWorld extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("Hello world!");
        this.getCommand("hello").setExecutor(new CommandHelloHandler());
        this.getCommand("pm").setExecutor(new CommandPM());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("Goodbye world!");

    }
    

}
