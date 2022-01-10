package github.gilbertokpl.essentialsk.util

import github.gilbertokpl.essentialsk.EssentialsK
import org.bukkit.Location

object LocationUtil {
    fun locationSerializer(loc: Location): String {
        return loc.x.toString() + ";" +
                loc.y.toString() + ";" +
                loc.z.toString() + ";" +
                loc.world?.name + ";" +
                loc.pitch + ";" +
                loc.yaw
    }

    fun locationSerializer(s: String): Location? {
        return try {
            val parts = s.split(";").toTypedArray()
            val x = parts[0].toDouble()
            val y = parts[1].toDouble()
            val z = parts[2].toDouble()
            val w = try {
                EssentialsK.instance.server.getWorld(parts[3])
            } catch (e: Exception) {
                null
            }
            Location(
                w, x, y, z, try {
                    parts[5].toFloat()
                } catch (e: Exception) {
                    0F
                }, try {
                    parts[4].toFloat()
                } catch (e: Exception) {
                    0F
                }
            )
        } catch (e: Exception) {
            null
        }
    }
}
