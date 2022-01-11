package me.yarinlevi.minigameframework.game.events;

import lombok.Getter;
import lombok.Setter;
import me.yarinlevi.minigameframework.game.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author YarinQuapi
 **/
public class QPlayerDeathEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    @Getter private final Game game;
    @Getter private final Player victim;
    @Getter @Setter private Player killer;

    public QPlayerDeathEvent(Game game, Player victim) {
        this.game = game;
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
