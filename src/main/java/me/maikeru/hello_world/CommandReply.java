package me.maikeru.hello_world;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.UUID;

public class CommandReply implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        if (strings.length < 1) {
            commandSender.sendMessage(ChatColor.RED + "What is your msg? Usage: /reply (message...)" );
            return false;
        }

        Player sender = (Player) commandSender;
        UUID receiverUUID = HelloWorld.getLastMessaged(sender.getUniqueId());

        if (receiverUUID == null) {
            sender.sendMessage(ChatColor.GRAY + "No-one has pm'd you!");
            return false;
        }

        Player receiver = Bukkit.getPlayer(receiverUUID);
        if (!receiver.isOnline()) {
            sender.sendMessage(ChatColor.GRAY + "The person that messaged you last is no longer online!");
            return false;
        }
        String msgContent = compileMsg(strings);
        TextComponent msg = Component.text()
                .append(sender.displayName())
                .append(Component.text().content(" -> ").color(TextColor.color(128,128,128)))
                .append(receiver.displayName())
                .append(Component.text(" : " + msgContent).color(TextColor.color(128,128,128)))
                .build();

        receiver.sendMessage(msg);
        sender.sendMessage(msg);

        HelloWorld.updateReplyMap(sender.getUniqueId(), receiver.getUniqueId());

        return true;
    }
    private String compileMsg(String[] args) {
        StringBuilder compiledString = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            compiledString.append(" " + args[i]);
        }
        return compiledString.toString();
    }
}

