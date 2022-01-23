package me.yarinlevi.minigameframework.game;

import me.yarinlevi.minigameframework.MinigameFramework;
import me.yarinlevi.minigameframework.game.events.QGameStartEvent;
import me.yarinlevi.minigameframework.game.events.QPlayerDeathEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * @author YarinQuapi
 **/
public class GameListener implements Listener {
    private final Game game;

    public GameListener(Game game) {
        this.game = game;

        QGameStartEvent gameStartEvent = new QGameStartEvent(game);
        MinigameFramework.getInstance().getServer().getPluginManager().callEvent(gameStartEvent);
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player && game.isInGame(player) && !game.isStarted()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (game.isInGame(event.getPlayer()) && !game.isStarted()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event) {
        Player victim = event.getEntity();

        if (victim.getKiller() != null && game.isInGame(victim)) {
            QPlayerDeathEvent playerKillEvent = new QPlayerDeathEvent(game, victim);

            playerKillEvent.setKiller(victim.getKiller());

            MinigameFramework.getInstance().getServer().getPluginManager().callEvent(playerKillEvent);

            MinigameFramework.getFramework().getStatistics().addDeath(victim);
        } else if (victim.getKiller() == null && game.isInGame(victim)) {
            QPlayerDeathEvent playerKillEvent = new QPlayerDeathEvent(game, victim);

            MinigameFramework.getInstance().getServer().getPluginManager().callEvent(playerKillEvent);

            MinigameFramework.getFramework().getStatistics().addDeath(victim);
        }
    }
}
