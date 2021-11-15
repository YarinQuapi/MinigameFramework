package me.yarinlevi.minigameframework.arena;

import lombok.Getter;
import lombok.Setter;
import me.yarinlevi.minigameframework.utilities.MiniaturizedLocation;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * This Arena class supports single death games only!
 * Meaning games with respawning abilities will not work.
 */
public class Arena {
    @Getter @Setter private int maxPlayers;
    @Getter @Setter private String worldName;
    @Getter @Setter private String arenaName;
    @Getter @Setter private boolean running = false;
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
}
