package me.yarinlevi.minigameframework;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinigameFramework extends JavaPlugin {
    @Getter private static MinigameFramework instance;

    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
