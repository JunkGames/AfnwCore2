package net.azisaba.afnw.afnwcore2.commands;

import java.io.File;
import javax.naming.Name;
import net.azisaba.afnw.afnwcore2.util.data.PlayerData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public record BonusCommand(JavaPlugin plugin, PlayerData playerData) implements CommandExecutor {

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    if(!(command.getName().equals("bonus"))) {
      return false;
    }

    if (!(sender instanceof Player player)) {
      sender.sendMessage(
          Component.text("/bonusコマンドはコンソールからのみ実行可能です。チケットの配布は/ticket giveを使用してください。"));
      return false;
    }

    if(!(sender.hasPermission("afnw.command.bonus"))) {
      sender.sendMessage(Component.text("You don't have permission to use this command.", NamedTextColor.RED));
      return false;
    }

    FileConfiguration data = playerData.getPlayerData();
    FileConfiguration config = plugin.getConfig();
    int voteCount = data.getInt(player.getUniqueId().toString());

    player.sendMessage(Component.text("投票ボーナスまで: " + voteCount + "/" + config.getInt("vote.bonus-line", 10), NamedTextColor.YELLOW));
    player.sendMessage(Component.text("注意: 投票ボーナスを受け取ると値はリセットされます。", NamedTextColor.YELLOW));
    return true;
  }
}
