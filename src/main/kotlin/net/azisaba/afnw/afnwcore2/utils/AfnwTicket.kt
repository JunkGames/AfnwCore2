package net.azisaba.afnw.afnwcore2.utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object AfnwTicket {

    @JvmField
    var ticket = ItemStack(Material.AMETHYST_SHARD, 1)
    init {
        val metaData = ticket.itemMeta
        metaData.displayName(Component.text("AfnwTicket", NamedTextColor.LIGHT_PURPLE))
        ticket.itemMeta = metaData
    }
}
