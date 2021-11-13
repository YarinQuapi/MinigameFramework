package me.yarinlevi.minigameframework.game;

import me.yarinlevi.minigameframework.MinigameFramework;
import me.yarinlevi.minigameframework.game.events.GameStartEvent;
import me.yarinlevi.minigameframework.game.events.PlayerKillEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * @author YarinQuapi
 **/
public record GameListener(Game game) implements Listener {
    public GameListener(Game game) {
        this.game = game;

        GameStartEvent gameStartEvent = new GameStartEvent(game);
        MinigameFramework.getInstance().getServer().getPluginManager().callEvent(gameStartEvent);
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (!game.isStarted()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!game.isStarted()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerKill(EntityDeathEvent event) {
        if (event.getEntity() instanceof Player victim) {
            if (victim.getKiller() != null) {
                PlayerKillEvent playerKillEvent = new PlayerKillEvent(game, victim, victim.getKiller());
                MinigameFramework.getInstance().getServer().getPluginManager().callEvent(playerKillEvent);
            }
        }
    }
}
