package me.maikeru.hello_world;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.TimeSkipEvent;


public class ListenerSleep implements Listener {
    @EventHandler
    public void PlayerBedEnterEvent(PlayerBedEnterEvent e) {

        if (e.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.OK) {
            if (e.getPlayer().getWorld().getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE) == Boolean.TRUE) {
            TextComponent text = Component.text()
                    .content(HelloWorld.updateCurrentPlayerSleepingCount(1) + "/" + Bukkit.getOnlinePlayers().size() + " are currently sleeping! Get into bed!")
                    .color(TextColor.color(190, 190, 190))
                    .build();
            Bukkit.broadcast(text);

            }
        }
    }
    @EventHandler
    public void PlayerBedLeaveEvent(PlayerBedLeaveEvent e) {
        if (HelloWorld.getCurrentPlayerSleepingCount() != 0) { // getting daytime is too slow
            if (e.getPlayer().getWorld().getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE) == Boolean.TRUE) {
                TextComponent text = Component.text()
                        .content(HelloWorld.updateCurrentPlayerSleepingCount(-1) + "/" + Bukkit.getOnlinePlayers().size() + " are currently sleeping! :(")
                        .color(TextColor.color(190, 190, 190))
                        .build();

                Bukkit.broadcast(text);
            }
        }
    }
    @EventHandler
    public void TimeSkipEvent(TimeSkipEvent e) {
        HelloWorld.resetCurrentPlayerSleepingCount();
        Bukkit.getLogger().info("TimeSkip works!");
        if (e.getWorld().getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE) == Boolean.TRUE) {
            TextComponent text = Component.text()
                    .content("It's daytime! Yay :)")
                    .color(TextColor.color(144, 238, 144))
                    .build();
            Bukkit.broadcast(text);
        }
    }
    @EventHandler
    public void PlayerQuitEvent(PlayerQuitEvent e) {
        HelloWorld.VoteSystem.removeVote(e.getPlayer().getUniqueId());
    }

}

