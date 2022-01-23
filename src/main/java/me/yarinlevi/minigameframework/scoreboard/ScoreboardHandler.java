package me.yarinlevi.minigameframework.scoreboard;

import lombok.Getter;
import me.yarinlevi.minigameframework.MinigameFramework;
import me.yarinlevi.minigameframework.exceptions.PlayerNotInGameException;
import me.yarinlevi.minigameframework.game.Game;
import me.yarinlevi.minigameframework.game.GameState;
import me.yarinlevi.minigameframework.utilities.MessagesUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author YarinQuapi
 **/
public class ScoreboardHandler implements Listener {
    @Getter private static ScoreboardHandler scoreboardHandler;
    @Getter private final Map<String, List<String>> scoreboards = new HashMap<>();

    @Getter private final Map<Game, PlayerScoreboard> cache = new HashMap<>();

    public ScoreboardHandler(MinigameFramework framework) {
        scoreboardHandler = this;
    }

    public void insertScoreboard(String key, List<String> lines) {
        if (scoreboards.containsKey(key)) {
            scoreboards.replace(key, lines);
        } else {
            scoreboards.put(key, lines);
        }
    }

    public void createScoreboard(Game game) {
        switch (game.getGameState()) {
            case RUNNING -> {
                this.cache.put(
                        game,
                        new PlayerScoreboard(game.getGameName(), MessagesUtils.getLines("scoreboard.running-game"))
                );
            }

            case STARTING -> {
                this.cache.put(
                        game,
                        new PlayerScoreboard(game.getGameName(), MessagesUtils.getLines("scoreboard.starting-game"))
                );
            }

            case UNINITIALIZED -> {
                this.cache.put(
                        game,
                        new PlayerScoreboard(game.getGameName(), MessagesUtils.getLines("scoreboard.waiting-game"))
                );
            }
        }
    }

    public void updateScoreboard(Player player) {
        Game game = MinigameFramework.getFramework().getGameManager().getPlayerGame(player);

        if (game != null) {
            cache.get(game).setScoreboard(player);
        }
    }
}
