package me.maikeru.hello_world;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


import java.util.UUID;



public class CommandKillText implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        // Check for validation
        //  validation: must be a valid name
        if (!(sender instanceof Player)) return false;

        try {
            flatFileAccessor accessor = new flatFileAccessor();
            if (args.length < 1) throw new CustomException.invalidArgsException(1);

            UUID armorStandUUID = accessor.getValue(args[0]);
            ArmorStand armorStand = (ArmorStand) Bukkit.getEntity(armorStandUUID);
            armorStand.setHealth(0);

            if (accessor.deleteEntry(args[0], armorStandUUID)) {
                sender.sendMessage(Component.text("Successfully deleted hologram: " + args[0], NamedTextColor.GREEN));
            }else {
                sender.sendMessage(Component.text("Failed to delete hologram: " + args[0], NamedTextColor.RED));
            }

        } catch(CustomException e) {
            sender.sendMessage(e.getComponentMessage());
            return false;
        }
        return true;
    }
}
