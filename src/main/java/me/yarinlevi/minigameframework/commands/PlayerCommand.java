package me.yarinlevi.minigameframework.commands;

import me.yarinlevi.minigameframework.MinigameFramework;
import me.yarinlevi.minigameframework.game.Game;
import me.yarinlevi.minigameframework.game.events.Result;
import me.yarinlevi.minigameframework.utilities.MessagesUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * @author YarinQuapi
 **/
public class PlayerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player player) {
            switch (args[0].toLowerCase()) {
                case "join" -> {
                    String game = args[1];

                    Result result = MinigameFramework.getFramework().getGameManager().getAvailableGames().stream().filter(x -> x.getGameName().equalsIgnoreCase(game)).findFirst().get().addPlayer(player);

                    if (result == Result.denied) {
                        sender.sendMessage(MessagesUtils.getMessage("you_are_not_allowed"));
                    } else {
                        sender.sendMessage(MessagesUtils.getMessage("game_joined"));
                    }
                }

                case "leave" -> {
                    Game game = MinigameFramework.getFramework().getGameManager().getPlayerGame(player);

                    if (game != null && game.removePlayer(player)) {
                        player.sendMessage(MessagesUtils.getMessage("game_leave"));
                    } else {
                        player.sendMessage(MessagesUtils.getMessage("not_in_game"));
                    }
                }
            }
        }

        return true;
    }
}
