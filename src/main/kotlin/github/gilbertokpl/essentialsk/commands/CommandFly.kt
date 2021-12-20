package github.gilbertokpl.essentialsk.commands

import github.gilbertokpl.essentialsk.EssentialsK
import github.gilbertokpl.essentialsk.configs.GeneralLang
import github.gilbertokpl.essentialsk.configs.MainConfig
import github.gilbertokpl.essentialsk.data.PlayerData
import github.gilbertokpl.essentialsk.manager.ICommand
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CommandFly : ICommand {
    override val consoleCanUse: Boolean = true
    override val permission: String = "essentialsk.commands.fly"
    override val minimumSize = 0
    override val maximumSize = 1
    override val commandUsage = listOf(
        "P_/fly",
        "essentialsk.commands.fly.other_/fly <PlayerName>"
    )

    override fun kCommand(s: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if (args.isEmpty() && s !is Player) {
            return true
        }

        if (args.size == 1) {

            //check perms
            if (s is Player && !s.hasPermission("essentialsk.commands.fly.other")) {
                s.sendMessage(GeneralLang.getInstance().generalNotPerm)
                return false
            }

            //check if player is online
            val p = EssentialsK.instance.server.getPlayer(args[0]) ?: run {
                s.sendMessage(GeneralLang.getInstance().generalPlayerNotOnline)
                return false
            }

            if (PlayerData(p.name.lowercase()).switchFly()) {
                p.sendMessage(GeneralLang.getInstance().flySendOtherActive)
                s.sendMessage(GeneralLang.getInstance().flySendActivatedOther.replace("%player", p.name))
            } else {
                p.sendMessage(GeneralLang.getInstance().flySendOtherDisable)
                s.sendMessage(GeneralLang.getInstance().flySendDisabledOther.replace("%player", p.name))
            }

            return false
        }

        if (MainConfig.getInstance().flyDisabledWorlds.contains((s as Player).location.world!!.name.lowercase())) {
            s.sendMessage(GeneralLang.getInstance().flySendDisabledWorld)
            return false
        }

        if (PlayerData(s.name.lowercase()).switchFly()) {
            s.sendMessage(GeneralLang.getInstance().flySendActive)
        } else {
            s.sendMessage(GeneralLang.getInstance().flySendDisable)
        }
        return false
    }
}