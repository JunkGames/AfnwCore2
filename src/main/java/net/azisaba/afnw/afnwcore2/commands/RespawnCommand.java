package net.azisaba.afnw.afnwcore2.commands;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * /respawn - Forced respawning of players.
 *
 * @author m2en
 * @see org.bukkit.command.CommandExecutor
 */
public class RespawnCommand implements TabExecutor {

  /**
   * Tab補完生成の補助メソッド
   */
  private static @NotNull List<String> filter(Stream<String> stream, String s) {
    return stream.filter(s1 -> s1.toLowerCase(Locale.ROOT).startsWith(s.toLowerCase(Locale.ROOT)))
        .collect(Collectors.toList());
  }

  /**
   * /respawn
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
    if (!(command.getName().equals("respawn"))) {
      return true;
    }
    if (!(sender.hasPermission("afnw.command.respawn"))) {
      sender.sendMessage(Component.text("You don't have permission to use this command.", NamedTextColor.RED));
      return true;
    }
    if (args.length == 0) {
      sender.sendMessage(Component.text("Usage: /respawn <player>", NamedTextColor.RED));
      return true;
    }

    Player target = Bukkit.getPlayer(args[0]);
    if (target == null) {
      sender.sendMessage(Component.text("プレイヤーを見つけることができませんでした。", NamedTextColor.RED));
      return true;
    }

    target.spigot().respawn();
    sender.sendMessage(
        Component.text(target.getName() + "を強制的にリスポーンさせました。", NamedTextColor.YELLOW));

    return true;
  }

  /**
   * オンラインプレイヤーのTab補完リストを生成するメソッド
   *
   * @param sender  Source of the command.  For players tab-completing a command inside of a command
   *                block, this will be the player, not the command block.
   * @param command Command which was executed
   * @param alias   The alias used
   * @param args    The arguments passed to the command, including final partial argument to be
   *                completed and command label
   * @return オンラインプレイヤーのリストを返す
   */
  @Override
  public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
      @NotNull String alias, @NotNull String[] args) {
    if (args.length == 0) {
      return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
    }
    if (args.length == 1) {
      return filter(Bukkit.getOnlinePlayers().stream().map(Player::getName), args[0]);
    }
    return Collections.emptyList();
  }
}
