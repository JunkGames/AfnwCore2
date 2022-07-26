package net.azisaba.afnw.afnwcore2.utils.data

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.logging.Level.SEVERE

class PlayerData(private val plugin: Plugin, private val fileName: String) {

    private val dataFile: File = File(plugin.dataFolder, fileName)
    private var playerData: FileConfiguration? = null

    fun saveDefaultPlayerData() {
        if(!dataFile.exists()) {
            try {
                dataFile.createNewFile()
            } catch (error: IOException) {
                plugin.logger.log(SEVERE, "プレイヤーデータファイルを作成できませんでした: ", error)
            }
        }
    }

    fun getPlayerData(): FileConfiguration? {
        if(playerData == null) {
            reloadPlayerData()
        }
        return playerData
    }

    private fun reloadPlayerData() {
        var playerData = YamlConfiguration.loadConfiguration(dataFile)
        val defPlayerData = plugin.getResource(fileName) ?: return
        playerData.setDefaults(
            YamlConfiguration.loadConfiguration(
                InputStreamReader(defPlayerData, StandardCharsets.UTF_8)
            )
        )
    }

    fun savePlayerData() {
        if (playerData == null) {
            return
        }
        try {
            getPlayerData()!!.save(dataFile)
        } catch (exception: IOException) {
            plugin.logger.log(java.util.logging.Level.CONFIG, "プレイヤーデータの保存に失敗しました: $playerData", exception)
        }
    }
}
