package me.jjm_223.smartgiants.api.util;

import me.jjm_223.smartgiants.api.entity.ISmartGiant;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public interface IGiantTools
{
    ISmartGiant spawnGiant(Location location, boolean hostile);

    boolean isSmartGiant(Entity entity);
}
