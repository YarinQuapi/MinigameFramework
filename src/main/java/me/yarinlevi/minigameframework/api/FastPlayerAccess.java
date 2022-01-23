package me.yarinlevi.minigameframework.api;

import me.yarinlevi.minigameframework.MinigameFramework;
import me.yarinlevi.minigameframework.exceptions.PlayerNotInGameException;
import me.yarinlevi.minigameframework.game.Game;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author YarinQuapi
 **/
public class FastPlayerAccess {

    /**
     * Returns the amount of kills the player has in his current game
     * @param player The player
     * @return the amount of kills
     * @throws PlayerNotInGameException if the player is not in a game currently.
     */
    public static int getPlayerGameKills(@NotNull Player player) throws PlayerNotInGameException {
        Game game = MinigameFramework.getFramework().getGameManager().getPlayerGame(player);

        if (game == null) throw new PlayerNotInGameException();

        return game.getGamePlayers().get(player).getKills();
    }
}
