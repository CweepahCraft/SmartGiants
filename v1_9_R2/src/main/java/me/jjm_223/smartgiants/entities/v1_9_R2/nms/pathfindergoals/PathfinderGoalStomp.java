package me.jjm_223.smartgiants.entities.v1_9_R2.nms.pathfindergoals;

import net.minecraft.server.v1_9_R2.EntityGiantZombie;
import net.minecraft.server.v1_9_R2.PathfinderGoalMeleeAttack;

public class PathfinderGoalStomp extends PathfinderGoalMeleeAttack {
    private final EntityGiantZombie h;
    private int i;

    public PathfinderGoalStomp(EntityGiantZombie var1, double var2, boolean var4) {
        super(var1, var2, var4);
        this.h = var1;
    }

    public void c() {
        super.c();
        this.i = 0;
    }
}
