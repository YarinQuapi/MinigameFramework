package me.yarinlevi.minigameframework.game.events;

import lombok.Getter;
import me.yarinlevi.minigameframework.game.Game;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author YarinQuapi
 **/
public class QGameStartEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    @Getter private final Game game;

    public QGameStartEvent(Game game) {
        this.game = game;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
