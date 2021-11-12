package me.yarinlevi.minigameframework.utilities;

import lombok.Getter;
import me.yarinlevi.minigameframework.MinigameFramework;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author YarinQuapi
 **/
public class Settings {

    static {
        FileConfiguration settings = MinigameFramework.getInstance().getConfig();

        GAME_START_TIMER = settings.getInt("game.start_timer");
        GAME_MIN_PERCENTAGE = settings.getInt("game.start_percentage");
    }

    public static final int GAME_START_TIMER;
    public static final int GAME_MIN_PERCENTAGE;
}
