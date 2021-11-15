package me.yarinlevi.minigameframework;

import lombok.Getter;
import me.yarinlevi.minigameframework.arena.ArenaManager;
import me.yarinlevi.minigameframework.commands.ArenaCommand;
import me.yarinlevi.minigameframework.game.GameManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinigameFramework {
    @Getter private static JavaPlugin instance;
    @Getter private static MinigameFramework framework;
    @Getter private static ArenaManager arenaManager;
    @Getter private static GameManager gameManager;

    public MinigameFramework create(JavaPlugin javaPlugin) {
        framework = this;
        instance = javaPlugin;

        javaPlugin.saveDefaultConfig();

        arenaManager = new ArenaManager();
        arenaManager.loadArenas();

        gameManager = new GameManager();

        javaPlugin.getCommand("arena").setExecutor(new ArenaCommand());

        return this;
    }
}
