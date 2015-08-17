package me.jjm_223.smartgiants.entities.v1_8_R1.nms;

import net.minecraft.server.v1_8_R1.*;

/**
 * Created by Jacob on 3/15/2015.
 * SmartGiant for 1.8
 */
public class SmartGiant extends EntityGiantZombie {
    public SmartGiant(World world) {
        super(world);

        width = 1;

        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalTempt(this, 0.5F, Items.APPLE, false));
        this.goalSelector.a(2, new PathfinderGoalRandomStroll(this, 0.5F));
        this.goalSelector.a(3, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 16.0F));
        this.goalSelector.a(4, new PathfinderGoalRandomLookaround(this));
    }

    @Override
    public float a(BlockPosition position) {
        return 0.5F - world.o(position);
    }
}
