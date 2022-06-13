package net.azisaba.afnw.afnwcore2.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * /respawn - プレイヤーの強制リスポーン
 *
 * @author m2en
 * @see org.bukkit.command.CommandExecutor
 */
public class RespawnCommand implements TabExecutor {

    /**
     * /respawn
     * @param sender Source of the command
     * @param command Command which was executed
     * @param label Alias of the command which was used
     * @param args Passed command arguments
     * @return コマンドの実行結果
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(command.getName().equals("respawn"))) {
            return true;
        }
        if(args.length == 0) {
            sender.sendMessage(Component.text("Usage: /respawn <player>", NamedTextColor.RED));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if(target == null) {
            sender.sendMessage(Component.text("プレイヤーを見つけることができませんでした。", NamedTextColor.RED));
            return true;
        }

        target.spigot().respawn();
        sender.sendMessage(Component.text(target.getName() + "を強制的にリスポーンさせました。", NamedTextColor.YELLOW));

        return true;
    }

    private static @NotNull List<String> filter(Stream<String> stream, String s) {
        return stream.filter(s1 -> s1.toLowerCase(Locale.ROOT).startsWith(s.toLowerCase(Locale.ROOT))).collect(Collectors.toList());
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if(args.length == 0) {
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
        }
        if(args.length == 1) {
            return filter(Bukkit.getOnlinePlayers().stream().map(Player::getName), args[0]);
        }
        return Collections.emptyList();
    }
}
