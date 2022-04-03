package net.azisaba.afnw.afnwcore2;

import net.azisaba.afnw.afnwcore2.listeners.player.FirstPlayerJoinListener;
import net.azisaba.afnw.afnwcore2.listeners.player.JoinListener;
import net.azisaba.afnw.afnwcore2.listeners.player.QuitListener;
import net.azisaba.afnw.afnwcore2.listeners.block.CropsBreakCanceller;
import net.azisaba.afnw.afnwcore2.listeners.block.SaplingBreakCanceller;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AfnwCore2 extends JavaPlugin {

    @Override
    public void onEnable() {
        // register listeners
        PluginManager pluginEvent = Bukkit.getPluginManager();
        /* listeners - player */
        pluginEvent.registerEvents(new JoinListener(), this);
        pluginEvent.registerEvents(new QuitListener(), this);
        pluginEvent.registerEvents(new FirstPlayerJoinListener(), this);
        /* listeners - block */
        pluginEvent.registerEvents(new CropsBreakCanceller(), this);
        pluginEvent.registerEvents(new SaplingBreakCanceller(), this);

        getLogger().info("[AfnwCore2] Enabled!");
    }

    @Override
    public void onDisable() { getLogger().info("[AfnwCore2] Disabled!"); }
}
