package net.azisaba.afnw.afnwcore2.util.item;

import net.azisaba.afnw.afnwcore2.AfnwCore2;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Create AfnwTicket
 *
 * @author m2en
 * @param plugin Main Class Arguments
 */
public record AfnwTicket(AfnwCore2 plugin) {

    public static ItemStack afnwTicket = new ItemStack(Material.PAPER, 1);
    static {
        ItemMeta metaData = afnwTicket.getItemMeta();
        metaData.displayName(Component.text("AfnwTicket").color(NamedTextColor.LIGHT_PURPLE));
        List<String> lore = new ArrayList<>();
        lore.add("Afnw内で使用できるチケットです。");
        lore.add("/afnw でチケットとアイテムを交換できます。また、このアイテムは交換可能です。");
        metaData.setLore(lore);
        afnwTicket.setItemMeta(metaData);
    }

}
