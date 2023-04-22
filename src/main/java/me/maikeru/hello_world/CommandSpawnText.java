package me.maikeru.hello_world;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

// Get JSON file responsible for holding { UUID : given_name }
// Create armor stand
// Pull armor stand into JSON file
// Finish


public class CommandSpawnText implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        /* Invisible Armor stands with TextComponent names */
        if (!(commandSender instanceof Player)) return false;

        Player player = ((Player) commandSender);

        TextComponent standName = Component.text()
                .content(compileMsg(args, 1))
                .color(TextColor.color(255, 0, 0))
                .build();

        ArmorStand armorStand = player.getWorld().spawn(player.getLocation(), ArmorStand.class);
        try {
            if (args.length < 1) throw new invalidArgsException();

            armorStand.setGravity(false);
            armorStand.setCustomNameVisible(true);
            armorStand.setCollidable(false);
            armorStand.customName(standName);
            armorStand.setInvisible(true);
            armorStand.setInvulnerable(true);

            flatFileAccessor accessor = new flatFileAccessor();

            accessor.appendEntry(player.getUniqueId(), args[0], armorStand.getUniqueId());
        } catch (IOException e) {
            e.printStackTrace();
            armorStand.setHealth(0);
            return false;
        } catch (CustomException e) {
            commandSender.sendMessage(
                    Component.text()
                            .content(e.getMessage())
                            .color(TextColor.color(255, 0, 0))
                            .build());
            armorStand.setHealth(0);
            return false;
        }
        return true;

        // Create armor stand

        // Send armor stand to list and append that to a flat file
    }
    private class invalidArgsException extends CustomException {
        public invalidArgsException() {
            super("You must enter a name and a message! Check your arguments!");
        }
    }
    private String compileMsg(String[] args, int beginningIndex) {
        StringBuilder compiledString = new StringBuilder();
        for (int i = beginningIndex; i < args.length; i++) {
            compiledString.append(" " + args[i]);
        }
        return compiledString.toString().trim();
    }
}