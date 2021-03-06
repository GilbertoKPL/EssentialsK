package github.gilbertokpl.essentialsk.api

import github.gilbertokpl.essentialsk.api.apis.Discord
import github.gilbertokpl.essentialsk.api.exceptions.PlayerNotFound
import github.gilbertokpl.essentialsk.player.PlayerData
import org.bukkit.plugin.java.JavaPlugin

class EssentialsKAPI(pl: JavaPlugin) {

    private val plugin = pl

    private val discordAPI = Discord(plugin)

    fun getDiscordAPI(): Discord {
        return discordAPI
    }

    fun getPlayer(PlayerID: String) : PlayerData {
        return PlayerData[PlayerID] ?: throw PlayerNotFound()
    }
}
