package net.azisaba.afnw.afnwcore2.listeners.player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Event handlers for first-time participants
 * @author m2en
 * @see org.bukkit.event.Listener
 */
public record FirstPlayerJoinListener(JavaPlugin plugin) implements Listener {

    /**
     * A light introduction to how to play for new players who join for the first time
     * @param e Target player for PlayerJoinEvent
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(p.hasPlayedBefore()) return;

        p.sendMessage(Component.text("Afnwへようこそ!", NamedTextColor.AQUA));
        p.sendMessage(Component.text("周りは奈落、基本的に投票でしかアイテムを入手できない世界で、他のプレイヤーと協力して発展を目指すマルチサーバーです。", NamedTextColor.AQUA));
        p.sendMessage(Component.text("まずはチュートリアルワールドを進んでみましょう!", NamedTextColor.AQUA));
        p.sendMessage(Component.text("(このチュートリアルワールドは /tutorial でいつでも来れます。)", NamedTextColor.AQUA));

        FileConfiguration config = plugin().getConfig();
        World tutorial = Bukkit.getWorld(config.getString("tp.tutorial_world_name", "tutorial"));
        if (tutorial == null) {
            throw new NullPointerException("Tutorial World could not be found");
        }

        p.teleport(tutorial.getSpawnLocation());
    }
}
