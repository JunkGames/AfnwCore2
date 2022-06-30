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

public record LobbyCommand(JavaPlugin plugin) implements CommandExecutor {

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    if(!(command.getName().equals("lobby"))) {
      return true;
    }

    if (!(sender instanceof Player p)) {
      sender.sendMessage(Component.text("/lobbyコマンドはプレイヤーのみ実行可能です。", NamedTextColor.RED));
      return false;
    }

    /**
    if(!(sender.hasPermission("afnw.command.lobby"))) {
      return true;
    }
    */

    FileConfiguration config = plugin.getConfig();
    World lobby = Bukkit.getWorld(config.getString("tp.lobby_world_name", "lobby"));
    int standby = config.getInt("tp.standby", 10);
    if(lobby == null) {
      throw new NullPointerException("Lobby World could not be found");
    }
    if(p.getWorld() == lobby) {
      sender.sendMessage(Component.text("既にロビーにいます。", NamedTextColor.RED));
      return false;
    }

    p.sendMessage(Component.text(standby + "秒後、ロビーへテレポートします....", NamedTextColor.AQUA));
    new BukkitRunnable() {
      @Override
      public void run() {
        p.teleport(lobby.getSpawnLocation());
        p.sendMessage(Component.text("ロビーへテレポートしました。", NamedTextColor.AQUA));
      }
    }.runTaskLater(plugin, 20L * standby);

    return true;
  }

}
