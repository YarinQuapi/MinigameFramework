package me.yarinlevi.minigameframework.commands;

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

                }
            }
        }

        return false;
    }
}
