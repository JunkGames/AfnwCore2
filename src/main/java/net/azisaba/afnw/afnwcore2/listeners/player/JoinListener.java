package net.azisaba.afnw.afnwcore2.listeners.player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
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
