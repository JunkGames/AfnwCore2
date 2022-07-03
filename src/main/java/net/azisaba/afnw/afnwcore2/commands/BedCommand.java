package net.azisaba.afnw.afnwcore2.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BedCommand implements CommandExecutor {

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
      p.sendMessage(Component.text("ロケーションを取得することができませんでした。ベッドで寝て設定してください。", NamedTextColor.RED));
      return false;
    }

    p.teleport(bedLoc);
    return true;
  }

}
