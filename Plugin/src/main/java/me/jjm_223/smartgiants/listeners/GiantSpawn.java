package me.jjm_223.smartgiants.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Only allows Giants to spawn in certain worlds.
 */
public class GiantSpawn implements Listener {

    JavaPlugin plugin;

    public GiantSpawn(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void spawn(CreatureSpawnEvent e) {
        if ((e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.CHUNK_GEN || e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL) && e.getEntityType() == EntityType.GIANT) {
            if (!(plugin.getConfig().getStringList("worlds").contains(e.getLocation().getWorld().getName()))) {
                e.setCancelled(true);
            }
        }
    }
}
