package me.maikeru.hello_world;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public class CommandPM implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] strings) {
        /*  Outline:
            PM system must:
                Send messages to Players and from Players
                Use TextComponents for DisplayNames
                Have better readability
                Better Input validation

        * */

        if (!(commandSender instanceof Player)) return false;

        final Player sender = (Player) commandSender;
        String msgContent;
        Player receiver;
        TextComponent msg;

        try {
            msgContent = validateCommandContent(strings);
            receiver = Bukkit.getPlayer(strings[0]);
            msg = Component.text()
                    .append(sender.displayName())
                    .append(Component.text().content(" -> ").color(TextColor.color(128,128,128)))
                    .append(receiver.displayName())
                    .append(Component.text(" : " + msgContent).color(TextColor.color(128,128,128)))
                    .build();

            receiver.sendMessage(msg);
            sender.sendMessage(msg);
            return true;
        }
        catch(MissingNameException e) {commandSender.sendMessage(e.getMessage());}
        catch(MissingMsgException e) {commandSender.sendMessage(e.getMessage());}
        catch(InvalidPlayerNameException e) {commandSender.sendMessage(e.getMessage());}
        catch(Exception e) {Bukkit.getLogger().log(Level.SEVERE, "Error! Could not finish PM command: " + e.getMessage());}
        finally {
            return false;
        }


    }
    public String validateCommandContent(String[] args)
            throws MissingMsgException, MissingNameException, InvalidPlayerNameException {

        if (args.length == 0) throw new MissingNameException();
        if (args.length == 1) throw new MissingMsgException();
        if (Bukkit.getPlayer(args[0]) == null) throw new InvalidPlayerNameException();

        StringBuilder compiledString = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            compiledString.append(" " + args[i]);
        }
        return compiledString.toString();

    }
}



class NonTargetException extends Exception {
    // This exception should activate whenever the commandSender or receiver
    // an unintended type. i.e., non-player;
    public NonTargetException() {
        super(ChatColor.RED + "Target is non-player. ??");
    }
}
class MissingNameException extends Exception {
    public MissingNameException() {
        super(ChatColor.RED + "Who are you sending your message to? Usage: /pm (name) (message...)");
    }

}
class MissingMsgException extends Exception {
    public MissingMsgException() {
        super(ChatColor.RED + "What is your message? Usage: /pm (name) (message...)");
    }
}
class InvalidPlayerNameException extends Exception {

    public InvalidPlayerNameException() {
        super(ChatColor.RED + "That player isn't online right now, check your spelling? Usage: /pm (name) (message...)");
    }

}
