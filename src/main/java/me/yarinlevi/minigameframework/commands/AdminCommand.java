package me.yarinlevi.minigameframework.commands;

import me.yarinlevi.minigameframework.MinigameFramework;
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
                    MinigameFramework.getServerSpawn().setServerSpawn(player.getLocation());
                    player.sendMessage("Successfully changed server spawn!");
                    return true;
                }
            }
        }

        return false;
    }
}
