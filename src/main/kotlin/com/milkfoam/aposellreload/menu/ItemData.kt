package com.milkfoam.aposellreload.menu

import org.bukkit.Material

//"a":
//material: "PLAYER_HEAD"
//name: "&c%player_name%"
//lore:
//- "&e金币: &710000"
//- "&e点卷: &710000"

data class ItemData(
    val id: String,
    val material: Material,
    val name: String?,
    val lore: MutableList<String>?
)
