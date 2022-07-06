package net.azisaba.afnw.afnwcore2.listeners.block;

import java.util.logging.Logger;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;

/**
 * Ability to protect saplings
 *
 * @author m2en
 * @see org.bukkit.event.Listener
 */
public class SaplingBreakCanceller implements Listener {

  /**
   * Protect saplings
   *
   * @param e BlockBreakEvent
   */
  @EventHandler(priority = EventPriority.HIGH)
  public void onBreakSapling(BlockBreakEvent e) {
    Player p = e.getPlayer();
    Block b = e.getBlock();

      if (!Tag.SAPLINGS.isTagged(b.getType())) {
          return;
      }
      if (b.getType() == Material.FLOWERING_AZALEA) {
          return;
      }
      if (b.getType() == Material.AZALEA) {
          return;
      }

    if (p.hasPermission("afnw.bypass.break.sapling")) {
      p.sendMessage(Component.text("苗木保護機能を回避しました。").color(NamedTextColor.GOLD));
      Logger.getLogger("bypass:sapling").info(p.getName() + "break sapling! (bypass canceller!)");
      return;
    }

    e.setCancelled(true);
    p.sendMessage(Component.text("一度設置した苗木は成長するまで破壊することはできません。破壊したい場合はサーバー内にいる運営に連絡してください。")
        .color(NamedTextColor.RED));
  }

  /**
   * Protect the soil in which the saplings are buried
   *
   * @param e BlockBreakEvent
   */
  @EventHandler(priority = EventPriority.HIGH)
  public void onBreakCrops(BlockBreakEvent e) {
    Player p = e.getPlayer();
    Block b = e.getBlock();

      if (!Tag.CROPS.isTagged(b.getLocation().add(0, 1, 0).getBlock().getType())) {
          return;
      }
    if (p.hasPermission("afnw.bypass.break.arable")) {
      p.sendMessage(Component.text("耕地保護機能を回避しました。").color(NamedTextColor.GOLD));
      return;
    }

    e.setCancelled(true);
    p.sendMessage(Component.text("苗木の下にある耕地は苗木が育ち切るまで破壊することはできません。").color(NamedTextColor.RED));
  }

  /**
   * Block water flow against saplings
   *
   * @param e BlockBreakEvent
   */
  @EventHandler(priority = EventPriority.HIGH)
  public void onBreakByWater(BlockFromToEvent e) {
    Block b = e.getToBlock();

      if (!Tag.SAPLINGS.isTagged(b.getType())) {
          return;
      }
      if (b.getType() == Material.FLOWERING_AZALEA) {
          return;
      }
      if (b.getType() == Material.AZALEA) {
          return;
      }

    e.setCancelled(true);
  }
}
