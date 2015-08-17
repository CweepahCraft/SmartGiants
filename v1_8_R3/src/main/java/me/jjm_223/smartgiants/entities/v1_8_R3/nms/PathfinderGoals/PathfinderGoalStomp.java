//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package me.jjm_223.smartgiants.entities.v1_8_R3.nms.PathfinderGoals;

import net.minecraft.server.v1_8_R3.*;

public class PathfinderGoalStomp extends PathfinderGoal {
    World a;
    protected EntityCreature b;
    int c;
    double speed;
    boolean e;
    PathEntity pathEntity;
    Class<? extends Entity> targetClass;
    private int h;
    private double i;
    private double j;
    private double k;

    public PathfinderGoalStomp(EntityCreature entity, Class<? extends Entity> targetClass, double two, boolean three) {
        this(entity, two, three);
        this.targetClass = targetClass;
    }

    public PathfinderGoalStomp(EntityCreature entity, double speed, boolean two) {
        this.b = entity;
        this.a = entity.world;
        this.speed = speed;
        this.e = two;
        this.a(3);
    }

    public boolean a() {
        EntityLiving entity = this.b.getGoalTarget();
        if(entity == null) {
            return false;
        } else if(!entity.isAlive()) {
            return false;
        } else if(this.targetClass != null && !this.targetClass.isAssignableFrom(entity.getClass())) {
            return false;
        } else {
            this.pathEntity = this.b.getNavigation().a(entity);
            return this.pathEntity != null;
        }
    }

    public boolean b() {
        EntityLiving entity = this.b.getGoalTarget();
        return entity != null && (!entity.isAlive() && (!this.e ? !this.b.getNavigation().m() : this.b.e(new BlockPosition(entity))));
    }

    public void c() {
        this.b.getNavigation().a(this.pathEntity, this.speed);
        this.h = 0;
    }

    public void d() {
        this.b.getNavigation().n();
    }

    public void e() {
        EntityLiving entity = this.b.getGoalTarget();
        this.b.getControllerLook().a(entity, 30.0F, 30.0F);
        double one = this.b.e(entity.locX, entity.getBoundingBox().b, entity.locZ);
        double two = this.a(entity);
        --this.h;
        if((this.e || this.b.getEntitySenses().a(entity)) && this.h <= 0 && (this.i == 0.0D && this.j == 0.0D && this.k == 0.0D || entity.e(this.i, this.j, this.k) >= 1.0D || this.b.bc().nextFloat() < 0.05F)) {
            this.i = entity.locX;
            this.j = entity.getBoundingBox().b;
            this.k = entity.locZ;
            this.h = 4 + this.b.bc().nextInt(7);
            if(one > 1024.0D) {
                this.h += 10;
            } else if(one > 256.0D) {
                this.h += 5;
            }

            if(!this.b.getNavigation().a(entity, this.speed)) {
                this.h += 15;
            }
        }

        this.c = Math.max(this.c - 1, 0);
        if(one <= two && this.c <= 0) {
            this.c = 20;
            if(this.b.bA() != null) {
                this.b.bw();
            }

            this.b.r(entity);
        }

    }

    @SuppressWarnings("unused")
    protected double a(EntityLiving entity) {
        return 7;
    }
}
