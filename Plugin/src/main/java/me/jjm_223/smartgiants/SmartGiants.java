package me.jjm_223.smartgiants;

import me.jjm_223.smartgiants.api.entities.ILoad;
import me.jjm_223.smartgiants.api.entities.INaturalSpawns;
import me.jjm_223.smartgiants.commands.*;
import me.jjm_223.smartgiants.listeners.EntityListener;
import me.jjm_223.smartgiants.listeners.ReloadListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

/**
 * Created by Jacob on 3/14/2015.
 * Main class for Smart Giants
 */
public class SmartGiants extends JavaPlugin {

    private FileConfiguration config;
    private boolean error;

    private ILoad load;
    private INaturalSpawns naturalSpawns;

    private DropManager dropManager;

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
            error = true;
        }
    }

    @Override
    public void onLoad() {
        saveResource("lang.yml", false);
        saveDefaultConfig();
        load();
    }

    @Override
    public void onEnable() {
        if (!error) {
            Messages.loadMessages(this);
            try {
                dropManager = new DropManager(this);
            } catch (IOException e) {
                e.printStackTrace();
                getLogger().severe("Unable to load drops.");
            }

            registerCommands();
            registerEvents();
        } else {
            this.setEnabled(false);
        }
    }

    public boolean reloadDrops() {
        dropManager.shutdown();
        try {
            dropManager = new DropManager(this);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new EntityListener(this), this);
        pm.registerEvents(new ReloadListener(this), this);
    }

    private void registerCommands() {
        getCommand("smartgiants").setExecutor(new CommandBase());
        new CommandAdd(this);
        new CommandRemove(this);
        new CommandReset(this);
        new CommandReloadDrops(this);
    }

    @Override
    public void onDisable() {
        dropManager.shutdown();
    }

    public DropManager getDropManager() {
        return dropManager;
    }
}
