package me.yarinlevi.minigameframework.commands;

import me.yarinlevi.minigameframework.MinigameFramework;
import me.yarinlevi.minigameframework.arena.Arena;
import me.yarinlevi.minigameframework.exceptions.ArenaNotExistException;
import me.yarinlevi.minigameframework.utilities.MessagesUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class ArenaCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {

            switch (args[0].toLowerCase()) {
                default -> player.sendMessage(MessagesUtils.getMessageLines("command_arena_help"));

                case "createarena" -> {
                    MinigameFramework.getFramework().getArenaManager().createArena(player, args[1], Integer.parseInt(args[2]));
                    player.sendMessage(MessagesUtils.getMessage("arena_create_begin"));
                }

                case "loadarena" -> MinigameFramework.getFramework().getArenaManager().loadArena(args[1]);

                case "edit" -> {
                    try {
                        MinigameFramework.getFramework().getArenaManager().editArena(player, args[1]);
                    } catch (ArenaNotExistException e) {
                        player.sendMessage(MessagesUtils.getMessage("arena_not_exists"));
                    }
                }

                case "save" -> {
                    try {
                        MinigameFramework.getFramework().getArenaManager().saveArena(MinigameFramework.getFramework().getArenaManager().getEditArena(player));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                case "set" -> {
                    switch (args[1].toLowerCase()) {
                        case "pos1" -> {
                            MinigameFramework.getFramework().getArenaManager().getEditArenaData(player).setPos1(player.getLocation().toVector());
                            player.sendMessage(MessagesUtils.getMessage("admin_arena_pos1"));
                        }

                        case "pos2" -> {
                            MinigameFramework.getFramework().getArenaManager().getEditArenaData(player).setPos2(player.getLocation().toVector());
                            player.sendMessage(MessagesUtils.getMessage("admin_arena_pos2"));
                        }
                    }
                }

                case "savemap" -> {
                    MinigameFramework.getFramework().getArenaManager().getEditArenaData(player).save(player.getLocation());
                    player.sendMessage(MessagesUtils.getMessage("admin_arena_savemap"));
                }

                case "addlocation" -> {
                    MinigameFramework.getFramework().getArenaManager().getEditArena(player).addLocation(player.getLocation());
                    player.sendMessage(MessagesUtils.getMessage("admin_arena_addlocation"));
                }

                case "removelocation" -> {
                    MinigameFramework.getFramework().getArenaManager().getEditArena(player).removeLocation(Integer.parseInt(args[1]));
                    player.sendMessage(MessagesUtils.getMessage("admin_arena_removelocation"));
                }

                case "listlocations" -> {
                    try {
                        Arena arena = MinigameFramework.getFramework().getArenaManager().getArena(args[1]);

                        arena.getLocations().forEach(x -> player.sendMessage("Id#" + arena.getLocations().indexOf(x) + " " + x.prettyPrint()));

                    } catch (ArenaNotExistException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return true;
    }
}
