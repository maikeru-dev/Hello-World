package me.maikeru.hello_world;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


import java.util.UUID;



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
                commandSender.sendMessage(ChatColor.GREEN + "Successfully deleted hologram: " + args[0]);
            }else {
                commandSender.sendMessage(ChatColor.RED + "Failed to delete hologram: " + args[0]);
            }

        } catch(CustomException e) {
            commandSender.sendMessage(ChatColor.RED + e.getMessage());
        }
        return true;
    }
    private class invalidArgsException extends CustomException {
        public invalidArgsException() {
            super("You can't kill nothing! Pass a name.");
        }
    }
}
