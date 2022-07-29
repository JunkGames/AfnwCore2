package net.azisaba.afnw.afnwcore2.listener.block

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Tag
import org.bukkit.block.data.Ageable
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockFromToEvent

class CropsBreakCanceller: Listener {

    @EventHandler
    fun onBreakCrops(event: BlockBreakEvent) {
        val player = event.player
        val block = event.block

        if(player.hasPermission("bypass.break")) {
            return
        }

        if(!Tag.CROPS.isTagged(block.location.add(0.0, 1.0, 0.0).block.type)) {
            return
        }

        event.isCancelled = true
        player.sendMessage(Component.text("農作物が植えられている耕地を破壊することはできません。").color(NamedTextColor.RED))
    }

    @EventHandler
    fun onBreakNewAgeCrops(event: BlockBreakEvent) {
        val player = event.player
        val block = event.block

        if(player.hasPermission("bypass.break")) {
            return
        }

        if(!Tag.CROPS.isTagged(block.type)) {
            return
        }

        if(block.blockData is Ageable) {
            val age = block.blockData as Ageable
            if(age.maximumAge == age.age) {
                return
            }
        }

        event.isCancelled = true
        player.sendMessage(Component.text("成長しきっていない農作物を破壊することはできません。").color(NamedTextColor.RED))
    }

    @EventHandler
    fun onBreakByWater(event: BlockFromToEvent) {
        val block = event.toBlock
        if(!Tag.CROPS.isTagged(block.type)) {
            return
        }

        if(block.blockData is Ageable) {
            val age = block.blockData as Ageable
            if(age.maximumAge == age.age) {
                return
            }
        }

        event.isCancelled = true
    }
}
