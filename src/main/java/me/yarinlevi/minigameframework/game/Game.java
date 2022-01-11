package me.yarinlevi.minigameframework.game;

import lombok.Getter;
import lombok.Setter;
import me.yarinlevi.minigameframework.MinigameFramework;
import me.yarinlevi.minigameframework.arena.Arena;
import me.yarinlevi.minigameframework.game.events.QGameStateChange;
import me.yarinlevi.minigameframework.game.events.QPlayerJoinGameEvent;
import me.yarinlevi.minigameframework.game.events.Result;
import me.yarinlevi.minigameframework.utilities.Settings;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YarinQuapi
 * Used as a base Game class, can be replaced with a custom one.
 **/
public class Game {
    @Getter private final Arena arena;
    @Getter private final String gameName;
    @Getter private final List<Player> gamePlayers = new ArrayList<>();
    @Getter private final List<Player> alivePlayers = new ArrayList<>();
    @Getter private GameState gameState = GameState.UNINITIALIZED;

    @Getter private boolean started = false;
    private int tick = 0;
    @Getter private int countdown = Settings.GAME_START_TIMER;
    @Getter private int gameTimer = 0;
    @Setter private GameListener gameListener;

    private BukkitTask task;

    protected Game(Arena arena) {
        this.arena = arena;
        this.gameName = arena.getArenaName();
    }

    public void startStartProcess() {
        this.gameState = GameState.STARTING;

        // Starts onTick() timer
        task = Bukkit.getScheduler().runTaskTimer(MinigameFramework.getInstance(), this::onTick, 1L, 1L);
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
                        this.begin();
                    }
                } else {
                    gameState = GameState.UNINITIALIZED;
                    countdown = Settings.GAME_START_TIMER;

                    Bukkit.getPluginManager().callEvent(new QGameStateChange(this));

                }

            } else if (gameState == GameState.RUNNING) {
                gameTimer++;

                if (this.alivePlayers.size() == 1) {
                    Player winner = this.alivePlayers.stream().findFirst().get();

                    this.win(winner);
                }
            }
        }
    }

    /**
     * Registers technical stuff like game listeners
     */
    public void construct() {
        gameListener = new GameListener(this);
    }

    /**
     * Technical start, timers, registration, etc.
     */
    public void begin() {
        started = true;
        tick = 0; // Reset tick clock to allow for accurate game time count
        gameTimer = 0; // Just in case
        alivePlayers.addAll(gamePlayers); // Copy players

        Bukkit.getServer().getPluginManager().registerEvents(this.gameListener, MinigameFramework.getInstance());

        this.gameState = GameState.RUNNING;

        Bukkit.getPluginManager().callEvent(new QGameStateChange(this));

        this.start();
    }

    /**
     * Player start, e.g. drop cages, move from lobby to game, etc.
     */
    public void start() {

    }

    public void win(Player player) {
        this.task.cancel();
        this.unregisterGameListener();

        player.sendTitle("§eVictory Royale!", "§6You've won the game!", 10, 200, 20);

        this.reset();
    }

    public void reset() {
        this.gameState = GameState.RESTARTING;
        Bukkit.getPluginManager().callEvent(new QGameStateChange(this));

        Location location = MinigameFramework.getFramework().getServerSpawn().getServerSpawn();

        this.task.cancel();

        // Teleport everyone out of the game and set their gamemode to survival //Todo: allow gamemode configuration
        this.gamePlayers.forEach(player -> {
            player.setGameMode(GameMode.SURVIVAL);
            player.teleport(location);
        });

        this.gamePlayers.clear();
        this.alivePlayers.clear();

        // Reset technical variables
        this.started = false;
        this.tick = 0;
        this.gameTimer = 0;

        // Resets physical arena
        this.arena.resetArena();


        // Set GameState to uninitialized to allow for joining
        this.gameState = GameState.UNINITIALIZED;
        Bukkit.getPluginManager().callEvent(new QGameStateChange(this));
    }

    /**
     * Used method when a player dies, declares him dead
     * @param player
     */
    public void lose(Player player) {
        player.setGameMode(GameMode.SPECTATOR);
        alivePlayers.remove(player);
        arena.getLocations().get(this.gamePlayers.indexOf(player)).teleport(player);
        player.sendTitle("§cYou lose!", "§ePlease use /game leave to return to lobby", 10, 200, 20);
    }


    /**
     * Used to replace the game's listener to allow for custom options per game and runtime editing
     * @param gameListener the new listener
     */
    public void replaceGameListener(Class<? extends GameListener> gameListener) {
        try {
            GameListener gl = gameListener.newInstance();

            if (this.gameListener != null) this.unregisterGameListener();

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
    public Result addPlayer(Player player) {
        if (!isJoiningAllowed()) {
            return Result.denied;
        } else {
            QPlayerJoinGameEvent playerJoinGameEvent = new QPlayerJoinGameEvent(this, player);
            MinigameFramework.getInstance().getServer().getPluginManager().callEvent(playerJoinGameEvent);

            if (playerJoinGameEvent.getResult() == Result.allowed) {
                this.gamePlayers.add(player);
                MinigameFramework.getFramework().getGameManager().getPlayersInGame().add(player);


                // Teleports player to his spawn location
                arena.getLocations().get(this.gamePlayers.indexOf(player)).teleport(player);

                // Begin start process if enough players are present
                if (canStart()) {
                    this.startStartProcess(); // Call start process
                }
            } else {
                return Result.denied;
            }

            return Result.allowed;
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

            alivePlayers.remove(player); // Remove if player uses /lobby

            player.teleport(MinigameFramework.getFramework().getServerSpawn().getServerSpawn());
            player.setGameMode(GameMode.SURVIVAL);

            MinigameFramework.getFramework().getGameManager().getPlayersInGame().remove(player);

            return true;
        } else return false;
    }

    /**
     * Is the game full
     * @return true or false
     */
    public boolean isFull() {
        return (gamePlayers.size() >= arena.getMaxPlayers());
    }

    private boolean canStart() {
        return (gamePlayers.size() / arena.getMaxPlayers()) * 100 > Settings.GAME_MIN_PERCENTAGE;
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
