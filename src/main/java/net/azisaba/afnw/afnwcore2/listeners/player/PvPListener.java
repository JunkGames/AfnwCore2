package net.azisaba.afnw.afnwcore2.listeners.player;

import net.azisaba.afnw.afnwcore2.AfnwCore2;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public record PvPListener(AfnwCore2 plugin) implements Listener {
    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player killer)) return;
        if (!(e.getEntity() instanceof Player player)) return;
        if (!plugin.pvpEnabled.contains(killer.getUniqueId()) || !plugin.pvpEnabled.contains(player.getUniqueId())) {
            e.setCancelled(true);
        }
    }
}
