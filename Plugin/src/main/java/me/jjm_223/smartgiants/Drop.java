package me.jjm_223.smartgiants;

import org.bukkit.inventory.ItemStack;

public class Drop {
    private final ItemStack item;
    private final int minAmount;
    private final int maxAmount;
    private final double percentChance;

    public Drop(ItemStack item, int minAmount, int maxAmount, double percentChance) {
        if (minAmount <= 0) {
            minAmount = 1;
        }

        if (maxAmount <= 0) {
            maxAmount = 1;
        }

        if (percentChance <= 0) {
            percentChance = 100;
        }

        this.item = item;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.percentChance = percentChance;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public double getPercentChance() {
        return percentChance;
    }

    public ItemStack getItem() {
        return item.clone();
    }
}