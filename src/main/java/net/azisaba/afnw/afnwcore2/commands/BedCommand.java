package net.azisaba.afnw.afnwcore2.commands;

import io.papermc.paper.event.player.PlayerDeepSleepEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

/**
 * コマンド "bed" の実装レコードです。
 * @param plugin
 */
public record BedCommand(JavaPlugin plugin) implements CommandExecutor, Listener {

  /**
   * 実行したプレイヤーのリスポーン地点へTPするコマンド
   * @param sender Source of the command
   * @param command Command which was executed
   * @param label Alias of the command which was used
   * @param args Passed command arguments
   * @return Result of command execution
   */
  @Override
  public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
    if(!(command.getName().equals("bed"))) {
      return false;
    }
    if(!(sender.hasPermission("afnw.command.bed"))) {
      return false;
    }

    Player p = (Player) sender;
    Location bedLoc = p.getBedSpawnLocation();
    if(bedLoc == null) {
      p.sendMessage(Component.text("リスポーン地点を取得することができませんでした。ベッドで寝て設定してください。", NamedTextColor.RED));
      return false;
    }

    FileConfiguration config = plugin.getConfig();
    int standby = config.getInt("tp.standby", 10);

    if(p.hasPermission("afnw.bypass.standby")) {
      p.teleport(bedLoc);
      sender.sendMessage(Component.text("リスポーン地点へテレポートしました。(Admin/Mod権限をもっているため、待機時間は発生しません。)", NamedTextColor.GREEN));
      return true;
    }

    p.sendMessage(Component.text(standby + "秒後、リスポーン地点へテレポートします....", NamedTextColor.AQUA));
    new BukkitRunnable() {
      @Override
      public void run() {
        p.teleport(bedLoc);
        p.sendMessage(Component.text("リスポーン地点へテレポートしました。", NamedTextColor.YELLOW));
      }
    }.runTaskLater(plugin, 20L * standby);
    return true;
  }

  @EventHandler
  public void onBed(PlayerDeepSleepEvent e) {
    Player p = e.getPlayer();
    if(!(p.isSneaking())) {
      return;
    }
    p.sendMessage(Component.text("リスポーン地点を更新しました。", NamedTextColor.AQUA));
  }

}
