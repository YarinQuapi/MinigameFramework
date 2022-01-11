package me.yarinlevi.minigameframework.arena;

import lombok.Getter;
import lombok.Setter;
import me.yarinlevi.minigameframework.MinigameFramework;
import me.yarinlevi.minigameframework.utilities.MiniaturizedLocation;
import me.yarinlevi.minigameframework.worldedit.WorldEditUtils;
import org.bukkit.Location;

import java.io.File;
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
    @Getter @Setter private boolean assigned = false;
    @Getter @Setter private MiniaturizedLocation pasteLocation;
    @Getter private List<MiniaturizedLocation> locations = new ArrayList<>();

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

    public void resetArena() {
        String arenaName = this.getArenaName();

        File file = new File(MinigameFramework.getInstance().getDataFolder() + "/schematics/", arenaName + ".schem");

        WorldEditUtils.paste(pasteLocation.toLocation().getWorld(), WorldEditUtils.load(file), pasteLocation.toLocation().toVector());
    }
}
