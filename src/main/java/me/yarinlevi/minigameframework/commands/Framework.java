package me.yarinlevi.minigameframework.commands;

import me.yarinlevi.minigameframework.MinigameFramework;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Framework implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandExecutor, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (commandExecutor instanceof Player player) {

            if (args.length == 0 ) {
                player.sendMessage("§eThank you for using §bMinigameFramework " + MinigameFramework.getFramework().getVersion());
                return true;
            }

        }
        return false;
    }
}
