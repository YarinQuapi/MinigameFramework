package me.yarinlevi.minigameframework.game;

import lombok.Getter;
import me.yarinlevi.minigameframework.MinigameFramework;
import me.yarinlevi.minigameframework.arena.Arena;
import me.yarinlevi.minigameframework.exceptions.ArenaNotExistException;
import me.yarinlevi.minigameframework.exceptions.PlayerNotInGameException;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author YarinQuapi
 **/
public class GameManager {

    @Getter private final List<Game> availableGames = new ArrayList<>();

    /**
     * Create a new game
     * @param arenaGame The name of the arena you want
     * @return The game instance
     * @throws ArenaNotExistException If arena wasn't loaded or doesn't exist
     */
    public Game createGame(String arenaGame) throws ArenaNotExistException {
        Arena arena = MinigameFramework.getArenaManager().getArena(arenaGame);

        Game game = new Game(arena);

        availableGames.add(game);
        return game;
    }

    /**
     * Gets the game the player is present in
     * @throws PlayerNotInGameException If the player was not found in any game
     */
    public Game getPlayerGame(Player player) throws PlayerNotInGameException {
        for (Game game : availableGames) {
            if (game.isInGame(player)) {
                return game;
            }
        }

        throw new PlayerNotInGameException();
    }
}
