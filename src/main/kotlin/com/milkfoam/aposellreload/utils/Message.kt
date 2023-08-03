package com.milkfoam.aposellreload.utils

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.common.platform.function.console
import taboolib.common.platform.function.onlinePlayers
import taboolib.module.chat.colored

fun Player.sendInfoMsg(text: String) {
    this.sendMessage("&7[&aApoSellReload&7] $text".colored())
}

fun Player.sendErrorMsg(text: String) {
    this.sendMessage("&7[&cApoSellReload&7] $text".colored())
}

fun consoleInfoMsg(text: String) {
    console().sendMessage("&7[&aApoSellReload&7] $text".colored())
}

fun consoleErrorMsg(text: String) {
    console().sendMessage("&7[&cApoSellReload&7] $text".colored())
}

fun CommandSender.sendInfoMsg(text: String) {
    this.sendMessage("&7[&aApoSellReload&7] $text".colored())
}

fun CommandSender.sendErrorMsg(text: String) {
    this.sendMessage("&7[&cApoSellReload&7] $text".colored())
}

fun sendAllPlayer(text: String) {
    onlinePlayers().forEach {
        it.sendMessage("&7[&cApoSellReload&7] $text".colored())
    }
}