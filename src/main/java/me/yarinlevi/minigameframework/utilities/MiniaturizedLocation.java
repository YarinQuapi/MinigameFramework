package me.yarinlevi.minigameframework.utilities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

public record MiniaturizedLocation(int x, int y, int z, float yaw, float pitch, String worldName) {
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
