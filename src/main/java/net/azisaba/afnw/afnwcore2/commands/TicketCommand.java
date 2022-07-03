package net.azisaba.afnw.afnwcore2.commands;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import net.azisaba.afnw.afnwcore2.util.item.AfnwTicket;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * /ticket - Distribute and send commands
 *
 * @author m2en
 * @see org.bukkit.command.CommandExecutor
 */
public record TicketCommand(JavaPlugin plugin) implements CommandExecutor {

    /**
     * /ticket send, give
     * @param sender Source of the command
     * @param command Command which was executed
     * @param label Alias of the command which was used
     * @param args Passed command arguments
     * @return Result of command execution
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(command.getName().equals("ticket"))) {
            return true;
        }
        if(args.length == 0) {
            sender.sendMessage(Component.text("Usage: /ticket <sub-command>\nsub-command: give, show", NamedTextColor.RED));
            return true;
        }

        switch (args[0]) {
            case "send" -> {
                if (sender instanceof Player) {
                    sender.sendMessage(Component.text("/ticketコマンドはコンソールからのみ実行可能です。チケットの配布は/ticket giveを使用してください。"));
                    break;
                }

                FileConfiguration config = plugin.getConfig();
                if (!(config.isInt("vote.send-ticket-size"))) {
                    throw new Error("configの値が数値ではありません。対象:vote> item-size, scaffold-size");
                }

                int ticketSize = config.getInt("vote.send-ticket-size", 1);
                Player sendTarget = Bukkit.getPlayerExact(args[1]);
                if (sendTarget == null) {
                    sender.sendMessage(Component.text("対象のプレイヤーを発見することができませんでした。本来のターゲット:" + args[2]));
                    break;
                }

                Inventory inv = sendTarget.getInventory();

                if(inv.firstEmpty() == -1) {
                    LocalDateTime date = LocalDateTime.now();
                    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    String dateString = dateFormat.format(date);
                    sendTarget.sendMessage(Component.text("インベントリが満杯でチケットを入手できませんでした。\nこのチャットの画像を公式Discord \"#補填 | Afnw鯖\" に送ってください。", NamedTextColor.RED));
                    sendTarget.sendMessage(ChatColor.RED + "MCID: " + ChatColor.WHITE + sendTarget.getName());
                    sendTarget.sendMessage(ChatColor.RED + "UUID: " + ChatColor.WHITE + sendTarget.getUniqueId());
                    sendTarget.sendMessage(ChatColor.RED + "日時: " + ChatColor.WHITE + dateString);
                    break;
                }


                for (int i = 0; i < ticketSize; i++) {
                    inv.addItem(AfnwTicket.afnwTicket);
                }

                // 成功した趣旨の情報送信
                sender.sendMessage(Component.text(sendTarget.getName() + "へのチケット送信に成功しました。配布数:" + ticketSize));
                // 通知
                sendTarget.sendMessage(Component.text("チケットを入手しました。/afnwを実行することでアイテムと交換することができます。", NamedTextColor.YELLOW));
            }
            case "give" -> {
                if(!(args.length == 3)) {
                    sender.sendMessage(Component.text("Usage(give): /ticket give <size> <player>", NamedTextColor.RED));
                    return true;
                }

                int giveTicketInt = Integer.parseInt(args[1]);
                Player giveTarget = Bukkit.getPlayerExact(args[2]);
                if(giveTarget == null) {
                    sender.sendMessage(Component.text("対象のプレイヤーを発見することができませんでした", NamedTextColor.RED));
                    break;
                }
                if(giveTicketInt <= 0) {
                    sender.sendMessage(Component.text("配布数は0以上を指定する必要があります。", NamedTextColor.RED));
                    break;
                }

                for(int i = 0; i < giveTicketInt; i++) {
                    giveTarget.getInventory().addItem(AfnwTicket.afnwTicket);
                }

                // 成功した趣旨の情報送信
                Bukkit.getLogger().info(sender.getName() + "が" + giveTarget.getName() + "にチケットを配布しました。: " + giveTicketInt + "枚");
                sender.sendMessage(Component.text(giveTarget.getName() + "に" + giveTicketInt + "枚のチケットを配布しました。", NamedTextColor.YELLOW));
                // 通知
                giveTarget.sendMessage(Component.text("チケットを入手しました。(補填)/afnwを実行することでアイテムと交換することができます。", NamedTextColor.YELLOW));
            }

            // TODO: 連続投票ボーナス数の表示処理の作成 : /ticket show
        }
        return true;
    }
}
