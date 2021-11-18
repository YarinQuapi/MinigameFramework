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

        serverSpawn = new ServerSpawn();
        serverSpawn.loadServerSpawn();

        arenaManager = new ArenaManager();
        arenaManager.loadArenas();

        gameManager = new GameManager();

        //Load default arenas, games
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
