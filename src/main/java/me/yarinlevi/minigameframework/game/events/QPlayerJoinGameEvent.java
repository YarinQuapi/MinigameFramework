package me.yarinlevi.minigameframework.game.events;

import lombok.Getter;
import me.yarinlevi.minigameframework.game.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author YarinQuapi
 **/
public class QPlayerJoinGameEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    @Getter private final Game game;
    @Getter private final Player player;
    @Getter private me.yarinlevi.minigameframework.game.events.Result result;

    public QPlayerJoinGameEvent(Game game, Player player) {
        this.game = game;
        this.player = player;
    }

    public me.yarinlevi.minigameframework.game.events.Result setDenied() {
        this.result = me.yarinlevi.minigameframework.game.events.Result.denied;
        return this.result;
    }

    public me.yarinlevi.minigameframework.game.events.Result setAllowed() {
        this.result = me.yarinlevi.minigameframework.game.events.Result.allowed;
        return this.result;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}