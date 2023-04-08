package me.maikeru.hello_world;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class HelloWorld extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        // Redundant Bukkit.getLogger().info("Hello world!");
        this.getCommand("hello").setExecutor(new CommandHelloHandler());
        this.getCommand("pm").setExecutor(new CommandPM());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }

    public void disablePlugin() {
        // Should this happen, it means something went really wrong
        Bukkit.getServer().getPluginManager().disablePlugin(this);
    }
    

}
