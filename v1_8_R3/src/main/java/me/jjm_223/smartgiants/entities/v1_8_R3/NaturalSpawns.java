package me.jjm_223.smartgiants.entities.v1_8_R3;

import me.jjm_223.smartgiants.api.entities.INaturalSpawns;
import me.jjm_223.smartgiants.entities.v1_8_R3.nms.SmartGiant;
import me.jjm_223.smartgiants.entities.v1_8_R3.nms.SmartGiantHostile;
import net.minecraft.server.v1_8_R3.BiomeBase;
import net.minecraft.server.v1_8_R3.BiomeBase.BiomeMeta;

import java.lang.reflect.Field;
import java.util.List;

public class NaturalSpawns implements INaturalSpawns {

    private boolean hostile;
    private int frequency;
    private int minGroupAmount;
    private int maxGroupAmount;

    public NaturalSpawns(boolean hostile, boolean daylight, int frequency, int minGroupAmount, int maxGroupAmount) {
        this.hostile = hostile;
        this.frequency = frequency;
        this.minGroupAmount = minGroupAmount;
        this.maxGroupAmount = maxGroupAmount;

        if (daylight) {
            daylight();
        } else {
            night();
        }
    }

    public void daylight() {
        try {
            for (Field field : BiomeBase.class.getDeclaredFields()) {
                if (field.getType().getSimpleName().equals(BiomeBase.class.getSimpleName())) {
                    if (field.get(null) != null) {
                        for (Field list : BiomeBase.class.getDeclaredFields()) {
                            if (list.getType().getSimpleName().equals(List.class.getSimpleName())) {
                                if (field.get(null) == BiomeBase.PLAINS
                                        || field.get(null) == BiomeBase.ICE_PLAINS
                                        || field.get(null) == BiomeBase.STONE_BEACH
                                        || field.get(null) == BiomeBase.EXTREME_HILLS
                                        || field.get(null) == BiomeBase.EXTREME_HILLS_PLUS
                                        || field.get(null) == BiomeBase.DESERT
                                        && list.getName().equals("au")) {
                                    list.setAccessible(true);
                                    List<BiomeMeta> metaList = (List) list.get(field.get(null));
                                    if (hostile) {
                                        metaList.add(new BiomeMeta(SmartGiantHostile.class, frequency, minGroupAmount,
                                                maxGroupAmount));
                                    } else {
                                        metaList.add(new BiomeMeta(SmartGiant.class, frequency, minGroupAmount,
                                                maxGroupAmount));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void night() {
        try {
            for (Field field : BiomeBase.class.getDeclaredFields()) {
                if (field.getType().getSimpleName().equals(BiomeBase.class.getSimpleName())) {
                    field.setAccessible(true);
                    if (field.get(null) != null) {
                        if (!(field.get(null) == BiomeBase.MUSHROOM_ISLAND)) {
                            Field atField = BiomeBase.class.getDeclaredField("at");
                            atField.setAccessible(true);
                            List at = (List) atField.get(field.get(null));
                            if (hostile) {
                                at.add(new BiomeMeta(SmartGiantHostile.class, frequency, minGroupAmount,
                                        maxGroupAmount));
                            } else {
                                at.add(new BiomeMeta(SmartGiant.class, frequency, minGroupAmount,
                                        maxGroupAmount));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
