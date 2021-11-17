package me.yarinlevi.minigameframework.commands;

import me.yarinlevi.minigameframework.MinigameFramework;
import me.yarinlevi.minigameframework.arena.Arena;
import me.yarinlevi.minigameframework.exceptions.ArenaNotExistException;
import me.yarinlevi.minigameframework.exceptions.NoArenaAvailable;
import me.yarinlevi.minigameframework.exceptions.PlayerNotInGameException;
import me.yarinlevi.minigameframework.game.Game;
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
                case "setspawn" -> {
                    MinigameFramework.getFramework().getServerSpawn().setServerSpawn(player.getLocation());
                    player.sendMessage("Successfully changed server spawn!");
                    return true;
                }

                case "creategame" -> {
                    String gameName = args[1];

                    try {
                        Arena arena = MinigameFramework.getFramework().getArenaManager().getArena(gameName);
                        try {
                            MinigameFramework.getFramework().getGameManager().createGame(arena);
                        } catch (NoArenaAvailable e) {
                            player.sendMessage("A game is already running on that arena.");
                        }

                    } catch (ArenaNotExistException e) {
                        player.sendMessage("No arena with that name was found.");
                    }
                    return true;
                }

                case "forcestart" -> {
                    try {
                        Game game = MinigameFramework.getFramework().getGameManager().getPlayerGame(player);

                        game.construct();
                        game.start();
                    } catch (PlayerNotInGameException e) {
                        player.sendMessage("§cYou are not in a game!");
                    }
                }

                case "resetgame" -> {
                    try {
                        Game game = MinigameFramework.getFramework().getGameManager().getPlayerGame(player);

                        game.reset();
                    } catch (PlayerNotInGameException e) {
                        player.sendMessage("§cYou are not in a game!");
                    }
                }
            }
        }

        return false;
    }
}
