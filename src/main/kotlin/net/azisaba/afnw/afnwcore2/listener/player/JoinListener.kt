package net.azisaba.afnw.afnwcore2.listener.player

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class JoinListener: Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player

        // ログインメッセージ
        event.joinMessage(Component.text("* " + player.name + " がログインしました。", NamedTextColor.AQUA))

        // BedrockPlayerを自動的に蹴る
        if(player.name.startsWith(".") && player.name != ".Meru92a") {
            event.joinMessage(Component.text("Minecraft Bedrock Edition(統合版)での接続を検知しました。統合版でAfnwをプレイすることはできません。15秒後自動的にアジ鯖ロビーへ戻ります。", NamedTextColor.RED))
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ekick " + player.name + "Bedrock Blocker // 統合版での接続を検知したため、接続を切断しました。")
        }
    }
}
