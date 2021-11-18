package me.yarinlevi.minigameframework.game;

import me.yarinlevi.minigameframework.MinigameFramework;
import me.yarinlevi.minigameframework.game.events.GameStartEvent;
import me.yarinlevi.minigameframework.game.events.PlayerDeathEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * @author YarinQuapi
 **/
public class GameListener implements Listener {
    private final Game game;

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
            if (victim.getKiller() != null && game.isInGame(victim)) {
                PlayerDeathEvent playerKillEvent = new PlayerDeathEvent(game, victim);

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
}
