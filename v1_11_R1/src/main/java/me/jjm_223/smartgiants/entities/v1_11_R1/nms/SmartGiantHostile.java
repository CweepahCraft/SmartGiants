package me.jjm_223.smartgiants.entities.v1_11_R1.nms;

import me.jjm_223.smartgiants.api.util.Configuration;
import me.jjm_223.smartgiants.entities.v1_11_R1.nms.pathfindergoals.PathfinderGoalStomp;
import net.minecraft.server.v1_11_R1.*;

public class SmartGiantHostile extends SmartGiant
{
    public SmartGiantHostile(World world)
    {
        super(world);

        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalStomp(this, .9D, false));
        this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, .9D));
        this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, .9D));
        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 0.5F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, true));
    }

    @Override
    public void initAttributes()
    {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(Configuration.getInstance().attackDamage());
    }
}
