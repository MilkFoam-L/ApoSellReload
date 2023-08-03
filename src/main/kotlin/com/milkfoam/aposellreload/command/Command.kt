package com.milkfoam.aposellreload.command

import com.milkfoam.aposellreload.ApoSellReload.loadMenu
import com.milkfoam.aposellreload.ApoSellReload.loadSetting
import com.milkfoam.aposellreload.menu.Menu
import com.milkfoam.aposellreload.utils.sendErrorMsg
import com.milkfoam.aposellreload.utils.sendInfoMsg
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.common.io.newFile
import taboolib.common.platform.command.*
import taboolib.common.platform.function.getDataFolder
import taboolib.expansion.createHelper


@CommandHeader("ApoSell", ["sell", "apos"], permission = "aposell.use")
object Command {

    @CommandBody
    val main = mainCommand {
        createHelper()
    }

    @CommandBody(permission = "aposell.use")
    val reload = subCommand {
        execute<Player> { player, context, _ ->
            loadSetting()
            loadMenu()
            player.sendInfoMsg("&a重载成功")
        }
    }

    @CommandBody(permission = "aposell.player.use")
    val open = subCommand {
        dynamic("menu") {
            suggest { newFile("${getDataFolder()}/menu").listFiles()?.map { it.name.replace(".yml", "") } }
            execute<Player> { player, context, _ ->
                Menu.open(player, context["menu"])
            }
        }
    }

    @CommandBody(permission = "aposell.openplayer.user")
    val openplayer = subCommand {
        dynamic("menu") {
            suggest { newFile("${getDataFolder()}/menu").listFiles()?.map { it.name.replace(".yml", "") } }
            dynamic("player") {
                suggestPlayers()
                execute<CommandSender> { sender, context, _ ->
                    Bukkit.getPlayer(context["player"])?.let {
                        Menu.open(it, context["menu"])
                    } ?: run {
                        sender.sendErrorMsg("&c玩家未找到!")
                    }
                }
            }
        }
    }


}