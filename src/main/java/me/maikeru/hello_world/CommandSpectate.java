package me.maikeru.hello_world;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class CommandSpectate implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;

        Player p = (Player) sender;
        try {
            if (args.length < 1) throw new CustomException.invalidArgsException(1);

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) throw new CommandSpectate.invalidNameException();

            PersistentDataContainer playerData = p.getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(HelloWorld.getPlugin(), "SPECTATE_POS");
            SpectatePositionInformation information = new SpectatePositionInformation(p, target);
            playerData.set(key, new SpectatePositionDataType(), information); // need to create a new data type for this to work, preferable.

            p.setGameMode(GameMode.SPECTATOR);
            p.setSpectatorTarget(target);

        } catch (CustomException e) {
            p.sendMessage(e.getComponentMessage());
            return false;
        }

        return true;
    }
    private static class invalidNameException extends CustomException {
        public invalidNameException() {
            super("This player doesn't exist! Check your spelling.");
        }
    }
}
