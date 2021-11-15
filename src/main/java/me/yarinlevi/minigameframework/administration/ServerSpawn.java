package me.yarinlevi.minigameframework.administration;

import lombok.Getter;
import me.yarinlevi.minigameframework.MinigameFramework;
import me.yarinlevi.minigameframework.utilities.FileUtils;
import me.yarinlevi.minigameframework.utilities.MiniaturizedLocation;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * @author YarinQuapi
 **/
public class ServerSpawn {
    @Getter private Location serverSpawn;

    public void setServerSpawn(Location location) {
        this.serverSpawn = location;

        File file = new File(MinigameFramework.getInstance().getDataFolder(), "settings.yml");
        FileConfiguration data = YamlConfiguration.loadConfiguration(file);

        FileUtils.registerData(file, data);

        data.set("spawn", MiniaturizedLocation.construct(location));

        FileUtils.saveData(file, data);
    }

    public void loadServerSpawn() {
        File file = new File(MinigameFramework.getInstance().getDataFolder(), "settings.yml");
        FileConfiguration data = YamlConfiguration.loadConfiguration(file);

        FileUtils.registerData(file, data);

        if (data.contains("spawn")) {
            this.serverSpawn = ((MiniaturizedLocation) data.get("spawn")).toLocation();
        } else {
            MinigameFramework.getInstance().getLogger().severe("Hey! no server spawn is defined! the framework wont work without it!");
        }
    }
}
