package me.jjm_223.smartgiants.entities.v1_8_R2;

import me.jjm_223.smartgiants.api.entities.INaturalSpawns;
import me.jjm_223.smartgiants.entities.v1_8_R2.nms.SmartGiant;
import me.jjm_223.smartgiants.entities.v1_8_R2.nms.SmartGiantHostile;
import net.minecraft.server.v1_8_R2.BiomeBase;
import net.minecraft.server.v1_8_R2.BiomeBase.BiomeMeta;
import net.minecraft.server.v1_8_R2.EnumCreatureType;

import java.util.ArrayList;
import java.util.List;

public class NaturalSpawns implements INaturalSpawns
{
    private boolean hostile;
    private boolean daylight;
    private int frequency;
    private int minGroupAmount;
    private int maxGroupAmount;


    public void load(boolean hostile, boolean daylight, int frequency, int minGroupAmount, int maxGroupAmount)
    {
        this.hostile = hostile;
        this.daylight = daylight;
        this.frequency = frequency;
        this.minGroupAmount = minGroupAmount;
        this.maxGroupAmount = maxGroupAmount;

        if (daylight)
        {
            daylight();
        }
        else
        {
            night();
        }
    }

    public void daylight()
    {
        for (BiomeBase biomeBase : BiomeBase.getBiomes())
        {
            List mobs = biomeBase.getMobs(EnumCreatureType.CREATURE);
            if (!mobs.isEmpty())
            {
                mobs.add(new BiomeMeta(
                        (hostile ? SmartGiantHostile.class : SmartGiant.class), frequency, minGroupAmount, maxGroupAmount));
            }
        }
    }

    public void night()
    {
        for (BiomeBase biomeBase : BiomeBase.getBiomes())
        {
            List mobs = biomeBase.getMobs(EnumCreatureType.MONSTER);
            if (!mobs.isEmpty())
            {
                mobs.add(new BiomeMeta((hostile ? SmartGiantHostile.class : SmartGiant.class),
                        frequency, minGroupAmount, maxGroupAmount));
            }
        }
    }

    public void cleanup()
    {
        for (BiomeBase biomeBase : BiomeBase.getBiomes())
        {
            List mobs;
            if (daylight)
            {
                mobs = biomeBase.getMobs(EnumCreatureType.CREATURE);
            }
            else
            {
                mobs = biomeBase.getMobs(EnumCreatureType.MONSTER);
            }
            for (Object object : new ArrayList<Object>(mobs))
            {
                BiomeMeta biomeMeta = ((BiomeMeta) object);
                if (biomeMeta.b == SmartGiantHostile.class || biomeMeta.b == SmartGiant.class)
                {
                    mobs.remove(object);
                }
            }
        }
    }
}
