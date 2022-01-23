package me.yarinlevi.minigameframework.game;

import lombok.Getter;
import me.yarinlevi.minigameframework.MinigameFramework;
import org.bukkit.entity.Player;

/**
 * @author YarinQuapi
 **/
public class PlayerGameStat {
    protected Player player;

    protected PlayerGameStat(Player player) {
        this.player = player;
    }

    @Getter private int kills = 0;

    public void addKill() {
        kills++;
        MinigameFramework.getFramework().getStatistics().addKill(player);
    }
}
