package me.maikeru.hello_world;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class CommandSetDisplayName implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;

        Player p = (Player) sender;

        try {
            if (args.length < 1) throw new CustomException.invalidArgsException(1);

            String dpName = compileString(args);

            PersistentDataContainer playerData = p.getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(HelloWorld.getPlugin(), "nickname");
            playerData.set(key, PersistentDataType.STRING, dpName);

            MiniMessage miniMessage = MiniMessage.miniMessage();
            Component parsed = miniMessage.deserialize(dpName);
            p.displayName(parsed);
            p.sendMessage(Component.text().content("Successfully changed your display name to: ").color(NamedTextColor.GREEN).append(parsed));

        }
        catch(CustomException e) {
            TextComponent text = Component.text()
                    .content(e.getMessage())
                    .color(NamedTextColor.RED)
                    .build();
            sender.sendMessage(text);
        }

        return false;
    }
    private String compileString(String[] args) {

        StringBuilder compiledString = new StringBuilder();
        for (int i = 1; i < args.length-1; i++) {
            compiledString.append(args[i]).append(" ");
        }
        compiledString.append(args[args.length-1]);
        return compiledString.toString();

    }
    private class invalidArgsException extends CustomException {
        public invalidArgsException() {
            super("Invalid arguments size! Are you entering a display name?");
        }
    }
}
