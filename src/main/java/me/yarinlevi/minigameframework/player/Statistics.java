package me.yarinlevi.minigameframework.player;

import org.bukkit.entity.Player;

/**
 * @author YarinQuapi
 * An interface which is meant to be shared by statistics-managing classes
 **/
public interface Statistics {
    void addKill(Player player);
    int getKills(Player player);

    void addDeath(Player player);
    int getDeaths(Player player);

    void setMultiplier(Player player, float multiplier);
    float getMultiplier(Player player);

    void addWin(Player player);
    int getWins(Player player);

    void addGamePlayed(Player player);
    int getGamesPlayed(Player player);
}
