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

/**
 * 投票回数などを保存するプレイヤーデータクラスです。
 *
 * @author m2en
 */
public class PlayerData {

    private FileConfiguration playerData = null;
    private final File dataFile;
    private final String file;
    private final Plugin plugin;

    /**
     * プレイヤーデータを定義します。
     * Paperサーバーでは player-data.yml として作成されます。
     * @param plugin メインクラス引数
     */
    public PlayerData(Plugin plugin) {
        this(plugin, "player-data.yml");
    }

    /**
     * プレイヤーデータをplugins/AfnwCore2配下に設置します。
     *
     * @param plugin メインクラス引数
     * @param fileName プレイヤーデータのファイル名
     */
    public PlayerData(Plugin plugin, String fileName) {
        this.plugin = plugin;
        this.file = fileName;
        dataFile = new File(plugin.getDataFolder(), file);
    }

    /**
     * プレイヤーデータが存在しない場合、ファイルを再生成します。
     * 本番環境時、公開中にプレイヤーデータが存在しない場合はバックアップサーバーから復活させます。
     */
    public void saveDefaultPlayerData() {
        if(!dataFile.exists()) {
            plugin.saveResource(file, false);
        }
    }

    /**
     * プレイヤーデータの読み込みを行います。
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
     * プレイヤーデータを取得します。
     * @return プレイヤーデータを返します。存在しない場合は再リロードを行います。
     */
    public FileConfiguration getPlayerData() {
        if(playerData == null) {
            reloadPlayerData();
        }

        return playerData;
    }

    /**
     * プレイヤーデータを保存します。
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
