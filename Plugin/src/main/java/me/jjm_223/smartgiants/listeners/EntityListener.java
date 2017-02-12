package me.jjm_223.smartgiants.listeners;

import me.jjm_223.smartgiants.SmartGiants;
import me.jjm_223.smartgiants.api.util.Configuration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityListener implements Listener
{
    private SmartGiants plugin;

    public EntityListener(SmartGiants plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event)
    {
        if (event.getEntityType() == EntityType.GIANT && Configuration.getInstance().handleDrops())
        {
            event.getDrops().clear();
            event.getDrops().addAll(plugin.getDropManager().getRandomDrops());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onSpawn(CreatureSpawnEvent event)
    {
        SpawnReason reason = event.getSpawnReason();
        if ((reason == SpawnReason.CHUNK_GEN || reason == SpawnReason.NATURAL) && event.getEntityType() == EntityType.GIANT)
        {
            if (!(Configuration.getInstance().worlds().contains(event.getLocation().getWorld().getName())))
            {
                event.setCancelled(true);
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
        plugin.getGiantTools().spawnGiant(event.getLocation(), Configuration.getInstance().isHostile());
    }

    @EventHandler(ignoreCancelled = true)
    public void onGiantDamage(EntityDamageByEntityEvent event)
    {
        if (!Configuration.getInstance().damageObeyGameDifficulty()
                && plugin.getGiantTools().isSmartGiant(event.getDamager()))
        {
            event.setDamage(Configuration.getInstance().attackDamage());
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onArrowDamage(EntityDamageByEntityEvent event)
    {
        if (!plugin.getGiantTools().isSmartGiant(event.getEntity()))
        {
            return;
        }

        EntityType type = event.getDamager().getType();
        Configuration config = Configuration.getInstance();

        event.setCancelled((type == EntityType.TIPPED_ARROW && !config.giantsTakeTippedArrowDamage())
                || (type == EntityType.ARROW && !config.giantsTakeArrowDamage()));
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityCombust(EntityCombustByEntityEvent event)
    {
        if (!plugin.getGiantTools().isSmartGiant(event.getEntity()))
        {
            return;
        }

        EntityType type = event.getCombuster().getType();
        Configuration config = Configuration.getInstance();

        event.setCancelled((type == EntityType.TIPPED_ARROW && !config.giantsTakeTippedArrowDamage())
                || (type == EntityType.ARROW && !config.giantsTakeArrowDamage()));
    }
}
