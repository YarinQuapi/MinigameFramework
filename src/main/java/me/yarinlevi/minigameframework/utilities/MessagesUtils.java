package me.yarinlevi.minigameframework.utilities;

import me.yarinlevi.minigameframework.MinigameFramework;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author YarinQuapi
 **/
public class MessagesUtils {
    private static final Map<String, String> messages = new HashMap<>();
    //static Pattern urlPattern = Pattern.compile("https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)");

    private static FileConfiguration messagesData;

    public MessagesUtils() {
        File messagesFile = new File(MinigameFramework.getInstance().getDataFolder(), "messages.yml");
        messagesData = YamlConfiguration.loadConfiguration(messagesFile);

        FileUtils.registerData(messagesFile, messagesData);

        messagesData.getKeys(false).forEach(key -> messages.put(key, messagesData.getString(key)));
    }

    public static void reload() {
        messages.clear();

        File messagesFile = new File(MinigameFramework.getInstance().getDataFolder(), "messages.yml");
        messagesData = YamlConfiguration.loadConfiguration(messagesFile);

        FileUtils.registerData(messagesFile, messagesData);

        messagesData.getKeys(false).forEach(key -> messages.put(key, messagesData.getString(key)));
    }

    public static String getMessage(String key, Object... args) {
        return messages.get(key).replaceAll("&", "§").formatted(args);
    }

    public static List<String> getLines(String key) {
        return messagesData.getStringList(key);
    }

    public static String getMessageLines(String key, Object... args) {
        StringBuilder message = new StringBuilder();

        for (String string : messagesData.getStringList(key)) {
            message.append(string.replaceAll("&", "§"));
        }

        return message.toString().formatted(args);
    }

    public static String getRawFormattedString(String key, Object... args) {
        return messages.getOrDefault(key, key).replaceAll("&", "§").formatted(args);
    }

    public static String getRawString(String key) {
        return messages.getOrDefault(key, key).replaceAll("&", "§");
    }
}
