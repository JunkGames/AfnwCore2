package net.azisaba.afnw.afnwcore2.listeners.entity;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public record WitherSpawn(JavaPlugin plugin) implements Listener {

  @EventHandler
  public void onWitherSpawn(CreatureSpawnEvent e) {
    Entity targetEntity = e.getEntity();
    if(!(targetEntity.getType() == EntityType.WITHER)) {
      return;
    }

    FileConfiguration config = plugin.getConfig();
    boolean isWitherSpawn = config.getBoolean("settings.allow-wither-spawn");
    if(!(isWitherSpawn)) {
      Bukkit.broadcast(Component.text("ウィザーの召喚が妨害されたようだ.....", NamedTextColor.DARK_PURPLE));
      e.setCancelled(true);
      return;
    }

    boolean isNotice = config.getBoolean("settings.notification-wither-spawn");
    if(isNotice) {
      String worldName = e.getLocation().getWorld().getName();
      Bukkit.broadcast(Component.text( worldName + "にてウィザーが召喚された....", NamedTextColor.DARK_PURPLE));
    }
  }
}
