package net.azisaba.afnw.afnwcore2.commands;

import net.azisaba.afnw.afnwcore2.util.item.AfnwScaffold;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AfnwCommand implements CommandExecutor {

    private final JavaPlugin plugin;
    public AfnwCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(command.getName().equals("afnw"))) return true;
        if(!(sender instanceof Player)) {
            sender.sendMessage(Component.text("/afnwコマンドはプレイヤーのみ実行可能です。").color(NamedTextColor.RED));
            return true;
        }

        Inventory inv = ((Player) sender).getInventory();
        int firstInv = inv.firstEmpty();
        if(firstInv == -1) {
            sender.sendMessage(Component.text("インベントリに空きがありません。").color(NamedTextColor.RED));
            return true;
        }

        FileConfiguration config = plugin.getConfig();
        if(!(config.isInt("vote.item-size")) && !(config.isInt("vote.scaffold-size"))) {
            throw new Error("configの値が数値ではありません。対象:vote> item-size, scaffold-size");
        }

        int itemSize = config.getInt("vote.item-size", 1);
        int scaffoldSize = config.getInt("vote.scaffold-size", 8);

        List<Material> itemList = new ArrayList<>(Arrays.asList(Material.values()));
        itemList.removeIf(type -> !isAllowed(type));
        ItemStack afnwItem = new ItemStack(itemList.get(0), itemSize);

        try {
            inv.addItem(afnwItem);
            for(int i = 0; i < scaffoldSize; i++) {
                inv.addItem(AfnwScaffold.afnwScaffold);
            }

            sender.sendMessage(Component.text("アイテムと交換しました。").color(NamedTextColor.GOLD));
            sender.sendMessage(Component.text("交換内容: " + afnwItem.getType() + " ×" + afnwItem.getAmount() + ", 足場ブロック ×" + scaffoldSize).color(NamedTextColor.GOLD));
        } catch (Error e) {
            Bukkit.getLogger().warning(e.getMessage());
        }
        return true;
    }

    private static boolean isAllowed(Material type) {
        if(!type.isItem()) return false;
        return switch (type) {
            case BEDROCK, STRUCTURE_BLOCK, STRUCTURE_VOID, COMMAND_BLOCK, CHAIN_COMMAND_BLOCK, COMMAND_BLOCK_MINECART, REPEATING_COMMAND_BLOCK, BARRIER, LIGHT, JIGSAW, END_PORTAL, KNOWLEDGE_BOOK, DEBUG_STICK, BUNDLE, AIR, VOID_AIR, CAVE_AIR ->
                    false;
            default -> true;
        };
    }
}
