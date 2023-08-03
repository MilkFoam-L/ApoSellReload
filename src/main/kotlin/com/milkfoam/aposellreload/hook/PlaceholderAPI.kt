package com.milkfoam.aposellreload.hook

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import taboolib.platform.compat.PlaceholderExpansion

object PlaceholderAPI : PlaceholderExpansion {

    override val identifier: String = "asr"

    //aor_<playername>
    //

    override fun onPlaceholderRequest(player: Player?, args: String): String {
        val players = Bukkit.getPlayer(args) ?: player
        return ""
    }
}