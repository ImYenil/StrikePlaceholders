package net.yenil.strikeplaceholders.command;

import net.yenil.strikeplaceholders.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class StrikePlaceholdersCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("strikeplaceholders.admin")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&cYou don't have permission to use this command!"));
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor
                    .translateAlternateColorCodes('&', "/strikeplaceholders reload"));
            return true;
        }
        String subCommand = args[0];
        if (subCommand.equalsIgnoreCase("reload")) {
            Main.getInstance().reloadSystem();
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&aStrikePlaceholders Reloaded Successfully!"));
        }
        return false;
    }
}
