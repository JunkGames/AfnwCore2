package net.azisaba.afnw.afnwcore2.listeners.player;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public record RespawnNether(JavaPlugin plugin) implements Listener {

  @EventHandler
  public void onRespawn(PlayerRespawnEvent e) {
    Player p = e.getPlayer();
    if(e.getRespawnLocation().getWorld().getEnvironment() != Environment.NETHER) {
      return;
    }

    Location respawn = p.getBedSpawnLocation();

    if(respawn == null) {
      FileConfiguration config = plugin.getConfig();
      World lobby = Bukkit.getWorld(config.getString("tp.lobby_world_name", "lobby"));
      if(lobby == null) {
        throw new NullPointerException("Lobby World could not be found");
      }
      p.teleport(lobby.getSpawnLocation());
    } else {
      e.setRespawnLocation(respawn);
    }
  }

}
