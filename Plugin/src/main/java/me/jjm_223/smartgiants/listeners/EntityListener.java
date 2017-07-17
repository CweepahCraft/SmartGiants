package me.jjm_223.smartgiants.listeners;

import me.jjm_223.smartgiants.SmartGiants;
import me.jjm_223.smartgiants.api.util.Configuration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import javax.annotation.Nonnull;
import java.util.List;

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
            if (!containsIgnoreCase(Configuration.getInstance().worlds(), event.getEntity().getWorld().getName()))
            {
                event.setCancelled(true);
            }
        }
    }

    private boolean containsIgnoreCase(@Nonnull List<String> sourceList, @Nonnull String search)
    {
        for (String string : sourceList)
        {
            if (search.equalsIgnoreCase(string))
            {
                return true;
            }
        }

        return false;
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
        event.setCancelled(shouldProtectFromArrow(event.getEntity(), event.getDamager().getType()));
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityCombust(EntityCombustByEntityEvent event)
    {
        event.setCancelled(shouldProtectFromArrow(event.getEntity(), event.getCombuster().getType()));
    }

    private boolean shouldProtectFromArrow(Entity damagedEntity, EntityType arrowType)
    {
        Configuration config = Configuration.getInstance();

        return plugin.getGiantTools().isSmartGiant(damagedEntity) &&
                ((!plugin.getVersion().startsWith("v1_8_R") && arrowType == EntityType.TIPPED_ARROW
                        && !config.giantsTakeTippedArrowDamage())
                || (arrowType == EntityType.ARROW && !config.giantsTakeArrowDamage()));
    }
}
