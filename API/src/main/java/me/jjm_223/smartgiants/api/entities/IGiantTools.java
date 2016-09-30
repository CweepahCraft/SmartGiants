package me.jjm_223.smartgiants.api.entities;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public interface IGiantTools
{
    void spawnGiant(Location location, boolean hostile);

    boolean isSmartGiant(Entity entity);
}
