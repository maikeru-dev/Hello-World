package me.maikeru.hello_world;

import com.google.gson.Gson;
import net.kyori.adventure.text.BuildableComponent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.io.Console;

public class CommandPM implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, String command_name, String[] strings) {
        String receiverName = strings[0];
        if (receiverName == null) { // 1. Validation
            commandSender.sendMessage(
                    ChatColor.RED + "Who are you sending your message to? Usage: /pm (name) (message...)");
            return false;
        } else if (strings.length <= 1 ) {
            commandSender.sendMessage(
                    ChatColor.RED + "What is your message? Usage: /pm (name) (message...)");
            return false;
        } else if (!receiverName.equalsIgnoreCase("console") && Bukkit.getPlayer(receiverName) == null) {
            commandSender.sendMessage(
                    ChatColor.RED + "That player isn't online right now, check your spelling? Usage: /pm (name) (message...)");
            return false;
        }
        String msg_content = compileStrArray(strings, 1); // String.join isn't usable here.

        TextComponent senderDisplayName = null;
        if (commandSender instanceof ConsoleCommandSender) {
            senderDisplayName = Component.text("Console")
                    .color(TextColor.color(61, 235, 52));
        } else if (commandSender instanceof Player) {
            senderDisplayName = (TextComponent) ((Player) commandSender).displayName();
        } else return false;

        TextComponent receiverDisplayName = null;
        if (receiverName.equalsIgnoreCase("console")){
            receiverDisplayName = Component.text("Console")
                    .color(TextColor.color(61, 235, 52));
        } else {
            receiverDisplayName = (TextComponent) Bukkit.getPlayer(receiverName).displayName();
        }

        final TextComponent msg = Component.text()
                .append(senderDisplayName)
                .append(Component.text().content(" -> ").color(TextColor.color(128,128,128)))
                .append(receiverDisplayName)
                .append(Component.text(" : " + msg_content).color(TextColor.color(128,128,128)))
                .build();

        if(receiverName.equalsIgnoreCase("console")) { // Send the msg to receiver
            Bukkit.getLogger().info(
                    senderDisplayName.content() + " -> Console : " + msg_content);
        }else {
            Bukkit.getPlayer(receiverName).sendMessage(msg);
        }

        if (commandSender instanceof Player ) {
            commandSender.sendMessage(msg);
        } else {
            Bukkit.getLogger().info("Console -> " + receiverDisplayName.content() + " : " + msg_content);
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
