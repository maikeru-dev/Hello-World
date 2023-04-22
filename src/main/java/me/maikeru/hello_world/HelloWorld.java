package me.maikeru.hello_world;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class HelloWorld extends JavaPlugin {

    private static HashMap<UUID, UUID> replyMap = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        // Redundant: // Bukkit.getLogger().info("Hello world!");
        this.getCommand("hello").setExecutor(new CommandHelloHandler());
        this.getCommand("pm").setExecutor(new CommandPM());
        this.getCommand("reply").setExecutor(new CommandReply());
        this.getCommand("spawntext").setExecutor(new CommandSpawnText());
        this.getCommand("creative").setExecutor(new CommandCreative());
        this.getCommand("killtext").setExecutor(new CommandKillText());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void disablePlugin() {
        // Should this happen, it means something went really wrong
        Bukkit.getServer().getPluginManager().disablePlugin(this);
    }
    public static UUID getLastMessaged(UUID user){
        return replyMap.get(user);
    }
    public static void updateReplyMap(UUID from, UUID to) {
        replyMap.put(to, from);
    }

}
