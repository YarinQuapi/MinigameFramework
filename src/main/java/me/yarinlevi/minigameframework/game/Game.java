package me.yarinlevi.minigameframework.game;

import lombok.Getter;
import me.yarinlevi.minigameframework.MinigameFramework;
import me.yarinlevi.minigameframework.arena.Arena;
import me.yarinlevi.minigameframework.utilities.Settings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YarinQuapi
 **/
public class Game {
    private final Arena arena;
    @Getter private final List<Player> gamePlayers = new ArrayList<>();
    @Getter private GameState gameState = GameState.UNINITIALIZED;

    @Getter private boolean started = false;
    private int tick = 0;
    private int countdown = Settings.GAME_START_TIMER;
    @Getter private int gameTimer = 0;
    private GameListener gameListener;

    protected Game(Arena arena) {
        this.arena = arena;
    }

    public void startStartProcess() {
        this.gameState = GameState.STARTING;

        // Starts onTick() timer
        Bukkit.getScheduler().runTaskTimer(MinigameFramework.getInstance(), this::onTick, 1L, 1L);
    }

    /**
     * Runs per server tick. Used to game timers and countdowns
     */
    public void onTick() {
        tick++;

        if (tick == 20) {
            tick = 0;

            if (gameState == GameState.STARTING) {

                if (canStart()) {
                    countdown--;

                    if (countdown == 0) {
                        gameState = GameState.RUNNING;
                        this.start();
                    }
                } else {
                    gameState = GameState.UNINITIALIZED;
                    countdown = Settings.GAME_START_TIMER;
                }

            } else if (gameState == GameState.RUNNING) {
                gameTimer++;
            }
        }
    }

    public void construct() {
        gameListener = new GameListener(this);
    }

    /**
     * Main start method
     */
    public void start() {
        started = true;

        Bukkit.getServer().getPluginManager().registerEvents(this.gameListener, MinigameFramework.getInstance());

        // Edit code per minigame, here we'll allow for movement and enable pvp.
    }

    /**
     * Used to replace the game's listener to allow for custom options per game and runtime editing
     * @param gameListener the new listener
     */
    public void replaceGameListener(Class<? extends GameListener> gameListener) {
        try {
            GameListener gl = gameListener.newInstance();

            this.unregisterGameListener();

            this.gameListener = gl;
            Bukkit.getServer().getPluginManager().registerEvents(gl, MinigameFramework.getInstance());

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void unregisterGameListener() {
        HandlerList.unregisterAll(this.gameListener);
    }

    /**
     * Add a player to the game
     * @return true if added successfully
     */
    public boolean addPlayer(Player player) {
        if (!isJoiningAllowed()) {
            return false;
        } else {
            this.gamePlayers.add(player);

            // Teleports player to his spawn location
            player.teleport(arena.getLocations().get(this.gamePlayers.indexOf(player)).toLocation());

            // Begin start process if enough players are present
            if (canStart()) {
                this.startStartProcess(); // Call start process
            }

            return true;
        }
    }

    /**
     * Is the player in the game?
     * @return true if is in game
     */
    public boolean isInGame(Player player) {
        return gamePlayers.stream().anyMatch(x -> x.getUniqueId().equals(player.getUniqueId()));
    }

    /**
     * Removes the player from game checking
     * @return true if removed, false if wasn't present
     */
    public boolean removePlayer(Player player) {
        if (gamePlayers.contains(player)) {
            gamePlayers.remove(player);
            return true;
        } else return false;
    }

    /**
     * Is the game full
     * @return true or false
     */
    public boolean isFull() {
        return (arena.getMaxPlayers() / gamePlayers.size()) == 1;
    }

    private boolean canStart() {
        return (arena.getMaxPlayers() / gamePlayers.size()) * 100 > Settings.GAME_MIN_PERCENTAGE;
    }

    /**
     * Can player join?
     * @return true or false
     */
    private boolean isJoiningAllowed() {
        if (this.isFull()) return false;
        if (this.gameState == GameState.RUNNING) return false;
        if (this.gameState == GameState.STOPPED) return false;
        if (this.gameState == GameState.RESTARTING) return false;

        return true;
    }
}
