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
 * Teleport Void(Afnw_World) Command
 *
 * @param plugin AfnwCore2
 */
public record VoidCommand(JavaPlugin plugin) implements CommandExecutor {

  /**
   * /void - sender teleport to lobby
   *
   * @param sender  Source of the command
   * @param command Command which was executed
   * @param label   Alias of the command which was used
   * @param args    Passed command arguments
   * @return command boolean
   */
  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
      @NotNull String label, @NotNull String[] args) {
    if (!(command.getName().equals("void"))) {
      return false;
    }

    if (!(sender instanceof Player p)) {
      sender.sendMessage(Component.text("/voidコマンドはプレイヤーのみ実行可能です。", NamedTextColor.RED));
      return false;
    }

    if (!(sender.hasPermission("afnw.command.void"))) {
      return false;
    }

    FileConfiguration config = plugin.getConfig();
    World main = Bukkit.getWorld(config.getString("tp.void_world_name", "afnw"));
    int standby = config.getInt("tp.standby", 10);
    if (main == null) {
      throw new NullPointerException("Void World could not be found");
    }
    if (p.getWorld() == main) {
      sender.sendMessage(Component.text("既にメインワールドにいるため、テレポートできません。", NamedTextColor.RED));
      return false;
    } else if (p.hasPermission("afnw.bypass.standby")) {
      p.teleport(main.getSpawnLocation());
      sender.sendMessage(Component.text("メインワールドへテレポートしました。(Admin/Mod権限をもっているため、待機時間は発生しません。)",
          NamedTextColor.GREEN));
      return true;
    }

    p.sendMessage(Component.text(standby + "秒後、メインワールドへテレポートします....", NamedTextColor.AQUA));
    new BukkitRunnable() {
      @Override
      public void run() {
        p.teleport(main.getSpawnLocation());
        p.sendMessage(Component.text("メインワールドへテレポートしました。", NamedTextColor.YELLOW));
      }
    }.runTaskLater(plugin, 20L * standby);

    return true;
  }
}
