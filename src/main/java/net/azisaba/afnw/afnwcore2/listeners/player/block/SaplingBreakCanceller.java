package net.azisaba.afnw.afnwcore2.listeners.player.block;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;

import java.util.logging.Logger;

/**
 * bypass permission
 * - afnw.bypass.break.sapling
 */
public class SaplingBreakCanceller implements Listener {

    /**
     * 苗木を保護するメソッド
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onBreakSapling(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();

        if(!Tag.SAPLINGS.isTagged(b.getType())) return;
        if(b.getType() == Material.FLOWERING_AZALEA) return;
        if(b.getType() == Material.AZALEA) return;

        if(p.hasPermission("afnw.bypass.break.sapling")) {
            p.sendMessage(Component.text("苗木保護機能をbypassしました。"));
            Logger.getLogger("bypass:sapling").info(p.getName() + "break sapling! (bypass canceller!)");
            return;
        }

        e.setCancelled(true);
        p.sendMessage(Component.text("一度設置した苗木は成長するまで破壊することはできません。破壊したい場合はサーバー内にいる運営に連絡してください。").color(NamedTextColor.RED));
    }

    /**
     * 苗木が埋まっている土を保護するメソッド
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onBreakCrops(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();

        if(!Tag.CROPS.isTagged(b.getLocation().add(0, 1, 0).getBlock().getType())) return;

        e.setCancelled(true);
        p.sendMessage(Component.text("苗木の下にある耕地は苗木が育ち切るまで破壊することはできません。").color(NamedTextColor.RED));
    }

    /**
     * 苗木に対する水流をブロックする阻害するメソッド
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onBreakByWater(BlockFromToEvent e) {
        Block b = e.getToBlock();

        if(!Tag.SAPLINGS.isTagged(b.getType())) return;
        if(b.getType() == Material.FLOWERING_AZALEA) return;
        if(b.getType() == Material.AZALEA) return;

        e.setCancelled(true);
    }
}
