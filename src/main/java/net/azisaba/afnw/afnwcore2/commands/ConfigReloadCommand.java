package net.azisaba.afnw.afnwcore2.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * /config reload - Reload Config file
 *
 * @author m2en
 * @see org.bukkit.command.CommandExecutor
 */
public record ConfigReloadCommand(JavaPlugin plugin) implements CommandExecutor {

    /**
     * /config
     * @param sender Source of the command
     * @param command Command which was executed
     * @param label Alias of the command which was used
     * @param args Passed command arguments
     * @return Result of command execution
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(command.getName().equals("config_reload"))) {
            return true;
        }
        if(!(sender.hasPermission("afnw.command.config_reload"))) {
            return true;
        }

        sender.sendMessage(Component.text("コンフィグファイルの再読み込みを開始します。", NamedTextColor.YELLOW));
        plugin.reloadConfig();
        sender.sendMessage(Component.text("コンフィグファイルの再読み込みが完了しました。", NamedTextColor.YELLOW));

        return true;
    }
}
