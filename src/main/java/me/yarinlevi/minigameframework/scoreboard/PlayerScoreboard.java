package me.yarinlevi.minigameframework.scoreboard;

import me.yarinlevi.minigameframework.MinigameFramework;
import me.yarinlevi.minigameframework.game.Game;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author YarinQuapi
 **/
public class PlayerScoreboard {
    private final Map<Integer, String> scoreTextMap = new HashMap<>();
    private final String displayName;

    protected PlayerScoreboard(String displayName, List<String> lines) {
        this.displayName = displayName;

        lines.forEach(x -> scoreTextMap.put(lines.indexOf(x), x));
    }

    protected void setScoreboard(Player player) {
        ScoreboardManager sc = Bukkit.getScoreboardManager();

        Scoreboard board = sc.getNewScoreboard();
        Objective objective = board
                .registerNewObjective("minigame-framework-scoreboard", "dummy", displayName);

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        scoreTextMap.forEach((score, text) -> {
            Score scoreText = objective.getScore(text);
            scoreText.setScore(score);
        });

        player.setScoreboard(board);
    }
}
