package me.yarinlevi.minigameframework;

import lombok.Getter;
import lombok.Setter;
import me.yarinlevi.minigameframework.administration.ServerSpawn;
import me.yarinlevi.minigameframework.arena.ArenaManager;
import me.yarinlevi.minigameframework.commands.AdminCommand;
import me.yarinlevi.minigameframework.commands.ArenaCommand;
import me.yarinlevi.minigameframework.commands.PlayerCommand;
import me.yarinlevi.minigameframework.exceptions.ArenaNotExistException;
import me.yarinlevi.minigameframework.exceptions.NoArenaAvailable;
import me.yarinlevi.minigameframework.game.GameManager;
import me.yarinlevi.minigameframework.player.Statistics;
import me.yarinlevi.minigameframework.utilities.MessagesUtils;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinigameFramework {
    @Getter private static JavaPlugin instance;
    @Getter private static MinigameFramework framework;
    @Getter private ArenaManager arenaManager;
    @Getter @Setter private GameManager gameManager;
    @Getter private ServerSpawn serverSpawn;
    @Getter @Setter private Statistics statistics;

    public void initialize(JavaPlugin javaPlugin) {
        framework = this;
        instance = javaPlugin;

        javaPlugin.saveDefaultConfig();

        // Loads the server spawn/hub point
        serverSpawn = new ServerSpawn();
        serverSpawn.loadServerSpawn();

        // Initializes and loads all arenas
        arenaManager = new ArenaManager();
        arenaManager.loadArenas();

        // Initialize the game manager
        gameManager = new GameManager();

        // Load non hardcode messages
        new MessagesUtils();

        // Load default arenas, games
        javaPlugin.getConfig().getStringList("auto-load").forEach(arenaName -> {
            try {
                gameManager.createGame(arenaName);
            } catch (ArenaNotExistException | NoArenaAvailable e) {
                e.printStackTrace();
            }
        });


        javaPlugin.getCommand("arena").setExecutor(new ArenaCommand());
        javaPlugin.getCommand("game").setExecutor(new PlayerCommand());
        javaPlugin.getCommand("admin").setExecutor(new AdminCommand());
    }
}
