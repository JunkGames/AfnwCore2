package net.azisaba.afnw.afnwcore2.listener.block

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.Tag
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockFromToEvent

class SaplingBreakCanceller: Listener {


    @EventHandler
    fun onBreakSapling(event: BlockBreakEvent) {
        val player = event.player
        val block = event.block
        if (!Tag.SAPLINGS.isTagged(block.type)) {
            return
        }
        if (block.type == Material.FLOWERING_AZALEA) {
            return
        }
        if (block.type == Material.AZALEA) {
            return
        }
        if (player.hasPermission("bypass.break")) {
            return
        }
        event.isCancelled = true
        player.sendMessage(Component.text("一度設置した苗木は成長するまで破壊することはできません。破壊したい場合はサーバー内にいる運営に連絡してください。").color(NamedTextColor.RED))
    }

    @EventHandler
    fun onBreakCrops(e: BlockBreakEvent) {
        val p = e.player
        val b = e.block
        if (!Tag.SAPLINGS.isTagged(b.location.add(0.0, 1.0, 0.0).block.type)) {
            return
        }
        if (p.hasPermission("bypass.break")) {
            return
        }
        e.isCancelled = true
        p.sendMessage(Component.text("苗木の下にある耕地は苗木が育ち切るまで破壊することはできません。").color(NamedTextColor.RED))
    }

    @EventHandler(priority = EventPriority.HIGH)
    fun onBreakByWater(e: BlockFromToEvent) {
        val b = e.toBlock
        if (!Tag.SAPLINGS.isTagged(b.type)) {
            return
        }
        if (b.type == Material.FLOWERING_AZALEA) {
            return
        }
        if (b.type == Material.AZALEA) {
            return
        }
        e.isCancelled = true
    }
}
