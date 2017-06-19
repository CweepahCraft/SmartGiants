package me.jjm_223.smartgiants.entities.v1_8_R3.nms.pathfindergoals;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityCreature;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.PathfinderGoalMeleeAttack;

public class PathfinderGoalStomp extends PathfinderGoalMeleeAttack
{
    public PathfinderGoalStomp(EntityCreature pathCreature, Class<? extends Entity> targetClass, double movementSpeed, boolean searchThroughWalls)
    {
        super(pathCreature, targetClass, movementSpeed, searchThroughWalls);
    }

    @Override
    protected double a(EntityLiving livingEntity)
    {
        return 7;
    }
}