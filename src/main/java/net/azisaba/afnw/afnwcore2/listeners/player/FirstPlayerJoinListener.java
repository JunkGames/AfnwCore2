package net.azisaba.afnw.afnwcore2.listeners.player;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * 初参加者に対するイベンドハンドラ
 */
public class FirstPlayerJoinListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(p.hasPlayedBefore()) return;

        String[] firstMessage = {
                ChatColor.GOLD +
                """
                Afnwへようこそ!
                奈落の世界を開拓するマルチサーバーです。まず初めにチュートリアルワールドを進んでみましょう。
                なお、アジ鯖公式Wikiをご覧ください。
                https://wiki.azisaba.net/wiki/
                """
        };

        p.sendMessage(firstMessage);
    }
}
