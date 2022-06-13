package net.azisaba.afnw.afnwcore2.util.data;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

public class PlayerData {

    private FileConfiguration playerData = null;
    private final File dataFile;
    private final String file;
    private final Plugin plugin;

    public PlayerData(Plugin plugin) {
        this(plugin, "player-data.yml");
    }

    public PlayerData(Plugin plugin, String fileName) {
        this.plugin = plugin;
        this.file = fileName;
        dataFile = new File(plugin.getDataFolder(), file);
    }

    public void saveDefaultPlayerData() {
        if(!dataFile.exists()) {
            plugin.saveResource(file, false);
        }
    }

    public void reloadPlayerData() {
        YamlConfiguration data = YamlConfiguration.loadConfiguration(dataFile);
        final InputStream defaultsPlayerData = plugin.getResource(file);

        if(defaultsPlayerData == null) {
            return;
        }

        data.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defaultsPlayerData, StandardCharsets.UTF_8)));
    }

    public FileConfiguration getPlayerData() {
        if(playerData == null) {
            reloadPlayerData();
        }

        return playerData;
    }

    public void savePlayerData() {
        if(playerData == null) {
            return;
        }

        try {
            getPlayerData().save(dataFile);
        } catch (IOException exception) {
            plugin.getLogger().log(Level.CONFIG, "プレイヤーデータの保存に失敗しました: " + playerData, exception);
        }
    }
}
