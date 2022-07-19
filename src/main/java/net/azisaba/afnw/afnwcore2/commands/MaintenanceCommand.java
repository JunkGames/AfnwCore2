package net.azisaba.afnw.afnwcore2.commands;

import net.azisaba.afnw.afnwcore2.AfnwCore2;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class MaintenanceCommand implements CommandExecutor {

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
    if(!(command.getName().equals("maintenance"))) {
      return false;
    }

    if(!(sender.hasPermission("afnw.command.maintenance"))) {
      return false;
    }

    sender.sendMessage(Component.text("メンテナンスモードを有効にします。", NamedTextColor.GOLD));
    sender.sendMessage(Component.text("警告: サーバーに参加しているAdminを除く全てのプレイヤーはキックされ、ホワイトリストが有効になります。", NamedTextColor.RED));

    Bukkit.broadcast(Component.text("只今から3分後、メンテナンスを開始します。", NamedTextColor.GOLD));

    new BukkitRunnable() {
      @Override
      public void run() {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kickall");
        Bukkit.setWhitelist(true);
        Bukkit.broadcast(Component.text("正常にメンテナンスモードがONになりました。", NamedTextColor.GOLD));
      }
    }.runTaskLater(AfnwCore2.getPlugin(AfnwCore2.class), 20L * 180);

    return true;
  }
}
