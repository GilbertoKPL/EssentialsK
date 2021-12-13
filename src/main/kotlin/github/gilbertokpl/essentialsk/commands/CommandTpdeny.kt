package github.gilbertokpl.essentialsk.commands

import github.gilbertokpl.essentialsk.EssentialsK
import github.gilbertokpl.essentialsk.configs.GeneralLang
import github.gilbertokpl.essentialsk.data.Dao
import github.gilbertokpl.essentialsk.manager.ICommand
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CommandTpdeny : ICommand {
    override val consoleCanUse: Boolean = false
    override val permission: String = "essentialsk.commands.tpa"
    override val minimumSize = 0
    override val maximumSize = 0
    override val commandUsage = listOf(
        "/tpdeny"
    )

    override fun kCommand(s: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val p = Dao.getInstance().tpaHash[s as Player] ?: run {
            s.sendMessage(GeneralLang.getInstance().tpaNotAnyRequest)
            return false
        }

        //remove checker
        Dao.getInstance().tpaHash.remove(s)
        Dao.getInstance().tpaHash.remove(p)

        s.sendMessage(GeneralLang.getInstance().tpaRequestDeny.replace("%player%", p.name))

        if (EssentialsK.instance.server.getPlayer(p.name) != null) {
            p.sendMessage(GeneralLang.getInstance().tpaRequestDeny.replace("%player%", s.name))
        }
        return false
    }
}