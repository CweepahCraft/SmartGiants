package me.jjm_223.smartgiants.entities.v1_8_R3;

import me.jjm_223.smartgiants.api.entities.IGiantTools;
import me.jjm_223.smartgiants.entities.v1_8_R3.nms.SmartGiant;
import me.jjm_223.smartgiants.entities.v1_8_R3.nms.SmartGiantHostile;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class GiantTools implements IGiantTools
{
    public void spawnGiant(Location location, boolean hostile)
    {
        World world = ((CraftWorld) location.getWorld()).getHandle();
        Entity entity = hostile ? new SmartGiantHostile(world) : new SmartGiant(world);
        entity.setPosition(location.getX(), location.getY(), location.getZ());
        world.addEntity(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
    }

    public boolean isSmartGiant(org.bukkit.entity.Entity entity)
    {
        return ((CraftEntity) entity).getHandle() instanceof SmartGiant;
    }
}
