package net.azisaba.afnw.afnwcore2.util.data;

import lombok.Data;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

/**
 * Player data class to store vote counts, etc.
 *
 * @author m2en
 */
@Data
public class PlayerData {

    private FileConfiguration playerData = null;
    private final File dataFile;
    private final String file;
    private final Plugin plugin;

    /**
     * Define player data.
     * On the Paper server, it is created as player-data.yml.
     * @param plugin Main Class Arguments
     */
    public PlayerData(Plugin plugin) {
        this(plugin, "player-data.yml");
    }

    /**
     * Place player data under plugins/AfnwCore2.
     *
     * @param plugin Main Class Arguments
     * @param fileName Player data file name
     */
    public PlayerData(Plugin plugin, String fileName) {
        this.plugin = plugin;
        this.file = fileName;
        dataFile = new File(plugin.getDataFolder(), file);
    }

    /**
     * If player data does not exist, the file is regenerated.
     * If player data does not exist in the production environment or while the system is open to the public, it will be restored from the backup server.
     */
    public void saveDefaultPlayerData() {
        if(!dataFile.exists()) {
            plugin.saveResource(file, false);
        }
    }

    /**
     * Loads player data.
     */
    public void reloadPlayerData() {
        YamlConfiguration data = YamlConfiguration.loadConfiguration(dataFile);
        final InputStream defaultsPlayerData = plugin.getResource(file);

        if(defaultsPlayerData == null) {
            return;
        }

        data.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defaultsPlayerData, StandardCharsets.UTF_8)));
    }

    /**
     * Retrieve player data.
     * @return Returns player data. If not present, reloads.
     */
    public FileConfiguration getPlayerData() {
        if(playerData == null) {
            reloadPlayerData();
        }

        return playerData;
    }

    /**
     * Save player data.
     */
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
