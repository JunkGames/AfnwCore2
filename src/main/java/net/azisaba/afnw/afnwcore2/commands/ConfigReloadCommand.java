package net.azisaba.afnw.afnwcore2.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public record ConfigReloadCommand(JavaPlugin plugin) implements CommandExecutor {

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
