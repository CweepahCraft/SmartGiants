package me.jjm_223.smartgiants.entities.v1_8_R3.nms;

import me.jjm_223.smartgiants.api.util.Configuration;
import me.jjm_223.smartgiants.entities.v1_8_R3.nms.PathfinderGoals.PathfinderGoalStomp;
import net.minecraft.server.v1_8_R3.*;

/**
 * Created by Jacob on 3/16/2015.
 * Hostile AI for the SmartGiant
 */

@SuppressWarnings("unchecked")
public class SmartGiantHostile extends SmartGiant
{
    public SmartGiantHostile(World world)
    {
        super(world);

        width = 1;

        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalStomp(this, EntityHuman.class, .9D, false));
        this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, .9D));
        this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, .9D));
        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, .5F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
    }

    @Override
    protected void initAttributes()
    {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(Configuration.getInstance().attackDamage());
    }

    @Override
    public float a(BlockPosition position)
    {
        return 0.5F - world.o(position);
    }
}