package net.azisaba.afnw.afnwcore2.utils.data

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.logging.Level

class PlayerData(private val plugin: Plugin, private val fileName: String) {

    private val dataFile: File = File(plugin.dataFolder, fileName)
    private var playerData: FileConfiguration? = null

    fun saveDefaultPlayerData() {
        if(!(dataFile.exists())) {
            try {
                dataFile.createNewFile()
            } catch (e: IOException) {
                plugin.logger.log(Level.SEVERE, "Failed to create player data file.", e)
            }
        }
    }

    fun getPlayerData(): FileConfiguration? {
        if (playerData == null) {
            reloadPlayerData()
        }
        return playerData
    }

    private fun reloadPlayerData() {
        playerData = YamlConfiguration.loadConfiguration(dataFile)
        val defaultPlayerData = plugin.getResource(fileName) ?: return
        (playerData as YamlConfiguration).setDefaults(
            YamlConfiguration.loadConfiguration(InputStreamReader(defaultPlayerData, StandardCharsets.UTF_8))
        )
    }

    fun savePlayerData() {
        if (playerData == null) {
            return
        }
        try {
            getPlayerData()!!.save(dataFile)
        } catch (exception: IOException) {
            plugin.logger.log(Level.CONFIG, "プレイヤーデータの保存に失敗しました: $playerData", exception)
        }
    }
}
