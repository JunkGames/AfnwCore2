@file:JvmName("AfnwCore2")

package net.azisaba.afnw.afnwcore2

import net.azisaba.afnw.afnwcore2.command.afnwticket.AfnwCommand
import net.azisaba.afnw.afnwcore2.command.afnwticket.TicketCommand
import net.azisaba.afnw.afnwcore2.listener.block.CropsBreakCanceller
import net.azisaba.afnw.afnwcore2.listener.block.SaplingBreakCanceller
import net.azisaba.afnw.afnwcore2.listener.player.AfkListener
import net.azisaba.afnw.afnwcore2.listener.player.JoinListener
import net.azisaba.afnw.afnwcore2.listener.player.QuitListener
import net.azisaba.afnw.afnwcore2.listener.player.RespawnListener
import net.azisaba.afnw.afnwcore2.utils.data.PlayerData
import org.bukkit.Bukkit
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin

class AfnwCore2 : JavaPlugin() {

    override fun onEnable() {
        saveDefaultConfig()

        val data = PlayerData(this, "player-data.yml")
        data.saveDefaultPlayerData()

        val pluginManager: PluginManager = Bukkit.getPluginManager()
        pluginManager.registerEvents(JoinListener(), this)
        pluginManager.registerEvents(QuitListener(), this)
        pluginManager.registerEvents(RespawnListener(this), this)
        pluginManager.registerEvents(SaplingBreakCanceller(), this)
        pluginManager.registerEvents(CropsBreakCanceller(), this)
        pluginManager.registerEvents(AfkListener(this), this)
        getCommand("afnw")?.setExecutor(AfnwCommand(this))
        getCommand("ticket")?.setExecutor(TicketCommand(this, data))

        logger.info(name + "が正常に有効化されました")
    }

    override fun onDisable() {
        logger.info(name + "が正常に無効化されました")
    }

}
