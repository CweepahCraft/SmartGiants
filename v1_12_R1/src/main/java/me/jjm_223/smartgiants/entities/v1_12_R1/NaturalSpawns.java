package me.jjm_223.smartgiants.entities.v1_12_R1;

import me.jjm_223.smartgiants.api.util.INaturalSpawns;
import me.jjm_223.smartgiants.entities.v1_12_R1.nms.SmartGiant;
import me.jjm_223.smartgiants.entities.v1_12_R1.nms.SmartGiantHostile;
import net.minecraft.server.v1_12_R1.BiomeBase;
import net.minecraft.server.v1_12_R1.BiomeBase.BiomeMeta;
import net.minecraft.server.v1_12_R1.EnumCreatureType;

import java.util.Iterator;
import java.util.List;

public class NaturalSpawns implements INaturalSpawns
{
    private boolean hostile;
    private boolean daylight;
    private int frequency;
    private int minGroupAmount;
    private int maxGroupAmount;

    @Override
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

    private void daylight()
    {
        for (BiomeBase biomeBase : BiomeBase.REGISTRY_ID)
        {
            if (biomeBase != null)
            {
                List<BiomeMeta> mobs = biomeBase.getMobs(EnumCreatureType.CREATURE);
                if (!mobs.isEmpty())
                {
                    mobs.add(new BiomeMeta((hostile ? SmartGiantHostile.class : SmartGiant.class), frequency,
                            minGroupAmount, maxGroupAmount));
                }
            }
        }
    }

    private void night()
    {
        for (BiomeBase biomeBase : BiomeBase.REGISTRY_ID)
        {
            if (biomeBase != null)
            {
                List<BiomeMeta> mobs = biomeBase.getMobs(EnumCreatureType.MONSTER);
                if (!mobs.isEmpty())
                {
                    mobs.add(new BiomeMeta((hostile ? SmartGiantHostile.class : SmartGiant.class), frequency,
                            minGroupAmount, maxGroupAmount));
                }
            }
        }
    }

    @Override
    public void cleanup()
    {
        for (BiomeBase biomeBase : BiomeBase.REGISTRY_ID)
        {
            List<BiomeMeta> mobs = biomeBase.getMobs(daylight ? EnumCreatureType.CREATURE : EnumCreatureType.MONSTER);
            Iterator<BiomeMeta> metaItr = mobs.iterator();

            while (metaItr.hasNext())
            {
                BiomeMeta meta = metaItr.next();

                if (meta.b == SmartGiantHostile.class || meta.b == SmartGiant.class)
                {
                    metaItr.remove();
                }
            }
        }
    }
}
