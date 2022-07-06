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
 * コマンド "tutorial" の実装レコードです。
 * @param plugin
 */
public record TutorialCommand(JavaPlugin plugin) implements CommandExecutor {

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    if (!(command.getName().equals("tutorial"))) {
      return false;
    }

    if (!(sender instanceof Player p)) {
      sender.sendMessage(Component.text("/tutorialコマンドはプレイヤーのみ実行可能です。", NamedTextColor.RED));
      return false;
    }

    if (!(sender.hasPermission("afnw.command.tutorial"))) {
      return false;
    }

    FileConfiguration config = plugin.getConfig();
    World tutorial = Bukkit.getWorld(config.getString("tp.tutorial_world_name", "tutorial"));
    int standby = config.getInt("tp.standby", 10);
    if (tutorial == null) {
      throw new NullPointerException("Tutorial World could not be found");
    }

    if (p.getWorld() == tutorial) {
      sender.sendMessage(Component.text("既にチュートリアルにいるため、テレポートできません。", NamedTextColor.RED));
      return false;
    } else if (p.hasPermission("afnw.bypass.standby")) {
      p.teleport(tutorial.getSpawnLocation());
      sender.sendMessage(Component.text("チュートリアルへテレポートしました。(Admin/Mod権限をもっているため、待機時間は発生しません。)",
          NamedTextColor.GREEN));
      return true;
    }

    p.sendMessage(Component.text(standby + "秒後、チュートリアルへテレポートします....", NamedTextColor.AQUA));
    new BukkitRunnable() {
      @Override
      public void run() {
        p.teleport(tutorial.getSpawnLocation());
        p.sendMessage(Component.text("チュートリアルへテレポートしました。", NamedTextColor.YELLOW));
      }
    }.runTaskLater(plugin, 20L * standby);

    return true;
  }
}
