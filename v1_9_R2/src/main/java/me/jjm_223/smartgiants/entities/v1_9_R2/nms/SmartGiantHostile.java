package me.jjm_223.smartgiants.entities.v1_9_R2.nms;

import me.jjm_223.smartgiants.api.util.Configuration;
import me.jjm_223.smartgiants.entities.v1_9_R2.nms.pathfindergoals.PathfinderGoalStomp;
import net.minecraft.server.v1_9_R2.*;


@SuppressWarnings("unchecked")
public class SmartGiantHostile extends SmartGiant
{
    public SmartGiantHostile(World world)
    {
        super(world);

        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalStomp(this, 1.0D, false));
        this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
        this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));
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
}