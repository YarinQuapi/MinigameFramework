package me.yarinlevi.minigameframework.arena;

import lombok.Getter;
import lombok.Setter;
import me.yarinlevi.minigameframework.utilities.MiniaturizedLocation;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * This Arena class supports single death games only!
 * Meaning games with respawning abilities will not work.
 */
public class Arena {
    @Getter private final List<Player> arenaPlayers = new ArrayList<>();
    @Getter @Setter private int maxPlayers;
    @Getter @Setter private String worldName;
    @Getter @Setter private String arenaName;
    @Getter private final List<MiniaturizedLocation> locations = new ArrayList<>();

    protected Arena(String arenaName, String world, int maxPlayers) {
        this.arenaName = arenaName;
        this.maxPlayers = maxPlayers;
        this.worldName = world;
    }

    public void addLocation(Location location) {
        this.locations.add(MiniaturizedLocation.construct(location));
    }

    public void removeLocation(int index) {
        this.locations.remove(index);
    }

    public boolean isFull() {
        return (maxPlayers / arenaPlayers.size()) == 1;
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
