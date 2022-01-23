package me.yarinlevi.minigameframework.commands;

import me.yarinlevi.minigameframework.MinigameFramework;
import me.yarinlevi.minigameframework.arena.Arena;
import me.yarinlevi.minigameframework.exceptions.ArenaNotExistException;
import me.yarinlevi.minigameframework.exceptions.NoArenaAvailable;
import me.yarinlevi.minigameframework.exceptions.PlayerNotInGameException;
import me.yarinlevi.minigameframework.game.Game;
import me.yarinlevi.minigameframework.utilities.MessagesUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author YarinQuapi
 **/
public class AdminCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            switch (args[0].toLowerCase()) {
                default -> player.sendMessage(MessagesUtils.getMessageLines("command_admin_help"));

                case "setspawn" -> {
                    MinigameFramework.getFramework().getServerSpawn().setServerSpawn(player.getLocation());
                    player.sendMessage(MessagesUtils.getMessage("admin_spawn_set"));
                    return true;
                }

                case "creategame" -> {
                    String gameName = args[1];

                    try {
                        Arena arena = MinigameFramework.getFramework().getArenaManager().getArena(gameName);
                        try {
                            MinigameFramework.getFramework().getGameManager().createGame(arena);
                        } catch (NoArenaAvailable e) {
                            player.sendMessage(MessagesUtils.getMessage("arena_running"));
                        }

                    } catch (ArenaNotExistException e) {
                        player.sendMessage(MessagesUtils.getMessage("arena_not_exists"));
                    }
                    return true;
                }

                case "forcestart" -> {
                    Game game = MinigameFramework.getFramework().getGameManager().getPlayerGame(player);

                    if (game != null) {

                        game.construct();
                        game.begin();

                        player.sendMessage(MessagesUtils.getMessage("admin_force_started"));
                    } else {
                        player.sendMessage(MessagesUtils.getMessage("not_in_game"));
                    }
                }

                case "resetgame" -> {
                    Game game = MinigameFramework.getFramework().getGameManager().getPlayerGame(player);

                    if (game != null) {
                        game.reset();

                        player.sendMessage(MessagesUtils.getMessage("admin_reset_game"));
                    } else {
                        player.sendMessage(MessagesUtils.getMessage("not_in_game"));
                    }
                }
            }
        }

        return false;
    }
}
