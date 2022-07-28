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
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class TicketCommand(private val plugin: AfnwCore2, private val data: PlayerData): CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (command.name != "ticket") {
            return false
        }

        // /ticket sendの場合の処理
        if(args[0] == "send") {
            sendTicketCommand(sender, args[1])
            return true
        }

        // プレイヤーの取得
        val target = Bukkit.getPlayer(args[1])
        if (target == null) {
            sender.sendMessage(Component.text("エラー: プレイヤーが見つかりませんでした", NamedTextColor.RED))
            return false
        }

        // 配布数の設定(引数が未設定の場合は1枚)
        var amount = 0
        if(args.size == 2) {
            amount++
        } else {
            amount = args[2].toInt()
        }

        // 配布
        val inv = target.inventory
        for (i in 0 until amount) {
            inv.addItem(ticket)
        }

        // 通知
        sender.sendMessage(Component.text("${sender.name} が ${target.name}　にチケット(${amount}枚)を配布しました。", NamedTextColor.YELLOW))
        // TODO: ドキュメントのリンクを挿入する
        sender.sendMessage(Component.text("警告: プレイヤーデータに対しての補填を実行する際は /data set を使用します。\n詳細はドキュメントを参照してください: ", NamedTextColor.RED))
        target.sendMessage(Component.text("チケットを入手しました。 /afnwを実行してアイテムと交換しましょう。", NamedTextColor.YELLOW))

        return true
    }

    private fun sendTicketCommand(sender: CommandSender, id: String) {
        // コンソールのみコマンドの処理を受け入れる
        if(sender !is Player) {
            sender.sendMessage(Component.text("エラー: sendコマンドはコンソールからのみ実行できます", NamedTextColor.RED))
            return
        }

        // プレイヤーの取得
        val target = Bukkit.getPlayer(id)
        if(target == null) {
            sender.sendMessage(Component.text("エラー: プレイヤーが見つかりませんでした", NamedTextColor.RED))
            return
        }

        // コンフィグの取得
        val config = plugin.config
        val amount = config.getInt("vote.ticket-amount")

        // 配布
        val inv = target.inventory
        for (i in 0 until amount) {
            inv.addItem(ticket)
        }

        // プレイヤーデータの操作
        saveVoteData(target)

        // 通知
        sender.sendMessage(Component.text("${target.name}　にチケット(${amount}枚)を配布しました。", NamedTextColor.YELLOW))
        target.sendMessage(Component.text("チケットを入手しました。 /afnwを実行してアイテムと交換しましょう。", NamedTextColor.YELLOW))
    }

    private fun saveVoteData(target: Player) {
        val config = plugin.config

        val bonusLine = config.getInt("vote.bonus-line", 1)
        val netherStarAmount = config.getInt("vote.nether-star-amount", 1)

        val inv = target.inventory
        val dataFile = data.getPlayerData() ?: return
        var voteCount = dataFile.getInt(target.uniqueId.toString())
        voteCount++
        if(voteCount >= bonusLine) {
            for(i in 0 .. 9) {
                inv.addItem(ticket)
            }

            inv.addItem(ItemStack(Material.NETHER_STAR, netherStarAmount))
            target.sendMessage(Component.text("* 投票ボーナスとしてチケット10枚とネザースターを獲得しました。").color(NamedTextColor.LIGHT_PURPLE))
            target.sendMessage(Component.text("* 投票ボーナスがリセットされました。次回以降の投票から有効です。").color(NamedTextColor.LIGHT_PURPLE))
            plugin.logger.info(target.name + "が投票ボーナスを獲得しました。")
            voteCount = 0
        }
        dataFile.set(target.uniqueId.toString(), voteCount)
        data.savePlayerData()
    }
}
