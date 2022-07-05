package net.azisaba.afnw.afnwcore2;

import net.azisaba.afnw.afnwcore2.commands.*;
import net.azisaba.afnw.afnwcore2.listeners.player.AFKListener;
import net.azisaba.afnw.afnwcore2.listeners.player.DeathListener;
import net.azisaba.afnw.afnwcore2.listeners.player.FirstPlayerJoinListener;
import net.azisaba.afnw.afnwcore2.listeners.player.JoinListener;
import net.azisaba.afnw.afnwcore2.listeners.player.QuitListener;
import net.azisaba.afnw.afnwcore2.listeners.block.CropsBreakCanceller;
import net.azisaba.afnw.afnwcore2.listeners.block.SaplingBreakCanceller;
import net.azisaba.afnw.afnwcore2.util.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.block.data.type.Bed;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class AfnwCore2 extends JavaPlugin {

    @Override
    public void onEnable() {
        // config.yml
        saveDefaultConfig();

        // player-data.yml
        PlayerData data = new PlayerData(this, "player-data.yml");
        data.saveDefaultPlayerData();

        // register listeners
        PluginManager pluginEvent = Bukkit.getPluginManager();
        /* listeners - player */
        pluginEvent.registerEvents(new JoinListener(data), this);
        pluginEvent.registerEvents(new QuitListener(), this);
        pluginEvent.registerEvents(new DeathListener(), this);
        pluginEvent.registerEvents(new FirstPlayerJoinListener(this, data), this);
        pluginEvent.registerEvents(new AFKListener(this), this);
        /* listeners - block */
        pluginEvent.registerEvents(new CropsBreakCanceller(), this);
        pluginEvent.registerEvents(new SaplingBreakCanceller(), this);

        // register commands
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

        getLogger().info("[AfnwCore2] Enabled!");
    }

    @Override
    public void onDisable() { getLogger().info("[AfnwCore2] Disabled!"); }
}
