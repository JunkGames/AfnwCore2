package net.azisaba.afnw.afnwcore2.listener.player

import net.azisaba.afnw.afnwcore2.AfnwCore2
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent
import java.lang.RuntimeException

class RespawnListener(private val plugin: AfnwCore2): Listener {

    @EventHandler
    fun onRespawn(event: PlayerRespawnEvent) {
        val player = event.player
        val respawnPoint = player.bedLocation

        if(respawnPoint == null) {
            val config = plugin.config
            val lobbyWorld = Bukkit.getWorld(config.getString("tp.lobby_world_name", "lobby") ?: return)
                ?: throw RuntimeException("Lobby world is not found.")

            event.respawnLocation = lobbyWorld.spawnLocation
        } else {
            event.respawnLocation = respawnPoint
        }
    }
}
