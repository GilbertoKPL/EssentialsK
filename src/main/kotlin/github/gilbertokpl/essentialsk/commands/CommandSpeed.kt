package github.gilbertokpl.essentialsk.commands

import github.gilbertokpl.essentialsk.EssentialsK
import github.gilbertokpl.essentialsk.config.files.LangConfig
import github.gilbertokpl.essentialsk.config.files.MainConfig
import github.gilbertokpl.essentialsk.manager.CommandCreator
import github.gilbertokpl.essentialsk.manager.CommandData
import github.gilbertokpl.essentialsk.player.PlayerData
import github.gilbertokpl.essentialsk.player.modify.SpeedCache.clearSpeed
import github.gilbertokpl.essentialsk.player.modify.SpeedCache.setSpeed
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CommandSpeed : CommandCreator {
    override val commandData: CommandData
        get() = CommandData(
            active = MainConfig.speedActivated,
            consoleCanUse = true,
            commandName = "speed",
            timeCoolDown = null,
            permission = "essentialsk.commands.speed",
            minimumSize = 1,
            maximumSize = 2,
            commandUsage = listOf(
                "/speed <value>",
                "/speed remove",
                "essentialsk.commands.speed.other_/speed <player> <value>",
                "essentialsk.commands.speed.other_/speed <player> remove"
            )
        )

    override fun funCommand(s: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        //check if is 1
        if (args.size == 1 && s is Player) {

            val playerCache = PlayerData[s] ?: return false

            if (args[0].lowercase() == "remove" || args[0].lowercase() == "remover") {
                playerCache.clearSpeed(s)
                s.sendMessage(LangConfig.speedRemove)
                return false
            }

            //check int
            try {
                args[0].toInt()
            } catch (e: Throwable) {
                return true
            }

            //check if number is 0-10
            if (args[0].toInt() > 10 || args[0].toInt() < 0) {
                s.sendMessage(LangConfig.speedIncorrectValue)
                return false
            }

            playerCache.setSpeed(args[0].toInt(), s)
            s.sendMessage(LangConfig.speedSuccess.replace("%value%", args[0]))


            return false
        }

        if (args.size != 2) return true

        //check perm
        if (s is Player && !s.hasPermission("essentialsk.commands.speed.other")) {
            s.sendMessage(LangConfig.generalNotPerm)
            return false
        }

        //check if player exist
        val p = EssentialsK.instance.server.getPlayer(args[0]) ?: run {
            s.sendMessage(LangConfig.generalPlayerNotOnline)
            return false
        }

        val playerCache = PlayerData[p] ?: return false

        if (args[1].lowercase() == "remove" || args[0].lowercase() == "remover") {
            playerCache.clearSpeed(p)
            s.sendMessage(LangConfig.speedRemoveOther.replace("%player%", p.name))
            p.sendMessage(LangConfig.speedOtherRemove)
            return false
        }

        //check if number is 0-10
        if (args[1].toInt() > 10 || args[1].toInt() < 0) {
            s.sendMessage(LangConfig.speedIncorrectValue)
            return false
        }

        playerCache.setSpeed(args[1].toInt(), p)

        s.sendMessage(LangConfig.speedSuccessOther.replace("%player%", p.name).replace("%value%", args[1]))
        p.sendMessage(LangConfig.speedOtherSuccess.replace("%value%", args[1]))
        return false
    }
}
