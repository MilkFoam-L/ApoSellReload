package com.milkfoam.aposellreload

import com.milkfoam.aposellreload.menu.ItemData
import com.milkfoam.aposellreload.menu.MenuData
import org.bukkit.Material
import taboolib.common.io.newFile
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.console
import taboolib.common.platform.function.getDataFolder
import taboolib.common.platform.function.releaseResourceFile
import taboolib.library.xseries.parseToMaterial
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration

object ApoSellReload : Plugin() {

    @Config(migrate = true, value = "settings.yml", autoReload = true)
    lateinit var config: Configuration
        private set

    @Config(migrate = true, value = "menu/default.yml", autoReload = true)
    lateinit var default: Configuration
        private set

    val menuMap: MutableMap<String, MenuData> = mutableMapOf()

    var loreSellKey = ""
    var nbtSellKey = ""

    override fun onActive() {



        loadMenu()
        loadSetting()
    }

    fun loadSetting() {
        loreSellKey = config.getString("loreSellKey")!!
    }

    fun loadMenu() {
        newFile("${getDataFolder()}/menu").listFiles()?.forEach { file ->
            val fileConfig = Configuration.loadFromFile(file)
            val items: MutableList<ItemData> = mutableListOf()
            fileConfig.getConfigurationSection("Items")?.getKeys(false)?.forEach { id ->
                items.add(
                    ItemData(
                        id,
                        fileConfig.getString("Items.${id}.material")?.parseToMaterial() ?: Material.BARRIER,
                        fileConfig.getString("Items.${id}.name"),
                        fileConfig.getStringList("Items.${id}.lore").toMutableList(),
                    )
                )
            }
            menuMap[file.name.replace(".yml", "")] =
                MenuData(
                    fileConfig.getString("title") ?: "出售商店",
                    fileConfig.getStringList("Layout").toMutableList(),
                    items
                )
        }
    }


}