package com.milkfoam.aposellreload.menu

import com.milkfoam.aposellreload.ApoSellReload.loreSellKey
import com.milkfoam.aposellreload.ApoSellReload.menuMap
import com.milkfoam.aposellreload.hook.Vault
import org.bukkit.entity.Player
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common5.Coerce
import taboolib.module.chat.Components
import taboolib.module.chat.colored
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.Basic
import taboolib.platform.compat.replacePlaceholder
import taboolib.platform.util.buildItem
import taboolib.platform.util.modifyLore
import taboolib.platform.util.sendLang
import java.util.regex.Pattern

object Menu {


    fun open(player: Player, file: String) {
        var totalPrice = 0.0
        menuMap[file]?.let { menuData ->
            player.openMenu<Basic>(menuData.title) {

                map(*menuData.layout.toTypedArray())

                menuData.items.map { itemData ->
                    set(Coerce.toChar(itemData.id), buildItem(itemData.material) {
                        name = itemData.name?.replacePlaceholder(player)?.colored()
                        itemData.lore?.let { lore.addAll(it.replacePlaceholder(player).colored()) }
                    }) {
                        isCancelled = true
                    }
                }

                onClick('-') {
                    getSlots('+').forEach { num ->
                        it.inventory.getItem(num)?.let { item ->
                            val amount = item.amount
                            item.modifyLore {
                                forEach { str ->
                                    if (str.contains(loreSellKey)) {
                                        val price = extractNumberFromString(str)?.toDoubleOrNull() ?: 0.0
                                        val endPrice = price * amount
                                        totalPrice += endPrice
                                        Vault.addMoney(player, endPrice)
                                        it.inventory.setItem(num, null)
                                    }
                                }
                            }
                        }
                    }
                    if (totalPrice > 0) {
                        val money = Vault.getMoney(player)
                        player.sendLang("sell-success", totalPrice, money)
                        totalPrice = 0.0
                    }
                }

                onClose {
                    it.returnItems(getSlots('+'))
                }


            }
        }


    }

    fun extractNumberFromString(inputString: String): String? {
        val pattern = "[\\d.]+".toRegex()
        val match = pattern.find(inputString)
        return match?.value
    }


}