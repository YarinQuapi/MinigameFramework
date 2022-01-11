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
    public void onPlayerKill(PlayerDeathEvent event) {
        Player victim = event.getEntity();

        if (victim.getKiller() != null && game.isInGame(victim)) {
            QPlayerDeathEvent playerKillEvent = new QPlayerDeathEvent(game, victim);

            playerKillEvent.setKiller(victim.getKiller());

            MinigameFramework.getInstance().getServer().getPluginManager().callEvent(playerKillEvent);

            game.lose(victim);

            if (game.getAlivePlayers().size() == 1) {
                game.win(game.getAlivePlayers().stream().findFirst().orElseThrow());
            }
        } else if (victim.getKiller() == null && game.isInGame(victim)) {

        }
    }
}
