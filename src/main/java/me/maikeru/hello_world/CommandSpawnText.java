package me.maikeru.hello_world;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;



// Get JSON file responsible for holding { UUID : given_name }
// Create armor stand
// Pull armor stand into JSON file
// Finish

// EDIT: There's a new update in 1.19.4 with Display Entities


public class CommandSpawnText implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        /* Invisible Armor stands with TextComponent names */
        if (!(sender instanceof Player)) return false;

        Player player = ((Player) sender);

        TextComponent standName = Component.text()
                .content(compileMsg(args, 1))
                .color(TextColor.color(255, 0, 0))
                .build();

        ArmorStand armorStand = player.getWorld().spawn(player.getLocation(), ArmorStand.class);
        try {
            flatFileAccessor accessor = new flatFileAccessor();

            if (args.length < 2) throw new CustomException.invalidArgsException(2 - args.length);
            if (accessor.getValue(args[0]) != null) throw new invalidNameException();

            armorStand.setGravity(false);
            armorStand.setCustomNameVisible(true);
            armorStand.setCollidable(false);
            armorStand.customName(standName);
            armorStand.setInvisible(false);
            armorStand.setInvulnerable(true);


            accessor.appendEntry(player.getUniqueId(), args[0], armorStand.getUniqueId());
        } catch (IOException e) {
            e.printStackTrace();
            armorStand.setHealth(0);
            return false;
        } catch (CustomException e) {
            sender.sendMessage(
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
    private class invalidNameException extends CustomException {
        public invalidNameException() {
            super("You cannot enter tag_name for a hologram that exists already!");
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