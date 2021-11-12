package me.yarinlevi.minigameframework.arena;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import me.yarinlevi.minigameframework.MinigameFramework;
import me.yarinlevi.minigameframework.exceptions.ArenaNotExistException;
import me.yarinlevi.minigameframework.utilities.FileUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ArenaManager {
    @Getter private final Map<Player, Arena> arenaEditor = new HashMap<>();
    private final Map<String, Arena> arenaSet = new HashMap<>();

    @Getter private static ArenaManager arenaManager;

    protected File arenaFile;
    protected FileConfiguration arenaData;

    public ArenaManager(MinigameFramework instance) {
        arenaManager = this;

        this.arenaFile = new File(instance.getDataFolder(), "arenas.yml");
        this.arenaData = YamlConfiguration.loadConfiguration(this.arenaFile);

        FileUtils.registerData(this.arenaFile, this.arenaData);
    }

    public void createArena(Player player, String arenaName, int maxPlayers) {
        Arena arena = new Arena(arenaName, player.getWorld().getName(), maxPlayers);

        arenaEditor.put(player, arena);
        arenaSet.put(arenaName, arena);

        try {
            this.saveArena(arena);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editArena(Player player, String arenaName) throws ArenaNotExistException {
        Arena arena = getArena(arenaName);
        arenaEditor.put(player, arena);
    }

    public void addLocation(Player player) {
        arenaEditor.get(player).addLocation(player.getLocation());
    }

    public void removeLocation(Player player, int index) {
        arenaEditor.get(player).removeLocation(index);
    }

    public boolean isLoaded(String arena) {
        return arenaSet.values().stream().anyMatch(x -> x.getArenaName().equalsIgnoreCase(arena));
    }

    public Arena getEditArena(Player player) {
        return arenaEditor.get(player);
    }

    public Arena getArena(String arena) throws ArenaNotExistException {
        return arenaSet.values().stream().filter(x -> x.getArenaName().equalsIgnoreCase(arena)).findFirst().orElseThrow(ArenaNotExistException::new);
    }

    public void saveArena(Arena arena) throws IOException {
        Gson gson = new Gson();

        String json = gson.toJson(arena);

        arenaData.set(arena.getArenaName(), json);
        save();
    }

    public Arena loadArena(String arenaName) throws FileNotFoundException {
        Gson gson = new Gson();

        if (!isLoaded(arenaName)) {
            Arena arena = gson.fromJson(arenaData.getString(arenaName), Arena.class);
            arenaSet.put(arenaName, arena);
            return arena;
        } else return arenaSet.get(arenaName);
    }

    private void save() {
        try {
            arenaData.save(arenaFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
