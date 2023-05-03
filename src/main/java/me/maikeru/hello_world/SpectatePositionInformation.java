package me.maikeru.hello_world;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.UUID;

public class SpectatePositionInformation implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Location location;
    private final UUID targetUUID;
    private final GameMode previousGameMode;

    public SpectatePositionInformation(Player spectator, Player target) {
        location = spectator.getLocation();
        targetUUID = target.getUniqueId();
        previousGameMode = spectator.getGameMode();
    }
    public Location getLocation() {
        return location;
    }
    public UUID getTargetUUID() {
        return targetUUID;
    }
    public GameMode getPreviousGameMode() {
        return previousGameMode;
    }
}
