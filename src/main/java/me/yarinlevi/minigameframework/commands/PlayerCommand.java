package me.yarinlevi.minigameframework.commands;

import me.yarinlevi.minigameframework.MinigameFramework;
import me.yarinlevi.minigameframework.exceptions.PlayerNotInGameException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author YarinQuapi
 **/
public class PlayerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            switch (args[0].toLowerCase()) {
                case "join" -> {
                    String game = args[1];

                    MinigameFramework.getFramework().getGameManager().getAvailableGames().stream().filter(x -> x.getGameName().equalsIgnoreCase(game)).findFirst().get().addPlayer(player);
                }

                case "leave" -> {
                    try {
                        MinigameFramework.getFramework().getGameManager().getPlayerGame(player).removePlayer(player);
                    } catch (PlayerNotInGameException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return false;
    }
}
