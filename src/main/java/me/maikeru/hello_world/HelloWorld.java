package me.maikeru.hello_world;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public final class HelloWorld extends JavaPlugin {
    private static int sleepingCount = 0;
    private static ArrayList<UUID> skipNightVotes = new ArrayList<>();
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
        this.getCommand("voteskip").setExecutor(new CommandVoteSkip());
        getServer().getPluginManager().registerEvents(new ListenerSleep(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void disablePlugin() {
        // Should this happen, it means something went really wrong
        Bukkit.getServer().getPluginManager().disablePlugin(this);
    }
    public static class VoteSystem {
        public static void addVoteTotal(UUID playerId) {
            skipNightVotes.add(playerId);
        }
        public static void resetVoteTotal() {
            skipNightVotes = new ArrayList<>();
        }
        public static ArrayList<UUID> getVotes() {
            return skipNightVotes;
        }
        public static boolean checkVoteExists(UUID playerId) {
            return skipNightVotes.contains(playerId);
        }
        public static int getVoteCount() {
            return skipNightVotes.size();
        }
        public static void removeVote(UUID playerId) {
            skipNightVotes.remove(playerId);
        }
    }
    public static int updateCurrentPlayerSleepingCount(int amount){
        sleepingCount += amount;
        return sleepingCount;
    }
    public static void resetCurrentPlayerSleepingCount(){
        sleepingCount = 0;
    }
    public static int getCurrentPlayerSleepingCount(){
        return sleepingCount;
    }


    public static UUID getLastMessaged(UUID user){
        return replyMap.get(user);
    }
    public static void updateReplyMap(UUID from, UUID to) {
        replyMap.put(to, from);
    }

}
