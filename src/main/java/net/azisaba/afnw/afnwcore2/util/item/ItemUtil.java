package net.azisaba.afnw.afnwcore2.util.item;

import net.azisaba.itemstash.ItemStash;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ItemUtil {
    public static void addToStashIfEnabled(@NotNull UUID uuid, @NotNull ItemStack stack) {
        if (Bukkit.getPluginManager().isPluginEnabled("ItemStash")) {
            ItemStash.getInstance().addItemToStash(uuid, stack);
        }
    }

    public static void addToStashIfEnabledAsync(@NotNull Plugin plugin, @NotNull UUID uuid, @NotNull ItemStack stack) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> addToStashIfEnabled(uuid, stack));
    }
}
