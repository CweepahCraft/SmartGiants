package me.jjm_223.smartgiants;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacob on 5/9/2015.
 * Manages saved item stacks.
 */
public class ItemManager {

    private static FileConfiguration config = new YamlConfiguration();
    private static File save;
    private static boolean hasLoaded;

    public ItemManager() {
        if (!hasLoaded) {
            try {
                throw new IllegalAccessException("You cannot use this constructor for the first instance of " +
                        "this class!");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public ItemManager(JavaPlugin plugin) {
        if (!hasLoaded) {
            config = new YamlConfiguration();

            save = new File(plugin.getDataFolder() + File.separator + "drops.yml");

            if (!save.exists()) {
                try {
                    if (!save.getParentFile().exists()) {
                        save.getParentFile().mkdir();
                    }
                    save.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }

            try {
                config.load(save);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidConfigurationException e) {
                e.printStackTrace();
            }

            new BukkitRunnable() {
                public void run() {
                    try {
                        config.save(save);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.runTaskTimerAsynchronously(plugin, 0L, 200L);

            hasLoaded = true;
        }
    }
    
    public List<ItemStack> getItems() {
        return (List<ItemStack>) config.getList("drops", new ArrayList<ItemStack>());
    }
    
    public void addItem(ItemStack itemStack) {
        List<ItemStack> listStack = (List<ItemStack>) config.getList("drops", new ArrayList<ItemStack>());
        listStack.add(itemStack);
        config.set("drops", listStack);
    }

    public void removeItem(ItemStack itemStack) {
        List<ItemStack> listStack = (List<ItemStack>) config.getList("drops", new ArrayList<ItemStack>());
        listStack.remove(itemStack);
        config.set("drops", listStack);
    }

    public void removeAllItems() {
        config.set("drops", null);
    }

    public static void unsafeSave() {
        try {
            config.save(save);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
