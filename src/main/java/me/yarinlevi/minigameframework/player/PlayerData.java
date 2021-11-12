package me.yarinlevi.minigameframework.player;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerData {
    @Getter private final UUID playerUUID;

    @Getter private int kills = 0;
    @Getter private int wins = 0;
    @Getter @Setter private float coinMultiplier = 1.0f;

    public PlayerData(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(playerUUID);
    }
}
