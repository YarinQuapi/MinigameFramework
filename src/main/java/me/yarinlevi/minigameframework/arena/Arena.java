package me.yarinlevi.minigameframework.arena;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class Arena {
    @Getter private final Set<Player> arenaPlayers = new HashSet<>();
    @Getter @Setter private int maxPlayers;
    @Getter @Setter private String worldName;

    public Arena(String world, int maxPlayers) {
        this.maxPlayers = maxPlayers;
        this.worldName = world;
    }

    public boolean addPlayer(Player player) {
        return arenaPlayers.add(player);
    }

    public boolean removePlayer(Player player) {
        if (arenaPlayers.contains(player)) {
            arenaPlayers.remove(player);
            return true;
        } else return false;
    }
}
