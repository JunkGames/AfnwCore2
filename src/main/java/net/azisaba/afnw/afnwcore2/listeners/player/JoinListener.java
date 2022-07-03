package net.azisaba.afnw.afnwcore2.listeners.player;

import net.azisaba.afnw.afnwcore2.AfnwCore2;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.time.Duration;

/**
 * Send the name of the player who logged in
 * @author m2en
 * @see org.bukkit.event.Listener
 */
public class JoinListener implements Listener {

    /**
     * Send player's name
     * @param e Target player for PlayerJoinEvent
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        // プレイヤーがサーバーに参加したらタイトルとログインメッセージを送信する
        sendPlayerTitle(p);
        e.joinMessage(Component.text(p.getName() + "がログインしました").color(NamedTextColor.YELLOW));

        // 統合版のプレイヤーをブロックする
        if(p.getName().startsWith(".")) {
            if(p.getName().equals(".Meru92a")) return;
            p.sendMessage(Component.text("Minecraft Bedrock Edition(統合版)での接続を検知しました。\n大変申し訳ありませんが統合版でAfnwをプレイすることはできません。Java版での接続をお願いします。15秒後自動的にロビーへ戻ります。", NamedTextColor.RED));

            new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "ekick " + p.getName() + " Bedrock Blockが発動しました");
                }
            }.runTaskLater(JavaPlugin.getPlugin(AfnwCore2.class), 20L * 15);
        }
    }

    /**
     * Methods to generate and send titles using Adventure
     * @deprecated We plan to remove this method in the future
     * @param p Player to send title
     */
    @Deprecated
    public void sendPlayerTitle(@NonNull Player p) {
        final Title.Times times = Title.Times.of(Duration.ofMillis(500), Duration.ofMillis(3000), Duration.ofMillis(1000));
        final Title title = Title.title(Component.text("Afnwへようこそ!").color(NamedTextColor.AQUA), Component.empty(), times);
        p.showTitle(title);
    }
}
