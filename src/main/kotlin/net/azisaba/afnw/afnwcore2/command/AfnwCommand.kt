package net.azisaba.afnw.afnwcore2.command

import net.azisaba.afnw.afnwcore2.AfnwCore2
import net.azisaba.afnw.afnwcore2.utils.AfnwTicket.ticket
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.util.Arrays

class AfnwCommand(private val plugin: AfnwCore2) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender !is Player) {
            Bukkit.getLogger().warning("このコマンドはプレイヤーのみ実行できます。")
            return false
        }

        if(command.name != "afnw") {
            return false
        }

        val player = sender
        val inv = player.inventory

        var amount: String? = args[0]
        if(amount == null) {
            amount = "1"
        }


        // 交換したい分のチケットがインベントリにあるか確認
        if(!(inv.containsAtLeast(ticket, amount.toInt()))) {
            player.sendMessage(Component.text("エラー: チケットが足りません。", NamedTextColor.RED))
            return false
        }

        // コンフィグの値取り出し
        val config: FileConfiguration = plugin.config
        val itemAmount = config.getInt("vote.item-amount", 1)
        val scaffoldAmount = config.getInt("vote.scaffold-amount", 1)

        // インベントリに十分な空きがない場合
        if(inv.firstEmpty() == -itemAmount + scaffoldAmount) {
            player.sendMessage(Component.text("エラー: インベントリに十分な空きがないため交換できません。整理してからまた再実行してください。", NamedTextColor.RED))
            return false
        }

        // TODO: 処理を改善させる
        // インベントリからチケットを消費させアイテムを追加する
        for(t in 0 until amount.toInt()) {
            for (i in 0 until itemAmount) {
                // アイテムの選出処理
                val random: SecureRandom
                val item: ItemStack
                try {
                    random = SecureRandom.getInstance("SHA1PRNG")
                    val itemList: MutableList<Material> = ArrayList(Arrays.asList(*Material.values()))
                    itemList.removeIf { type: Material -> !isAllowed(type) }
                    item = ItemStack(itemList[random.nextInt(itemList.size)], amount.toInt())
                } catch (error: NoSuchAlgorithmException) {
                    throw NoSuchAlgorithmException(error)
                }
                inv.addItem(item)
            }
            inv.removeItem(ticket)
        }

        inv.addItem(ItemStack(Material.SCAFFOLDING, scaffoldAmount))

        player.sendMessage(Component.text(amount + "枚のチケットをアイテムと交換しました", NamedTextColor.YELLOW))
        return true
    }

    companion object {
        private fun isAllowed(type: Material): Boolean {
            return if (!type.isItem) {
                false
            } else when (type) {
                Material.BEDROCK,
                Material.STRUCTURE_BLOCK,
                Material.STRUCTURE_VOID,
                Material.COMMAND_BLOCK,
                Material.CHAIN_COMMAND_BLOCK,
                Material.COMMAND_BLOCK_MINECART,
                Material.REPEATING_COMMAND_BLOCK,
                Material.BARRIER,
                Material.LIGHT,
                Material.JIGSAW,
                Material.END_PORTAL,
                Material.KNOWLEDGE_BOOK,
                Material.DEBUG_STICK,
                Material.AIR,
                Material.VOID_AIR,
                Material.CAVE_AIR,
                Material.BUNDLE -> false
                else -> true
            }
        }
    }

}
