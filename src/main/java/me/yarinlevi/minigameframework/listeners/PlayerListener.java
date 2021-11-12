package me.yarinlevi.minigameframework.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    /**
     * Listens for joining players to add them to data
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

    }

    /**
     * Listens for quitting players to remove them from memory
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

    }
}
