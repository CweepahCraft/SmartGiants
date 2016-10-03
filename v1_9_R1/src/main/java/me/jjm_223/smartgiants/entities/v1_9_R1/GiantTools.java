package me.jjm_223.smartgiants.entities.v1_9_R1;


import me.jjm_223.smartgiants.api.entity.ISmartGiant;
import me.jjm_223.smartgiants.api.util.IGiantTools;
import me.jjm_223.smartgiants.entities.v1_9_R1.nms.SmartGiant;
import me.jjm_223.smartgiants.entities.v1_9_R1.nms.SmartGiantHostile;
import net.minecraft.server.v1_9_R1.Entity;
import net.minecraft.server.v1_9_R1.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class GiantTools implements IGiantTools
{
    public ISmartGiant spawnGiant(Location location, boolean hostile)
    {
        World world = ((CraftWorld) location.getWorld()).getHandle();
        Entity entity = hostile ? new SmartGiantHostile(world) : new SmartGiant(world);
        entity.setPosition(location.getX(), location.getY(), location.getZ());
        world.addEntity(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);

        return ((ISmartGiant) entity);
    }

    public boolean isSmartGiant(org.bukkit.entity.Entity entity)
    {
        return ((CraftEntity) entity).getHandle() instanceof SmartGiant;
    }
}
