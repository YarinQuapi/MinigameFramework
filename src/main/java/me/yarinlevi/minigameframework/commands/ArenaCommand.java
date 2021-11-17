package me.yarinlevi.minigameframework.commands;

import me.yarinlevi.minigameframework.MinigameFramework;
import me.yarinlevi.minigameframework.arena.Arena;
import me.yarinlevi.minigameframework.exceptions.ArenaNotExistException;
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
                case "createarena" -> MinigameFramework.getFramework().getArenaManager().createArena(player, args[1], Integer.parseInt(args[2]));

                case "loadarena" -> MinigameFramework.getFramework().getArenaManager().loadArena(args[1]);

                case "edit" -> {
                    try {
                        MinigameFramework.getFramework().getArenaManager().editArena(player, args[1]);
                    } catch (ArenaNotExistException e) {
                        player.sendMessage("§cCouldn't edit arena!");
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
                        }

                        case "pos2" -> {
                            MinigameFramework.getFramework().getArenaManager().getEditArenaData(player).setPos2(player.getLocation().toVector());
                        }
                    }
                }

                case "savemap" -> {
                    MinigameFramework.getFramework().getArenaManager().getEditArenaData(player).save(player.getLocation());
                }

                case "addlocation" -> MinigameFramework.getFramework().getArenaManager().getEditArena(player).addLocation(player.getLocation());

                case "removelocation" -> MinigameFramework.getFramework().getArenaManager().getEditArena(player).removeLocation(Integer.parseInt(args[1]));

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
