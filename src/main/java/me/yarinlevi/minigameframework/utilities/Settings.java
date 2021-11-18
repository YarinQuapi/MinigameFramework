package me.yarinlevi.minigameframework.utilities;

import me.yarinlevi.minigameframework.MinigameFramework;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author YarinQuapi
 * A bunch of constants loaded at the server start
 **/
public class Settings {

    static {
        FileConfiguration settings = MinigameFramework.getInstance().getConfig();

        GAME_START_TIMER = settings.getInt("game.start_timer");
        GAME_MIN_PERCENTAGE = settings.getInt("game.start_percentage");
        KILL_COIN_GAIN = settings.getInt("player.kill_coin_gain");
    }

    public static final int GAME_START_TIMER;
    public static final int GAME_MIN_PERCENTAGE;
    public static final int KILL_COIN_GAIN;
}
