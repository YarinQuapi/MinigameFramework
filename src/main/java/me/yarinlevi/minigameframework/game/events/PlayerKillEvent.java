package me.yarinlevi.minigameframework.game.events;

import lombok.Getter;
import me.yarinlevi.minigameframework.game.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author YarinQuapi
 **/
public class PlayerKillEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    @Getter private final Game game;
    @Getter private final Player killer,victim;

    public PlayerKillEvent(Game game, Player killer, Player victim) {
        this.game = game;
        this.killer = killer;
        this.victim = victim;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
