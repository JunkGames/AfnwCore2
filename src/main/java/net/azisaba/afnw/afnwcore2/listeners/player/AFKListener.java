package net.azisaba.afnw.afnwcore2.listeners.player;

import net.ess3.api.events.AfkStatusChangeEvent;
import net.ess3.api.events.AfkStatusChangeEvent.Cause;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * プレイヤーがAFK状態になったら自動でAFKポイントにTPするクラス
 *
 * @param plugin AfnwCore2
 * @author m2en
 * @see org.bukkit.event.Listener
 */
public record AFKListener(JavaPlugin plugin) implements Listener {

  /**
   * AFK状態になったら自動でAFKポイントにTPするイベント
   *
   * @param e net.ess3.api.events.AfkStatusChangeEvent
   */
  @EventHandler(priority = EventPriority.HIGHEST)
  public void onAFK(AfkStatusChangeEvent e) {
    Player p = e.getAffected().getBase();
    if (p.hasPermission("afnw.bypass.afk")) {
      return;
    }

    // ワールドの特定、AFKポイントの設定
    FileConfiguration config = plugin.getConfig();
    World afkWorld = Bukkit.getWorld(config.getString("afk.afk_world_name", "afk"));
    if (afkWorld == null) {
      throw new NullPointerException("Lobby World could not be found");
    }
    int x = config.getInt("afk.afk_point_x", 0);
    int y = config.getInt("afk.afk_point_y", 0);
    int z = config.getInt("afk.afk_point_z", 0);

    y++;
    Location afkPoint = new Location(afkWorld, x, y, z);

    // AFKポイントにTPする
    if (e.getCause() == Cause.MOVE || e.getCause() == Cause.QUIT) {
      return;
    } else if (e.getCause() == Cause.ACTIVITY) {
      p.teleport(afkPoint);
    } else if (e.getCause() == Cause.COMMAND) {
      int standby = config.getInt("tp.standby", 10);

      p.sendMessage(Component.text("コマンドのAFKのため、待機時間が発動します。(" + standby + "秒後)", NamedTextColor.AQUA));
      new BukkitRunnable() {
        @Override
        public void run() {
          p.teleport(afkPoint);
          p.sendMessage(Component.text("AFKモード(コマンド)になったため、AFKポイントに退避しました。", NamedTextColor.GREEN));
        }
      }.runTaskLater(plugin, 20L * standby);
    } else {
      p.teleport(afkPoint);
      p.sendMessage(Component.text("AFKモードになったため、AFKポイントに退避しました。", NamedTextColor.GREEN));
    }
  }

}
