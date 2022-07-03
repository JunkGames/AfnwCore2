package net.azisaba.afnw.afnwcore2.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public record TrashCommand(JavaPlugin plugin) implements CommandExecutor {

  @Override
  public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String s, String[] strings) {
    if (!(command.getName().equals("trash"))) {
      return false;
    }

    if (!(sender instanceof Player p)) {
      sender.sendMessage(Component.text("/trashコマンドはプレイヤーのみ実行可能です。", NamedTextColor.RED));
      return false;
    }

    if (!(sender.hasPermission("afnw.command.trash"))) {
      return false;
    }

    FileConfiguration config = plugin.getConfig();
    String trashName = config.getString("trash.name", "ゴミ箱");
    int trashSize = config.getInt("trash.size", 54);
    if(trashSize % 9 == 0) {
      config.set("trash.size", 54);
    }

    trashGUI(p, trashSize, trashName);
    return true;
  }

  public void trashGUI(Player p, int size, String name) {
    p.openInventory(Bukkit.createInventory(null, size, Component.text(name, NamedTextColor.DARK_PURPLE)));
  }
}
