package net.azisaba.afnw.afnwcore2.listener.player

import net.azisaba.afnw.afnwcore2.AfnwCore2
import net.ess3.api.events.AfkStatusChangeEvent
import net.ess3.api.events.AfkStatusChangeEvent.Cause
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.scheduler.BukkitRunnable


class AfkListener(private val plugin: AfnwCore2): Listener {

    @EventHandler
    fun onAfK(event: AfkStatusChangeEvent) {
        val player = event.affected.base
        if(player.hasPermission("bypass.afk")) {
            return
        }

        val config = plugin.config
        val afkWorld: World =
            Bukkit.getWorld(config.getString("afk.afk_world_name", "afk") ?: return)
                ?: throw NullPointerException("Lobby World could not be found")
        val x = config.getInt("afk.afk_point_x", 0).toDouble()
        var y = config.getInt("afk.afk_point_y", 0).toDouble()
        val z = config.getInt("afk.afk_point_z", 0).toDouble()

        y++
        val afkPoint = Location(afkWorld, x, y, z)

        if (event.cause === Cause.MOVE || event.cause === Cause.QUIT) {
            return
        } else if (event.cause === Cause.ACTIVITY) {
            player.teleport(afkPoint)
        } else if (event.cause === Cause.COMMAND) {
            val standby = config.getInt("tp.standby", 10)
            player.sendMessage(
                Component.text(
                    "コマンドのAFKのため、待機時間が発動します。(" + standby + "秒後)",
                    NamedTextColor.AQUA
                )
            )

            object : BukkitRunnable() {
                override fun run() {
                    player.teleport(afkPoint)
                    player.sendMessage(
                        Component.text(
                            "AFKモード(コマンド)になったため、AFKポイントに退避しました。",
                            NamedTextColor.GREEN
                        )
                    )
                }
            }.runTaskLater(plugin, 20L * standby)
        } else {
            player.teleport(afkPoint)
            player.sendMessage(Component.text("AFKモードになったため、AFKポイントに退避しました。", NamedTextColor.GREEN))
        }
    }
}
