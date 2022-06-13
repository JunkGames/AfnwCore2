package net.azisaba.afnw.afnwcore2.listeners.player;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Event handlers for first-time participants
 * @author m2en
 * @see org.bukkit.event.Listener
 */
public class FirstPlayerJoinListener implements Listener {

    /**
     * A light introduction to how to play for new players who join for the first time
     * @param e Target player for PlayerJoinEvent
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(p.hasPlayedBefore()) return;

        String[] firstMessage = {
                ChatColor.GOLD +
                """
                Afnwへようこそ!
                奈落の世界を開拓するマルチサーバーです。まず初めにチュートリアルワールドを進んでみましょう。
                なお、詳しい遊び方はアジ鯖公式Wikiをご覧ください。
                https://wiki.azisaba.net/wiki/
                """
        };

        p.sendMessage(firstMessage);
    }
}
