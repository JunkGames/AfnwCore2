package net.azisaba.afnw.afnwcore2;

import java.util.Objects;
import net.azisaba.afnw.afnwcore2.commands.AfnwCommand;
import net.azisaba.afnw.afnwcore2.commands.BedCommand;
import net.azisaba.afnw.afnwcore2.commands.ConfigReloadCommand;
import net.azisaba.afnw.afnwcore2.commands.EnderchestCommand;
import net.azisaba.afnw.afnwcore2.commands.LobbyCommand;
import net.azisaba.afnw.afnwcore2.commands.RespawnCommand;
import net.azisaba.afnw.afnwcore2.commands.TicketCommand;
import net.azisaba.afnw.afnwcore2.commands.TrashCommand;
import net.azisaba.afnw.afnwcore2.commands.TutorialCommand;
import net.azisaba.afnw.afnwcore2.commands.VoidCommand;
import net.azisaba.afnw.afnwcore2.commands.VoteCommand;
import net.azisaba.afnw.afnwcore2.listeners.block.CropsBreakCanceller;
import net.azisaba.afnw.afnwcore2.listeners.block.SaplingBreakCanceller;
import net.azisaba.afnw.afnwcore2.listeners.entity.WitherSpawn;
import net.azisaba.afnw.afnwcore2.listeners.player.AFKListener;
import net.azisaba.afnw.afnwcore2.listeners.player.DeathListener;
import net.azisaba.afnw.afnwcore2.listeners.player.FirstPlayerJoinListener;
import net.azisaba.afnw.afnwcore2.listeners.player.JoinListener;
import net.azisaba.afnw.afnwcore2.listeners.player.QuitListener;
import net.azisaba.afnw.afnwcore2.util.data.PlayerData;
import net.azisaba.afnw.afnwcore2.util.data.PlayerDataSave;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * AfnwCore2 のメインクラス
 *
 * @author m2en
 * @see org.bukkit.plugin.java.JavaPlugin
 */
public class AfnwCore2 extends JavaPlugin {

  @Override
  public void onEnable() {
    getLogger().info("起動開始...");

    // コンフィグのロード
    saveDefaultConfig();
    getLogger().info("コンフィグ ロード完了");

    // プレイヤーデータのロード
    PlayerData data = new PlayerData(this, "player-data.yml");
    data.saveDefaultPlayerData();
    getLogger().info("プレイヤーデータ ロード完了");

    // 自動保存の設定
    PlayerDataSave dataSchedule = new PlayerDataSave(this, data);
    dataSchedule.playerData();
    getLogger().info("プレイヤーデータ自動保存 設定完了");

    // Listenerの確定
    PluginManager pluginEvent = Bukkit.getPluginManager();
    getLogger().info("Listener 設定中....");
    /* プレイヤーリスナー */
    pluginEvent.registerEvents(new JoinListener(this, data), this);
    pluginEvent.registerEvents(new QuitListener(), this);
    pluginEvent.registerEvents(new DeathListener(), this);
    pluginEvent.registerEvents(new FirstPlayerJoinListener(this, data), this);
    pluginEvent.registerEvents(new AFKListener(this), this);
    /* エンティティリスナー */
    pluginEvent.registerEvents(new WitherSpawn(this), this);
    getLogger().info("Listener 設定完了");
    /* ブロックリスナー */
    pluginEvent.registerEvents(new CropsBreakCanceller(), this);
    pluginEvent.registerEvents(new SaplingBreakCanceller(), this);
    getLogger().info("Listener(Canceller) 設定完了");

    // コマンドの設定
    Objects.requireNonNull(getCommand("afnw")).setExecutor(new AfnwCommand(this, data));
    Objects.requireNonNull(getCommand("vote")).setExecutor(new VoteCommand());
    Objects.requireNonNull(getCommand("respawn")).setExecutor(new RespawnCommand());
    Objects.requireNonNull(getCommand("config_reload")).setExecutor(new ConfigReloadCommand(this));
    Objects.requireNonNull(getCommand("ticket")).setExecutor(new TicketCommand(this, data));
    Objects.requireNonNull(getCommand("lobby")).setExecutor(new LobbyCommand(this));
    Objects.requireNonNull(getCommand("void")).setExecutor(new VoidCommand(this));
    Objects.requireNonNull(getCommand("tutorial")).setExecutor(new TutorialCommand(this));
    Objects.requireNonNull(getCommand("bed")).setExecutor(new BedCommand(this));
    Objects.requireNonNull(getCommand("pc")).setExecutor(new EnderchestCommand());
    Objects.requireNonNull(getCommand("trash")).setExecutor(new TrashCommand(this));
    getLogger().info("コマンド 設定完了");

    getLogger().info("正常に起動しました。");
  }

  @Override
  public void onDisable() {
    getLogger().info("正常に終了しました。");
  }
}
