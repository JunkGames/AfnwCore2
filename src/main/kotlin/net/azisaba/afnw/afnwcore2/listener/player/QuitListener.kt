package net.azisaba.afnw.afnwcore2.listener.player

import com.earth2me.essentials.Essentials
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class QuitListener: Listener {

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        val player: Player = event.player

        if(isVanished(player)) {
            return
        }

        event.quitMessage(Component.text("* " + player.name + " がログアウトしました", NamedTextColor.AQUA))
    }

    private fun isVanished(player: Player): Boolean {
        val essentials = Bukkit.getPluginManager().getPlugin("Essentials") as Essentials?
            ?: return false
        return essentials.getUser(player).isVanished
    }
}
