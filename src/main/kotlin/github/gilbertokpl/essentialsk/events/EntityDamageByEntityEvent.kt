package github.gilbertokpl.essentialsk.events

import github.gilbertokpl.essentialsk.configs.MainConfig
import github.gilbertokpl.essentialsk.util.FileLoggerUtil
import org.apache.commons.lang3.exception.ExceptionUtils
import org.bukkit.entity.Item
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

class EntityDamageByEntityEvent : Listener {
    @EventHandler
    fun event(e: EntityDamageByEntityEvent) {
        if (MainConfig.getInstance().addonsBlockExplodeItems) {
            try {
                blockItemsExplode(e)
            } catch (e: Exception) {
                FileLoggerUtil.getInstance().logError(ExceptionUtils.getStackTrace(e))
            }
        }
    }

    private fun blockItemsExplode(e: EntityDamageByEntityEvent) {
        if (e.entity is Item) {
            e.isCancelled = true
        }
    }
}