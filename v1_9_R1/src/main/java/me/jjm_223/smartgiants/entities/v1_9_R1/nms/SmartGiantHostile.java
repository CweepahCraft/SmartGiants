package me.jjm_223.smartgiants.entities.v1_9_R1.nms;

import me.jjm_223.smartgiants.entities.v1_9_R1.nms.pathfindergoals.PathfinderGoalStomp;
import net.minecraft.server.v1_9_R1.*;
import org.bukkit.Bukkit;


@SuppressWarnings("unchecked")
public class SmartGiantHostile extends EntityGiantZombie {

    private static final double ATTACK_DAMAGE = Bukkit.getPluginManager().getPlugin("SmartGiants").getConfig()
            .getDouble("attackDamage");

    public SmartGiantHostile(World world) {
        super(world);

        width = 1;

        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalStomp(this, .9D, false));
        this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, .9D));
        this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, .9D));
        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, .5F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
    }

    @Override
    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(ATTACK_DAMAGE);
    }

    @Override
    public float a(BlockPosition position) {
        return 0.5F - world.n(position);
    }
}