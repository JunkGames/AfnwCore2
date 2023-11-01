package net.azisaba.afnw.afnwcore2.commands;

import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.items.MythicItem;
import net.azisaba.afnw.afnwcore2.util.item.ItemUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public record MMGiveCommand(@NotNull Plugin plugin) implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = Bukkit.getPlayerExact(args[0]);
        if (player == null) {
            sender.sendMessage(Component.text("Player " + args[0] + " is offline (tried to give " + args[1] + " (extra: " + String.join(", ", args) + "))", NamedTextColor.RED));
            return true;
        }
        String mythicType = args[1];
        int amount = args.length == 2 ? 1 : Integer.parseInt(args[2]);
        for (ItemStack value : player.getInventory().addItem(MythicBukkit.inst().getItemManager().getItemStack(mythicType, amount)).values()) {
            ItemUtil.addToStashIfEnabledAsync(plugin, player.getUniqueId(), value);
            player.sendMessage(Component.text("インベントリがいっぱいのため、Stashに入りました。", NamedTextColor.RED));
            player.sendMessage(
                    Component.text("/pickupstashで受け取れます。", NamedTextColor.RED)
                            .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/pickupstash"))
                            .hoverEvent(HoverEvent.showText(Component.text("クリックでStashの画面を開く")))
            );
        }
        return true;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).filter(s -> s.startsWith(args[0])).toList();
        }
        if (args.length == 2) {
            return MythicBukkit.inst()
                    .getItemManager()
                    .getItems()
                    .stream()
                    .map(MythicItem::getInternalName).filter(s -> s.startsWith(args[1]))
                    .toList();
        }
        return Collections.emptyList();
    }
}
