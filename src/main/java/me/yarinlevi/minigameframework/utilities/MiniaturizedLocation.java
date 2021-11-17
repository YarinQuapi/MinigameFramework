package me.yarinlevi.minigameframework.utilities;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

public class MiniaturizedLocation {

    @Getter private int x,y,z;
    @Getter private float yaw,pitch;
    @Getter private String worldName;

    protected MiniaturizedLocation(int x1, int y1, int z1, float yaw1, float pitch1, String worldName) {
        this.x = x1;
        this.y = y1;
        this.z = z1;
        this.yaw = yaw1;

        this.pitch = pitch1;
        this.worldName = worldName;
    }

    public static MiniaturizedLocation construct(Location location) {
        return new MiniaturizedLocation(location.getBlockX(), location.getBlockY(), location.getBlockZ(), location.getYaw(), location.getPitch(), location.getWorld().getName());
    }

    public void teleport(Player player) {
        player.teleport(this.toLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

    public Location toLocation() {
        return new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
    }

    public String prettyPrint() {
        return "X: " + x + " Y: " + y + " Z: " + z + " Yaw: " + yaw + " Pitch: " + pitch + " World: " + worldName;
    }
}
