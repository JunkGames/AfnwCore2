package net.azisaba.afnw.afnwcore2.listeners.block;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;

/**
 * Crop Protection Functions
 *
 * @author m2en
 * @see org.bukkit.event.Listener
 */
public class CropsBreakCanceller implements Listener {

  /**
   * Protection of arable land planted with crops
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
    p.sendMessage(Component.text("農作物が植えられている耕地を破壊することはできません。").color(NamedTextColor.RED));
  }

  /**
   * Protecting Undergrown Crops
   *
   * @param e BlockBreakEvent
   */
  @EventHandler(priority = EventPriority.HIGH)
  public void onBreakNewAgeCrops(BlockBreakEvent e) {
    Player p = e.getPlayer();
    Block b = e.getBlock();
      if (!Tag.CROPS.isTagged(b.getType())) {
          return;
      }
    if (p.hasPermission("afnw.bypass.break.newCrops")) {
      p.sendMessage(Component.text("農作物保護機能を回避しました。").color(NamedTextColor.GOLD));
      return;
    }

    if (b.getBlockData() instanceof Ageable crop) {
      if (crop.getMaximumAge() == crop.getAge()) {
        return;
      }
    }

    e.setCancelled(true);
    p.sendMessage(Component.text("成長しきっていない農作物を破壊することはできません。").color(NamedTextColor.RED));
  }

  /**
   * Block water flow against crops
   *
   * @param e BlockFromToEvent
   */
  @EventHandler(priority = EventPriority.HIGH)
  public void onBreakByWater(BlockFromToEvent e) {
    Block b = e.getToBlock();
      if (!Tag.CROPS.isTagged(b.getType())) {
          return;
      }
    if (b.getBlockData() instanceof Ageable crop) {
        if (crop.getMaximumAge() == crop.getAge()) {
            return;
        }
    }
    e.setCancelled(true);
  }
}
