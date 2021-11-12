package me.yarinlevi.minigameframework;

import lombok.Getter;
import me.yarinlevi.minigameframework.arena.ArenaManager;
import me.yarinlevi.minigameframework.commands.ArenaCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinigameFramework extends JavaPlugin {
    @Getter private static MinigameFramework instance;
    @Getter private static ArenaManager arenaManager;

    @Override
    public void onEnable() {
        instance = this;
        arenaManager = new ArenaManager(this);

        getCommand("arena").setExecutor(new ArenaCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
