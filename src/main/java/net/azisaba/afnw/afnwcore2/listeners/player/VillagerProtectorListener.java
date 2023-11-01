package net.azisaba.afnw.afnwcore2.listeners.player;

import org.bukkit.GameMode;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class VillagerProtectorListener implements Listener {
    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Villager)) return;
        if (e.getDamager() instanceof Player player) {
            if (player.getGameMode() != GameMode.CREATIVE) {
                e.setCancelled(true);
            }
        }
        if (e.getDamager() instanceof Arrow arrow && arrow.getShooter() instanceof Player) {
            e.setCancelled(true);
        }
    }
}
