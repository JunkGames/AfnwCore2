package net.azisaba.afnw.afnwcore2.listeners.player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Send name of logged out player
 * @author m2en
 * @see org.bukkit.event.Listener
 */
public class QuitListener implements Listener {

    /**
     * Send name of logged out player
     * @param e Target player for PlayerQuitEvent
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        e.quitMessage(Component.text(p.getName() + "がログアウトしました。").color(NamedTextColor.YELLOW));
    }
}
