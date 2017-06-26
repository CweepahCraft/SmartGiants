package me.jjm_223.smartgiants.entities.v1_10_R1.nms;

import me.jjm_223.smartgiants.api.entity.ISmartGiant;
import me.jjm_223.smartgiants.api.util.Configuration;
import net.minecraft.server.v1_10_R1.*;

public class SmartGiant extends EntityGiantZombie implements ISmartGiant
{
    public SmartGiant(World world)
    {
        super(world);

        width = 1;

        if (this instanceof SmartGiantHostile)
        {
            return;
        }

        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalTempt(this, 1.0D, Items.APPLE, false));
        this.goalSelector.a(2, new PathfinderGoalRandomStroll(this, 1.0D));
        this.goalSelector.a(3, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 16.0F));
        this.goalSelector.a(4, new PathfinderGoalRandomLookaround(this));
    }

    @Override
    protected void initAttributes()
    {
        super.initAttributes();

        double health = Configuration.getInstance().maxHealth();

        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(health);
        setHealth((float) health);

        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(Configuration.getInstance().movementSpeed());
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(Configuration.getInstance().followRange());
    }

    @Override
    public float a(BlockPosition position)
    {
        return 0.5F - world.n(position);
    }

    public boolean isHostile()
    {
        return (this instanceof SmartGiantHostile);
    }
}