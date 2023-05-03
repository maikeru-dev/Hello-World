package me.maikeru.hello_world;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandVoteSkip implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return false;

        Player p = (Player) sender;

        try{
            int onlinePlayersCount = Bukkit.getOnlinePlayers().size();

            if (p.getWorld().isDayTime()) throw new invalidTimeException();
            if (HelloWorld.VoteSystem.checkVoteExists(p.getUniqueId())) throw new invalidVoteAdd();
            if (HelloWorld.getCurrentPlayerSleepingCount() < (onlinePlayersCount/2)) throw new invalidSleepingCount();


            HelloWorld.VoteSystem.addVoteTotal(p.getUniqueId());
            int votesCount = HelloWorld.VoteSystem.getVoteCount();

            if (votesCount == onlinePlayersCount){
                p.getWorld().setTime(1000);
            }else {
                TextComponent text = Component.text()
                        .content( votesCount + "/" + onlinePlayersCount + " have voted to skip the night! " + (onlinePlayersCount-votesCount) + " more are required to vote!")
                        .color(NamedTextColor.GRAY)
                        .build();
                p.sendMessage(text);
            }


        }catch (CustomException e) {
            p.sendMessage(e.getMessage());
        }
        return true;
    }
    private class invalidTimeException extends CustomException {
        public invalidTimeException() {
            super(ChatColor.RED + "It's daytime! You can't vote yet!");
        }
    }
    private class invalidVoteAdd extends CustomException {
        public invalidVoteAdd() {
            super(ChatColor.RED + "You already voted! Can't do it again ;P");
        }
    }
    private class invalidSleepingCount extends CustomException {
        public invalidSleepingCount() {
            super(ChatColor.RED + "There's noone sleeping! Atleast players " + Bukkit.getOnlinePlayers().size()/2 + " need to be sleeping!");
        }
    }
}
