package me.yarinlevi.minigameframework.commands;

import me.yarinlevi.minigameframework.MinigameFramework;
import me.yarinlevi.minigameframework.exceptions.ArenaNotExistException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.FileNotFoundException;

public class ArenaCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args[0].equalsIgnoreCase("createarena")) {
                MinigameFramework.getArenaManager().createArena(player, args[1], Integer.parseInt(args[2]));
            } else if (args[0].equalsIgnoreCase("loadarena")) {
                try {
                    MinigameFramework.getArenaManager().loadArena(args[1]);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (args[0].equalsIgnoreCase("edit")) {
                try {
                    MinigameFramework.getArenaManager().editArena(player, args[1]);
                } catch (ArenaNotExistException e) {
                    player.sendMessage("§cCouldn't edit arena!");
                }
            } else if (args[0].equalsIgnoreCase("addlocation")) {
                MinigameFramework.getArenaManager().getEditArena(player).addLocation(player.getLocation());
            }
        }
        return true;
    }
}
