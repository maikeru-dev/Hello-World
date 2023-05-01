package me.maikeru.hello_world;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ListenerJoin implements Listener {

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent e) {

        Player p = e.getPlayer();
        PersistentDataContainer playerData = p.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(HelloWorld.getPlugin(), "nickname");

        String nickname = playerData.get(key, PersistentDataType.STRING);
        if (nickname != null) {
            Component joinMsg = Component.text()
                    .content(" joined the game")
                    .color(NamedTextColor.YELLOW)
                    .build();
            MiniMessage miniMessage = MiniMessage.miniMessage();
            Component dpName = miniMessage.deserialize(nickname);

            p.displayName(dpName);
            e.joinMessage(dpName.append(joinMsg));
        }
    }
}
