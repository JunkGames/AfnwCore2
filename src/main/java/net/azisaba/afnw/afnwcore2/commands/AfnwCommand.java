package net.azisaba.afnw.afnwcore2.commands;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.azisaba.afnw.afnwcore2.util.data.PlayerData;
import net.azisaba.afnw.afnwcore2.util.item.AfnwScaffold;
import net.azisaba.afnw.afnwcore2.util.item.AfnwTicket;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * コマンド "afnw" の実装レコードです。
 *
 * @author m2en
 * @see org.bukkit.command.CommandExecutor
 */
public record AfnwCommand(JavaPlugin plugin, PlayerData playerData) implements CommandExecutor {

  private static boolean isAllowed(Material type) {
      if (!type.isItem()) {
          return false;
      }
    switch (type) {
      case BEDROCK:
      case STRUCTURE_BLOCK:
      case STRUCTURE_VOID:
      case COMMAND_BLOCK:
      case CHAIN_COMMAND_BLOCK:
      case COMMAND_BLOCK_MINECART:
      case REPEATING_COMMAND_BLOCK:
      case BARRIER:
      case LIGHT:
      case JIGSAW:
      case END_PORTAL:
      case KNOWLEDGE_BOOK:
      case DEBUG_STICK:
      case BUNDLE:
      case AIR:
      case VOID_AIR:
      case CAVE_AIR:
        return false;
      default:
        return true;
    }
  }

  /**
   * /afnw
   *
   * @param sender  Source of the command
   * @param command Command which was executed
   * @param label   Alias of the command which was used
   * @param args    Passed command arguments
   * @return Result of command execution
   */
  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
      @NotNull String label, @NotNull String[] args) {
    if (!(command.getName().equals("afnw"))) {
      return true;
    }
    if (!(sender instanceof Player)) {
      sender.sendMessage(Component.text("/afnwコマンドはプレイヤーのみ実行可能です。").color(NamedTextColor.RED));
      return true;
    }
    if (!(sender.hasPermission("afnw.command.afnw"))) {
      return true;
    }

    Inventory inv = ((Player) sender).getInventory();
    int firstInv = inv.firstEmpty();
    if (firstInv == -1) {
      sender.sendMessage(
          Component.text("インベントリに空きがありません。十分な空きを作ってから交換してください。").color(NamedTextColor.RED));
      return true;
    }
    if (!inv.containsAtLeast(AfnwTicket.afnwTicket, 1)) {
      sender.sendMessage(
          Component.text("チケットが見つかりません。インベントリにチケットがあるか確認してください。").color(NamedTextColor.RED));
      return true;
    }

    FileConfiguration config = plugin.getConfig();
    int itemSize = config.getInt("vote.item-size", 1);
    int scaffoldSize = config.getInt("vote.scaffold-size", 8);

    SecureRandom random;
    ItemStack afnwItem;
    try {
      random = SecureRandom.getInstance("SHA1PRNG");
      List<Material> itemList = new ArrayList<>(Arrays.asList(Material.values()));
      itemList.removeIf(type -> !isAllowed(type));
      afnwItem = new ItemStack(itemList.get(random.nextInt(itemList.size() - 1)), itemSize);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }

    inv.removeItem(AfnwTicket.afnwTicket);
    inv.addItem(afnwItem);
    for (int i = 0; i < scaffoldSize; i++) {
      inv.addItem(AfnwScaffold.afnwScaffold);
    }

    sender.sendMessage(Component.text("アイテムと交換しました。").color(NamedTextColor.GOLD));
    sender.sendMessage(Component.text(
            "交換内容: " + afnwItem.getType() + " ×" + afnwItem.getAmount() + ", 足場ブロック ×" + scaffoldSize)
        .color(NamedTextColor.GOLD));
    return true;
  }
}
