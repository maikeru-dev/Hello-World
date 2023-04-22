package me.maikeru.hello_world;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CommandCreative implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        String absPath = Bukkit.getPluginsFolder().getAbsolutePath().toString() + "/../world/playerdata/" + player.getUniqueId().toString();
        File fromFile = new File(absPath + ".dat");
        File toFile = new File(absPath + ".BACKUPdat");

        boolean backupAction = fromFile.renameTo(toFile);

        if (!backupAction) {
            commandSender.sendMessage(ChatColor.RED + "You can't have more than one backup file!");
            return false;
        }else {
            // Your code here executes after 5 seconds!
            CompletableFuture.delayedExecutor(5, TimeUnit.SECONDS).execute(player::loadData);
            player.setGameMode(GameMode.CREATIVE);
        }



        return true;
    }
    // Remember user's data and then change their inventory
}
