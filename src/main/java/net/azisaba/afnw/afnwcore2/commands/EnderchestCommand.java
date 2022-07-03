package net.azisaba.afnw.afnwcore2.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EnderchestCommand implements CommandExecutor {

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    if (!(command.getName().equals("pc"))) {
      return false;
    }

    if (!(sender instanceof Player p)) {
      sender.sendMessage(Component.text("/pcコマンドはプレイヤーのみ実行可能です。", NamedTextColor.RED));
      return false;
    }

    if (!(sender.hasPermission("afnw.command.pc"))) {
      return false;
    }

    p.openInventory(p.getEnderChest());
    p.sendMessage(Component.text("個人チェストを開きました", NamedTextColor.DARK_PURPLE));
    return true;
  }

}
