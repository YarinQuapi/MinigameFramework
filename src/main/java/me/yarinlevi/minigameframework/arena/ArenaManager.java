package me.yarinlevi.minigameframework.arena;

import com.google.gson.Gson;
import lombok.Getter;
import me.yarinlevi.minigameframework.MinigameFramework;
import me.yarinlevi.minigameframework.exceptions.ArenaNotExistException;
import me.yarinlevi.minigameframework.exceptions.NoArenaAvailable;
import me.yarinlevi.minigameframework.utilities.FileUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ArenaManager {
    @Getter private final Map<Player, ArenaEdit> arenaEditor = new HashMap<>();
    private final Map<String, Arena> arenaMap = new HashMap<>();

    @Getter private static ArenaManager arenaManager;

    protected File arenaFile;
    protected FileConfiguration arenaData;
    private final Gson gson = new Gson();

    public ArenaManager() {
        arenaManager = this;

        this.arenaFile = new File(MinigameFramework.getInstance().getDataFolder(), "arenas.yml");
        this.arenaData = YamlConfiguration.loadConfiguration(this.arenaFile);

        FileUtils.registerData(this.arenaFile, this.arenaData);
    }

    public void loadArenas() {
        arenaData.getKeys(false).forEach(x -> {
            if (!isLoaded(x)) {
                loadArena(x).resetArena();
            }
        });
    }

    /**
     * Create a new arena
     * @param player creating the arena
     * @param arenaName name of the arena
     * @param maxPlayers max players able to connect to a game with the arena
     */
    public void createArena(Player player, String arenaName, int maxPlayers) {
        Arena arena = new Arena(arenaName, player.getWorld().getName(), maxPlayers);

        arenaEditor.put(player, new ArenaEdit(arena));
        arenaMap.put(arenaName, arena);
    }

    /**
     * Edit an arena (add, remove locations, etc.)
     * @param player that would like to edit
     * @param arenaName name of the arena to edit
     * @throws ArenaNotExistException If the arena is not loaded or does not exist
     */
    public void editArena(Player player, String arenaName) throws ArenaNotExistException {
        Arena arena = getArena(arenaName);
        arenaEditor.put(player, new ArenaEdit(arena));
    }

    /**
     * Is the arena loaded?
     * @param arena name
     * @return true if loaded false if not
     */
    public boolean isLoaded(String arena) {
        return arenaMap.values().stream().anyMatch(x -> x.getArenaName().equalsIgnoreCase(arena));
    }

    /**
     * Gets the arena the player is currently editing
     * @return Arena object
     */
    public Arena getEditArena(Player player) {
        return arenaEditor.get(player).getArena();
    }

    /**
     * Gets the arena the player is currently editing
     * @return Arena object
     */
    public ArenaEdit getEditArenaData(Player player) {
        return arenaEditor.get(player);
    }

    public Arena getAvailable() throws NoArenaAvailable {
        return arenaMap.values().stream().filter(x -> !x.isAssigned()).findFirst().orElseThrow(NoArenaAvailable::new);
    }

    /**
     * Get an Arena
     * @param arena name
     * @return Arena object
     * @throws ArenaNotExistException If arena is not loaded or does not exist
     */
    public Arena getArena(String arena) throws ArenaNotExistException {
        return arenaMap.values().stream().filter(x -> x.getArenaName().equalsIgnoreCase(arena)).findFirst().orElseThrow(ArenaNotExistException::new);
    }

    /**
     * Saves the arena to data
     * @param arena to save
     * @throws IOException
     */
    public void saveArena(Arena arena) throws IOException {
        Gson gson = new Gson();

        String json = gson.toJson(arena);

        arenaData.set(arena.getArenaName(), json);
        save();
    }

    public Arena loadArena(String arenaName) {
        Arena arena = gson.fromJson(arenaData.getString(arenaName), Arena.class);
        arenaMap.put(arenaName, arena);

        return arena;
    }

    private void save() {
        try {
            arenaData.save(arenaFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
