package me.yarinlevi.minigameframework.arena;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import lombok.Getter;
import lombok.Setter;
import me.yarinlevi.minigameframework.MinigameFramework;
import me.yarinlevi.minigameframework.utilities.MiniaturizedLocation;
import me.yarinlevi.minigameframework.worldedit.WorldEditUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;

/**
 * @author YarinQuapi
 **/
public class ArenaEdit {
    @Getter private final Arena arena;
    @Getter @Setter private Vector pos1;
    @Getter @Setter private Vector pos2;
    @Getter @Setter private World world;

    public ArenaEdit(Arena arena) {
        this.arena = arena;
        this.world = Bukkit.getWorld(arena.getWorldName());
    }

    public void save(Location location) {
        this.arena.setPasteLocation(MiniaturizedLocation.construct(location));

        String arenaName = arena.getArenaName();

        File file = new File(MinigameFramework.getInstance().getDataFolder() + "/schematics/", arenaName + ".schem");

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        Clipboard clipboard = WorldEditUtils.copy(world, pos1, pos2, location.toVector());

        WorldEditUtils.save(file, clipboard);

        try {
            MinigameFramework.getFramework().getArenaManager().saveArena(arena);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
