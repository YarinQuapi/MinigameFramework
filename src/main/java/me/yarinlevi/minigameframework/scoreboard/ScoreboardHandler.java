package me.yarinlevi.minigameframework.scoreboard;

import lombok.Getter;
import me.yarinlevi.minigameframework.MinigameFramework;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

/**
 * @author YarinQuapi
 **/
public class ScoreboardHandler implements Listener {
    @Getter private static ScoreboardHandler scoreboardHandler;

    private final GameScoreboard gameScoreboard;
    private final LobbyScoreboard lobbyScoreboard;

    public ScoreboardHandler(MinigameFramework framework) {
        scoreboardHandler = this;

        gameScoreboard = new GameScoreboard();
        lobbyScoreboard = new LobbyScoreboard();
    }

    public void updateScoreboard(Player player, ScoreboardType type) {
        switch (type) {
            case Game -> gameScoreboard.setScoreboard(player);
            case Lobby -> lobbyScoreboard.setScoreboard(player);
        }
    }

    enum ScoreboardType {
        Lobby,
        Game
    }
}
