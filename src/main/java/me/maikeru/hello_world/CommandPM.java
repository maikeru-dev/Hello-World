package me.maikeru.hello_world;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandPM implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, String command_name, String[] strings) {
        String name = strings[0];
        if (name == null || name.equals("")) {
            commandSender.sendMessage(ChatColor.RED + "Who are you sending your message to? Usage: /pm (name) (message...)");
            return false;
        } else if (strings == null || strings.length == 1 ) {
            commandSender.sendMessage(ChatColor.RED + "What is your message? Usage: /pm (name) (message...)");
            return false;
        } else if (Bukkit.getPlayer(name) == null || name.equalsIgnoreCase("console")) {
            commandSender.sendMessage(ChatColor.RED + "That player isn't online right now, check your spelling. Usage: /pm (name) (message...)");
            return false;
        }
        String message = compileStrArray(strings, 1);

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            player.sendMessage(ChatColor.GRAY + "" + player.displayName().toString() + " -> " + name + " : " + message);
            if (name.equalsIgnoreCase("console")) { // intended receiver is console
                Bukkit.getLogger().info(ChatColor.GRAY + "" + player.displayName().toString() + " -> Console : " + message);
            }else {
                Player receiver = Bukkit.getPlayer(name);
                receiver.sendMessage(ChatColor.GRAY + "" + player.displayName().toString() + " -> " + receiver.displayName().toString() + " : " + message);
            }

        }else if (commandSender instanceof ConsoleCommandSender) {
            ConsoleCommandSender console = (ConsoleCommandSender) commandSender;
            console.sendMessage(ChatColor.GRAY + "Console -> " + name + " : " + message);

            if (name.equalsIgnoreCase("console")) {
                Bukkit.getLogger().info(ChatColor.GRAY + "Console -> Console : " + message);
            }else {
                Player receiver = Bukkit.getPlayer(name);
                receiver.sendMessage(ChatColor.GRAY + "Console -> " + receiver.displayName().toString() + " : " + message);
            }
        }

        return true;
    }
    private String compileStrArray(@NotNull String[] strings, int beginningIndex) {
        StringBuilder compiledString = new StringBuilder();
        for (int i = beginningIndex; i < strings.length; i++) {
            compiledString.append(" " + strings[i]);
        }
        return compiledString.toString();
    }
}
