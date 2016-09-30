package me.jjm_223.smartgiants.entities.v1_9_R2.nms;

import net.minecraft.server.v1_9_R2.*;
import org.bukkit.Bukkit;

public class SmartGiant extends EntityGiantZombie
{
    private static final double HEALTH = Bukkit.getPluginManager().getPlugin("SmartGiants").getConfig()
            .getDouble("maxHealth");

    public SmartGiant(World world)
    {
        super(world);

        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(HEALTH);

        if (this instanceof SmartGiantHostile)
        {
            return;
        }

        width = 1;

        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalTempt(this, .9F, Items.APPLE, false));
        this.goalSelector.a(2, new PathfinderGoalRandomStroll(this, .9F));
        this.goalSelector.a(3, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 16.0F));
        this.goalSelector.a(4, new PathfinderGoalRandomLookaround(this));
    }

    @Override
    public float a(BlockPosition position)
    {
        return 0.5F - world.n(position);
    }
}