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
import java.nio.file.*;

public class CommandCreative implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        String absPath = Bukkit.getPluginsFolder().getAbsolutePath() + "/../world/playerdata/" + player.getUniqueId();
        File fromFile = new File(absPath + ".dat");
        File toFile = new File(absPath + ".BACKUPdat");

        if (Files.exists(toFile.toPath())) {
            commandSender.sendMessage(ChatColor.RED + "You can't have more than one backup file!");
            return false;
        }

        try {
            Files.move(fromFile.toPath(), toFile.toPath(), StandardCopyOption.ATOMIC_MOVE);
            player.loadData();
            player.setGameMode(GameMode.CREATIVE);
        } catch (IOException e) {
            Bukkit.getLogger().severe(e.getMessage());
            return false;
        }


        return true;
    }
    // Remember user's data and then change their inventory
}
