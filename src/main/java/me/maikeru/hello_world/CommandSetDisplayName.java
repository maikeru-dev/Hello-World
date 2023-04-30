package me.maikeru.hello_world;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandSetDisplayName implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;

        try {
            if (args.length < 1) throw new invalidArgsException();

            Player p = (Player) sender;


        }
        catch(CustomException e) {
            TextComponent text = Component.text()
                    .content(e.getMessage())
                    .color(TextColor.color(255, 0, 0))
                    .build();
            sender.sendMessage(text);
        }

        return false;
    }
    private class invalidArgsException extends CustomException {
        public invalidArgsException() {
            super("Invalid arguments size! Are you entering a display name?");
        }
    }
}
