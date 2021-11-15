package me.yarinlevi.minigameframework;

import lombok.Getter;
import me.yarinlevi.minigameframework.administration.ServerSpawn;
import me.yarinlevi.minigameframework.arena.ArenaManager;
import me.yarinlevi.minigameframework.commands.AdminCommand;
import me.yarinlevi.minigameframework.commands.ArenaCommand;
import me.yarinlevi.minigameframework.commands.PlayerCommand;
import me.yarinlevi.minigameframework.game.GameManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinigameFramework {
    @Getter private static JavaPlugin instance;
    @Getter private static MinigameFramework framework;
    @Getter private static ArenaManager arenaManager;
    @Getter private static GameManager gameManager;
    @Getter private static ServerSpawn serverSpawn;

    public void initialize(JavaPlugin javaPlugin) {
        framework = this;
        instance = javaPlugin;

        javaPlugin.saveDefaultConfig();

        serverSpawn = new ServerSpawn();
        serverSpawn.loadServerSpawn();

        arenaManager = new ArenaManager();
        arenaManager.loadArenas();

        gameManager = new GameManager();

        javaPlugin.getCommand("arena").setExecutor(new ArenaCommand());
        javaPlugin.getCommand("game").setExecutor(new PlayerCommand());
        javaPlugin.getCommand("admin").setExecutor(new AdminCommand());
    }
}
