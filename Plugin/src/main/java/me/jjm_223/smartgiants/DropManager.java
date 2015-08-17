package me.jjm_223.smartgiants;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class DropManager {

    private SmartGiants plugin;
    private FileConfiguration config = new YamlConfiguration();

    private List<Drop> drops = new ArrayList<Drop>();
    private File dropsFile;
    private int nextIncrement;

    private Random random = new Random();

    public DropManager(SmartGiants plugin) {
        this.plugin = plugin;

        File pluginDataFolder = plugin.getDataFolder();
        dropsFile = new File(pluginDataFolder.getAbsolutePath() + File.separator + "drops.yml");

        try {
            boolean dropsNoExist = dropsFile.createNewFile();
        } catch (IOException e) {
            plugin.getLogger().severe("Unable to create file: " + dropsFile.toString());
        }

        try {
            config.load(dropsFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Unable to create file: " + dropsFile.toString());
        } catch (InvalidConfigurationException e) {
            plugin.getLogger().severe("Invalid configuration: drops.yml. Feel free to start fresh if you aren't sure" +
                    " how to fix this. You can reload this config with /smartgiants dropsreload");
            e.printStackTrace();
        }
        loadDrops();
    }

    public List<ItemStack> getRandomDrops() {

        List<ItemStack> results = new ArrayList<ItemStack>();

        for (Drop drop : drops) {
            if (random.nextInt((int) (100 / drop.getPercentChance())) == 0) {
                ItemStack item = drop.getItem();
                item.setAmount(random.nextInt(drop.getMaxAmount() - drop.getMinAmount()) + drop.getMinAmount());
            }
        }

        return results;
    }

    public void saveDrop(ItemStack item, int minAmount, int maxAmount, double percentage) {
        item = item.clone();
        String material = item.getType().name();
        short data = item.getDurability();
        String customName = item.getItemMeta().getDisplayName();
        Map<Enchantment, Integer> enchantments = item.getEnchantments();

        String base = "drops." + nextIncrement + ".";

        config.set(base + "type", material);
        config.set(base + "data", data);
        config.set(base + "name", customName);
        config.set(base + "minAmount", minAmount);
        config.set(base + "maxAmount", maxAmount);
        config.set(base + "chance", percentage);
        for (Enchantment enchant : enchantments.keySet()) {
            config.set(base + "enchantments." + enchant + ".level", enchantments.get(enchant));
        }

        loadDrops();
        save();
    }

    public void loadDrops() {
        drops.clear();
        for (String string : config.getConfigurationSection("drops").getKeys(false)) {
            int id = Integer.parseInt(string);
            if (id >= nextIncrement) {
                nextIncrement = id + 1;
            }
            Material material = Material.getMaterial(config.getString("drops." + string + ".type"));
            short data = (short) config.getInt("drops." + string + ".data");
            String customName = config.getString("drops." + string + ".name");
            int minAmount = config.getInt("drops." + string + ".minAmount");
            int maxAmount = config.getInt("drops." + string + ".maxAmount");
            double percentChance = config.getDouble("drops." + string + ".chance");
            Map<Enchantment, Integer> enchantments = new HashMap<Enchantment, Integer>();
            for (String enchant : config.getConfigurationSection("drops." + string + ".enchantments").getKeys(false)) {
                enchantments.put(Enchantment.getByName(enchant),
                        config.getInt("drops." + string + ".enchantments." + enchant + ".level"));
            }
            ItemStack item = new ItemStack(material, 1, data);
            item.addUnsafeEnchantments(enchantments);
            if (customName != null) {
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', customName));
                item.setItemMeta(meta);
            }

            drops.add(new Drop(item, minAmount, maxAmount, percentChance));
        }
    }

    private void save() {
        new BukkitRunnable() {
            public void run() {
                synchronized (this) {
                    try {
                        config.save(dropsFile);
                    } catch (IOException e) {
                        plugin.getLogger().severe("Unable to save file: " + dropsFile.toString());
                    }
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    class Drop {
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
}
