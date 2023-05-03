package me.maikeru.hello_world;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.persistence.PersistentDataContainer;

public class ListenerSpectate implements Listener {
    @EventHandler
    public void VehicleExitEvent(VehicleExitEvent e) {
        if (!(e.getExited() instanceof Player)) return;
        if (!(e.getVehicle() instanceof Player)) return;
        if (!(((Player) e.getExited()).getGameMode() == GameMode.SPECTATOR)) return;
        Player p = (Player) e.getExited();

        PersistentDataContainer container = p.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(HelloWorld.getPlugin(), "SPECTATE_POS");
        SpectatePositionInformation information = container.get(key, new SpectatePositionDataType());
        if (information == null) return;
        if (information.getTargetUUID() != e.getVehicle().getUniqueId()) return;

        p.teleport(information.getLocation());
        p.setGameMode(p.getPreviousGameMode());
        container.remove(key);

    }
}
