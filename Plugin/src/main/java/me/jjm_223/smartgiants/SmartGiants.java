package me.jjm_223.smartgiants;

import me.jjm_223.smartgiants.api.entities.INaturalSpawns;
import me.jjm_223.smartgiants.commands.CommandSmartGiants;
import me.jjm_223.smartgiants.api.entities.ILoad;
import me.jjm_223.smartgiants.listeners.GiantDeath;
import me.jjm_223.smartgiants.listeners.GiantSpawn;
import me.jjm_223.smartgiants.listeners.Reload;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * Created by Jacob on 3/14/2015.
 * Main class for Smart Giants
 */
public class SmartGiants extends JavaPlugin {

    FileConfiguration config;
    boolean errored;

    ILoad load;
    INaturalSpawns naturalSpawns;

    DropManager dropManager;

    private static File pluginFolder;

    public DropManager getDropManager() {
        return dropManager;
    }

    public static File getPluginFolder() {
        return pluginFolder;
    }

    public void load() {
        config = this.getConfig();

        boolean natural = config.getBoolean("naturalSpawns");
        boolean hostile = config.getBoolean("isHostile");

        String packageName = this.getServer().getClass().getPackage().getName();
        String version = packageName.substring(packageName.lastIndexOf('.') + 1);

        this.getLogger().info("Loading support for version: " + version);
        getLogger().info("Spawn naturally?: " + natural);
        boolean daylight = getConfig().getBoolean("daylight");
        getLogger().info("Spawn during day?: " + daylight);
        getLogger().info("Giants are hostile?: " + hostile);
        int frequency = config.getInt("frequency");
        if (frequency <= 0) {
            frequency = 5;
        }
        getLogger().info("Spawn frequency is: " + frequency);
        int minGroupAmount = config.getInt("minGroupAmount");
        if (minGroupAmount <= 0) {
            minGroupAmount = 1;
        }
        getLogger().info("Minimum group amount is: " + minGroupAmount);
        int maxGroupAmount = config.getInt("maxGroupAmount");
        if (maxGroupAmount <= 0 || maxGroupAmount < minGroupAmount) {
            maxGroupAmount = minGroupAmount + 1;
        }
        getLogger().info("Maximum group amount is: " + maxGroupAmount);

        try {
            final Class<?> clazzLoad = Class.forName("me.jjm_223.smartgiants.entities." + version + ".Load");
            final Class<?> clazzNaturalSpawns = Class.forName("me.jjm_223.smartgiants.entities." + version +
                    ".NaturalSpawns");
            if (ILoad.class.isAssignableFrom(clazzLoad) && INaturalSpawns.class.isAssignableFrom(clazzNaturalSpawns)) {
                this.load = (ILoad) clazzLoad.getConstructor(boolean.class)
                        .newInstance(hostile);
                if (natural) {
                    this.naturalSpawns = (INaturalSpawns) clazzNaturalSpawns.getConstructor(boolean.class,
                            boolean.class, int.class, int.class, int.class).newInstance(hostile, daylight, frequency,
                            minGroupAmount, maxGroupAmount);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            getLogger().severe("This Spigot version is not supported.");
            getLogger().info("Check for updates at https://www.spigotmc.org/resources/smartgiants.4882/");
            errored = true;
        }
    }

    @Override
    public void onLoad() {
        pluginFolder = this.getDataFolder();
        saveResource("lang.yml", false);
        saveDefaultConfig();
        load();
    }

    @Override
    public void onEnable() {
        if (!errored) {
            new ItemManager(this);
            getCommand("smartgiants").setExecutor(new CommandSmartGiants(this));
            getServer().getPluginManager().registerEvents(new GiantDeath(this), this);
            getServer().getPluginManager().registerEvents(new GiantSpawn(this), this);

            new Reload(this);
        } else {
            this.setEnabled(false);
        }
    }

    @Override
    public void onDisable() {
        ItemManager.unsafeSave();
        getLogger().info("Saved drops.");
    }
}
