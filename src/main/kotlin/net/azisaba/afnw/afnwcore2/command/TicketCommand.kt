package net.azisaba.afnw.afnwcore2.command

import net.azisaba.afnw.afnwcore2.AfnwCore2
import net.azisaba.afnw.afnwcore2.utils.AfnwTicket.ticket
import net.azisaba.afnw.afnwcore2.utils.data.PlayerData
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.UUID

class TicketCommand(private val plugin: AfnwCore2, private val data: PlayerData): CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(command.name != "ticket") {
            return false
        }

        val player = sender as Player
        val target = Bukkit.getPlayer(args[1])
        if(target == null) {
            sender.sendMessage(Component.text("エラー: プレイヤーが見つかりませんでした", NamedTextColor.RED))
            return false
        }

        when(args[0]) {
            "send" -> sendSubCommand(player, target)
            "give" -> giveSubCommand(player, target, args)
        }

        return true
    }

    private fun sendSubCommand(player: Player, target: Player) {
        // コンフィグからの値取り出し
        val config = plugin.config
        val ticketAmount = config.getInt("vote.ticket-amount", 1)

        // /ticket [player] (コンソール)
        val inv = target.inventory

        for(i in 0 until ticketAmount) {
            inv.addItem(ticket)
        }

        saveVoteData(target)

        player.sendMessage(Component.text("${target.name}　にチケットを配布しました。", NamedTextColor.YELLOW))
        target.sendMessage(Component.text("チケットを入手しました。 /afnwを実行してアイテムと交換しましょう。", NamedTextColor.YELLOW))
        return
    }

    private fun giveSubCommand(player: Player, target: Player, args: Array<out String>) {
        val inv = target.inventory
        val amount = args[2]

        for(i in 0 until amount.toInt()) {
            inv.addItem(ticket)
        }

        saveVoteData(target)

        player.sendMessage(Component.text("${target.name}　にチケット(${amount}枚)を配布しました。", NamedTextColor.YELLOW))
        target.sendMessage(Component.text("チケットを入手しました。 /afnwを実行してアイテムと交換しましょう。", NamedTextColor.YELLOW))
        return
    }

    private fun saveVoteData(target: Player) {
        val config = plugin.config
        val bonusLine = config.getInt("vote.bonus-line", 1)
        val netherStarAmount = config.getInt("vote.nether-star-amount", 1)

        val inv = target.inventory
        plugin.logger.info("1")
        val dataFile = data.getPlayerData() ?: return
        plugin.logger.info("1")
        var voteCount = dataFile.getInt(target.uniqueId.toString())
        voteCount++
        if(voteCount >= bonusLine) {
            for(i in 0 .. 9) {
                inv.addItem(ticket)
            }

            inv.addItem(ItemStack(Material.NETHER_STAR, netherStarAmount))
            target.sendMessage(
                Component.text("* 投票ボーナスとしてチケット10枚とネザースターを獲得しました。")
                    .color(NamedTextColor.YELLOW)
            )
            target.sendMessage(
                Component.text("* 投票ボーナスがリセットされました。次回以降の投票から有効です。")
                    .color(NamedTextColor.YELLOW)
            )
            plugin.logger.info(target.name + "が投票ボーナスを獲得しました。")
            voteCount = 0
        }
        dataFile.set(target.uniqueId.toString(), voteCount)
        data.savePlayerData()
    }
}
