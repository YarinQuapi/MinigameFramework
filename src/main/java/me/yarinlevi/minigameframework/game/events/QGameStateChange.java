package me.yarinlevi.minigameframework.game.events;

import lombok.Getter;
import me.yarinlevi.minigameframework.game.Game;
import me.yarinlevi.minigameframework.game.GameState;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * @author YarinQuapi
 **/
public class QGameStateChange extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    @Getter private final Game game;
    @Getter private final GameState state;

    public QGameStateChange(Game game) {
        this.game = game;
        this.state = game.getGameState();
    }

    @NotNull @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}
