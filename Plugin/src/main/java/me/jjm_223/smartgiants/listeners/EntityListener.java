package me.jjm_223.smartgiants.listeners;

import me.jjm_223.smartgiants.SmartGiants;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityListener implements Listener
{
    private SmartGiants plugin;

    public EntityListener(SmartGiants plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e)
    {
        if (e.getEntityType() == EntityType.GIANT)
        {
            e.getDrops().clear();
            e.getDrops().addAll(plugin.getDropManager().getRandomDrops());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onSpawn(CreatureSpawnEvent e)
    {
        if ((e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.CHUNK_GEN || e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL) && e.getEntityType() == EntityType.GIANT)
        {
            if (!(plugin.getConfig().getStringList("worlds").contains(e.getLocation().getWorld().getName())))
            {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPluginSpawn(CreatureSpawnEvent event)
    {
        if (plugin.getGiantTools().isSmartGiant(event.getEntity()) || event.getEntityType() != EntityType.GIANT)
        {
            return;
        }

        event.setCancelled(true);
        plugin.getGiantTools().spawnGiant(event.getLocation(), plugin.getConfig().getBoolean("isHostile"));
    }
}
