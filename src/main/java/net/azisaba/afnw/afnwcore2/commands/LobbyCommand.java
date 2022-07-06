package net.azisaba.afnw.afnwcore2.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

/**
 * コマンド "lobby" の実装レコードです。
 *
 * @see org.bukkit.command.CommandExecutor
 * @author m2en
 * @param plugin AfnwCore2
 */
public record LobbyCommand(JavaPlugin plugin) implements CommandExecutor {

  /**
   * /lobby - sender teleport to lobby
   * @param sender Source of the command
   * @param command Command which was executed
   * @param label Alias of the command which was used
   * @param args Passed command arguments
   * @return command boolean
   */
  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    if(!(command.getName().equals("lobby"))) {
      return false;
    }

    if (!(sender instanceof Player p)) {
      sender.sendMessage(Component.text("/lobbyコマンドはプレイヤーのみ実行可能です。", NamedTextColor.RED));
      return false;
    }

    if(!(sender.hasPermission("afnw.command.lobby"))) {
      return false;
    }

    FileConfiguration config = plugin.getConfig();
    World lobby = Bukkit.getWorld(config.getString("tp.lobby_world_name", "lobby"));
    int standby = config.getInt("tp.standby", 10);
    if(lobby == null) {
      throw new NullPointerException("Lobby World could not be found");
    }

    if(p.getWorld() == lobby) {
      sender.sendMessage(Component.text("既にロビーにいるため、テレポートできません。", NamedTextColor.RED));
      return false;
    } else if(p.hasPermission("afnw.bypass.standby")) {
      p.teleport(lobby.getSpawnLocation());
      sender.sendMessage(Component.text("ロビーへテレポートしました。(Admin/Mod権限をもっているため、待機時間は発生しません。)", NamedTextColor.GREEN));
      return true;
    }

    p.sendMessage(Component.text(standby + "秒後、ロビーへテレポートします....", NamedTextColor.AQUA));
    new BukkitRunnable() {
      @Override
      public void run() {
        p.teleport(lobby.getSpawnLocation());
        p.sendMessage(Component.text("ロビーへテレポートしました。", NamedTextColor.YELLOW));
      }
    }.runTaskLater(plugin, 20L * standby);

    return true;
  }

}
