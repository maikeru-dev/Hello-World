package me.maikeru.hello_world;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public class CommandPM implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        /*  Outline:
            PM system must:
                Send messages to Players and from Players
                Use TextComponents for DisplayNames
                Have better readability
                Better Input validation

        * */

        if (!(sender instanceof Player)) return false;

        final Player p = (Player) sender;
        String msgContent;
        Player receiver;
        TextComponent msg;

        try {
            if (args.length < 2) throw new CustomException.invalidArgsException(2 - args.length);
            if (Bukkit.getPlayer(args[0]) == null) throw new InvalidPlayerNameException();

            msgContent = validateCommandContent(args);
            receiver = Bukkit.getPlayer(args[0]);
            msg = Component.text()
                    .append(p.displayName())
                    .append(Component.text().content(" -> ").color(TextColor.color(128,128,128)))
                    .append(receiver.displayName())
                    .append(Component.text(" : " + msgContent).color(TextColor.color(128,128,128)))
                    .build();

            receiver.sendMessage(msg);
            p.sendMessage(msg);

            HelloWorld.updateReplyMap(p.getUniqueId(), receiver.getUniqueId());
            return true;
        }
        catch(CustomException e) {
            sender.sendMessage(e.getComponentMessage());
            return false;
        }
        catch(Exception e) {Bukkit.getLogger().log(Level.SEVERE, "Error! Could not finish PM command: " + e.getMessage());}
        return false;


    }
    public String validateCommandContent(String[] args){

        StringBuilder compiledString = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            compiledString.append(" ").append(args[i]);
        }
        return compiledString.toString();

    }
    private class InvalidPlayerNameException extends CustomException {
        public InvalidPlayerNameException() {
            super(ChatColor.RED + "That player isn't online right now, check your spelling? Usage: /pm (name) (message...)");
        }

    }

}

