package net.azisaba.afnw.afnwcore2.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * 投票サイト一覧をコマンド実行者に送信する
 */
public class VoteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(command.getName().equals("vote"))) return true;
        if(!(sender.hasPermission("afnw.command.vote"))) return true;

        String[] voteSiteURLs = {
                ChatColor.GOLD +
                """
                投票よろしくお願いします!
                ・monocraft
                https://monocraft.net/servers/xWBVrf1nqB2P0LxlMm2v
                ・JMS
                https://minecraft.jp/servers/azisaba.net
                """
        };

        sender.sendMessage(voteSiteURLs);
        return true;
    }
}
