package net.azisaba.afnw.afnwcore2.listeners.player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Communicating Lost Experience upon Death
 *
 * @author m2en
 * @see org.bukkit.event.Listener
 */
public class DeathListener implements Listener {

    /**
     * Send a message to the deceased player
     * @param e Target player for PlayerDeathEvent
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getPlayer();

        // ドロップした経験値量を通知
        int dropExp = e.getDroppedExp();
        if(dropExp == 0) {
            p.sendMessage(Component.text("死亡しました。経験値の消費はありません。", NamedTextColor.RED));
            return;
        }
        p.sendMessage(Component.text("死亡したため、" + e.getDroppedExp() + " Expを失いました。", NamedTextColor.RED));
    }
}
