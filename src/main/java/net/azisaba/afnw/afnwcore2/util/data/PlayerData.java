package net.azisaba.afnw.afnwcore2.util.data;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

/**
 * プレイヤーデータのセーブとロードを行うクラスです。
 *
 * @author m2en
 */
public class PlayerData {

  private final File dataFile;
  private final String file;
  private final Plugin plugin;
  private FileConfiguration playerData = null;

  /**
   * データファイル名等を格納します。
   *
   * @param plugin   メインクラス
   * @param fileName プレイヤーデータのファイル名
   */
  public PlayerData(Plugin plugin, String fileName) {
    this.plugin = plugin;
    this.file = fileName;
    dataFile = new File(plugin.getDataFolder(), file);
  }

  /**
   * プレイヤーデータの設定を行います。 * プレイヤーデータが存在しない場合は plugins/AfnwCore2 配下にデータファイルを作成します。
   */
  public void saveDefaultPlayerData() {
    if (!dataFile.exists()) {
      try {
        dataFile.createNewFile();
      } catch (IOException e) {
        plugin.getLogger().log(Level.SEVERE, "Could not create player data file.", e);
      }
    }
  }

  /**
   * プレイヤーデータをロードします。
   *
   * @return プレイヤーデータ(FileConfigurationとしてロードされたものを返します)
   */
  public FileConfiguration getPlayerData() {
    if (playerData == null) {
      reloadPlayerData();
    }

    return playerData;
  }

  /**
   * プレイヤーデータを再読込します。
   */
  public void reloadPlayerData() {
    playerData = YamlConfiguration.loadConfiguration(dataFile);
    final InputStream defaultsPlayerData = plugin.getResource(file);

    if (defaultsPlayerData == null) {
      return;
    }

    playerData.setDefaults(YamlConfiguration.loadConfiguration(
        new InputStreamReader(defaultsPlayerData, StandardCharsets.UTF_8)));
  }

  /**
   * プレイヤーデータをセーブします。
   */
  public void savePlayerData() {
    if (playerData == null) {
      return;
    }

    try {
      getPlayerData().save(dataFile);
    } catch (IOException exception) {
      plugin.getLogger().log(Level.CONFIG, "プレイヤーデータの保存に失敗しました: " + playerData, exception);
    }
  }
}
