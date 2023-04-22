package me.maikeru.hello_world;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.UUID;

import static org.bukkit.Bukkit.getEntity;

public class CommandKillText implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        // Check for validation
        //  validation: must be a valid name, must have more than one argument,
        if (!(commandSender instanceof Player)) return false;

        try {
            flatFileAccessor accessor = new flatFileAccessor();
            if (args.length < 1) throw new invalidArgsException();

            UUID armorStandUUID = accessor.getValue(args[0]);
            ArmorStand armorStand = (ArmorStand) Bukkit.getEntity(armorStandUUID);
            armorStand.setHealth(0);
            if (accessor.deleteEntry(args[0], armorStandUUID)) {
                commandSender.sendMessage(
                        Component.text()
                                .content("Successfully deleted hologram: " + args[0])
                                .color(TextColor.fromHexString("#50C878")) // Emerald green
                                .build()
                );
            }else {
                commandSender.sendMessage(
                        Component.text()
                                .content("Failed to delete hologram: " + args[0])
                                .color(TextColor.color(255, 0, 0))
                                .build()
                );
            }

        } catch(CustomException e) {
            commandSender.sendMessage(
                    Component.text()
                            .content((e.getMessage()))
                            .color(TextColor.color(255, 0 , 0))
                            .build());
        }
        return true;
    }
    private class invalidArgsException extends CustomException {
        public invalidArgsException() {
            super("You can't kill nothing! Pass a name.");
        }
    }
}
