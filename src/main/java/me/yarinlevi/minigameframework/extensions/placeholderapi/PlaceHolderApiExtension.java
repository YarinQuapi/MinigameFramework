package me.yarinlevi.minigameframework.extensions.placeholderapi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.yarinlevi.minigameframework.MinigameFramework;
import me.yarinlevi.minigameframework.api.FastPlayerAccess;
import me.yarinlevi.minigameframework.exceptions.PlayerNotInGameException;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author YarinQuapi
 **/
public class PlaceHolderApiExtension extends PlaceholderExpansion {
    private final MinigameFramework framework;

    public PlaceHolderApiExtension(MinigameFramework framework) {
        this.framework = framework;
    }

    @Override
    public @NotNull String getIdentifier() {
        return MinigameFramework.getInstance().getDescription().getName();
    }

    @Override
    public @NotNull String getAuthor() {
        return MinigameFramework.getInstance().getDescription().getAuthors().get(0);
    }

    @Override
    public @NotNull String getVersion() {
        return MinigameFramework.getInstance().getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String param) {
        switch (param.toLowerCase()) {

            case "game_kills" -> {
                try {
                    return String.valueOf(FastPlayerAccess.getPlayerGameKills(player));
                } catch (PlayerNotInGameException e) {
                    return "Not in game.";
                }
            }

            case "total_kills" -> {
                return String.valueOf(MinigameFramework.getFramework().getStatistics().getKills(player));
            }

            default -> {
                return null;
            }
        }
    }
}
