@file:JvmName("AfnwCore2")

package net.azisaba.afnw.afnwcore2

import net.azisaba.afnw.afnwcore2.command.AfnwCommand
import net.azisaba.afnw.afnwcore2.command.TicketCommand
import net.azisaba.afnw.afnwcore2.listener.JoinListener
import net.azisaba.afnw.afnwcore2.listener.QuitListener
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
        getCommand("afnw")?.setExecutor(AfnwCommand(this))
        getCommand("ticket")?.setExecutor(TicketCommand(this, data))

        logger.info(name + "が正常に有効化されました")
    }

    override fun onDisable() {
        logger.info(name + "が正常に無効化されました")
    }

}
