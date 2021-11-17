package me.yarinlevi.minigameframework.game;

import lombok.Getter;
import me.yarinlevi.minigameframework.MinigameFramework;
import me.yarinlevi.minigameframework.arena.Arena;
import me.yarinlevi.minigameframework.exceptions.ArenaNotExistException;
import me.yarinlevi.minigameframework.exceptions.NoArenaAvailable;
import me.yarinlevi.minigameframework.exceptions.PlayerNotInGameException;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public Game createGame(String arenaGame) throws ArenaNotExistException, NoArenaAvailable {
        Arena arena = MinigameFramework.getFramework().getArenaManager().getArena(arenaGame);

        if (!arena.isAssigned()) {
            Game game = new Game(arena);

            arena.setAssigned(true);

            availableGames.add(game);
            return game;
        } else throw new NoArenaAvailable();
    }

    /**
     * Create a new game
     * @param arena The arena
     * @return The game instance
     */
    public Game createGame(Arena arena) throws NoArenaAvailable {
        if (arena.isAssigned()) throw new NoArenaAvailable();

        Game game = new Game(arena);

        arena.setAssigned(true);

        availableGames.add(game);
        return game;
    }

    /**
     * Gets the first available game
     * @return the game
     * @throws NoArenaAvailable If there are no games and no arenas to open games on.
     */
    public Game getAvailable() throws NoArenaAvailable {
        return availableGames.stream().filter(game -> !game.isStarted()).findFirst().orElse(this.createGame(MinigameFramework.getFramework().getArenaManager().getAvailable()));
    }

    /**
     * Gets all the games that have not started and that don't contain max players
     * @return A list of all 'empty' games
     */
    public List<Game> getAllEmptyGames() {
        return this.availableGames.stream().filter(game -> !game.isStarted()).filter(game -> !game.isFull()).collect(Collectors.toList());
    }

    /**
     * Gets the game the player is present in
     * @throws PlayerNotInGameException If the player was not found in any game
     */
    public Game getPlayerGame(Player player) throws PlayerNotInGameException {
        return this.availableGames.stream().filter(game -> game.isInGame(player)).findFirst().orElseThrow(PlayerNotInGameException::new);
    }
}
