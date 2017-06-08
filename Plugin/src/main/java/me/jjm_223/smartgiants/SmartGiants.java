package me.jjm_223.smartgiants;

import me.jjm_223.smartgiants.api.util.Configuration;
import me.jjm_223.smartgiants.api.util.IGiantTools;
import me.jjm_223.smartgiants.api.util.ILoad;
import me.jjm_223.smartgiants.api.util.INaturalSpawns;
import me.jjm_223.smartgiants.commands.*;
import me.jjm_223.smartgiants.listeners.EntityListener;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * Copyright (C) 2016  Jacob Martin
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class SmartGiants extends JavaPlugin
{
    private boolean errorOnLoad;

    private ILoad load = null;
    private INaturalSpawns naturalSpawns = null;
    private IGiantTools giantTools = null;

    private DropManager dropManager;

    @Override
    public void onLoad()
    {
        if (!new File(getDataFolder(), "lang.yml").exists())
        {
            saveResource("lang.yml", false);
        }

        this.getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        try
        {
            loadConfig();
        }
        catch (InvalidConfigurationException | IOException e)
        {
            e.printStackTrace();
            this.setEnabled(false);
        }

        saveConfig();

        loadGiants();
    }

    private void loadConfig() throws IOException, InvalidConfigurationException
    {
        Configuration.load(new File(getDataFolder(), "config.yml"));
    }

    @Override
    public void onEnable()
    {
        if (!errorOnLoad)
        {
            Messages.loadMessages(this);
            try
            {
                dropManager = new DropManager(this);
            }
            catch (IOException e)
            {
                e.printStackTrace();
                getLogger().severe("Unable to load drops.");
            }

            registerCommands();
            registerEvents();
        }
        else
        {
            getLogger().severe("This version of Spigot isn't supported. Disabling.");
            this.setEnabled(false);
        }
    }

    public boolean reloadDrops()
    {
        dropManager.shutdown();
        try
        {
            dropManager = new DropManager(this);
            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    private void loadGiants()
    {
        Configuration config = Configuration.getInstance();

        boolean natural = config.naturalSpawns();
        boolean hostile = config.isHostile();

        String packageName = this.getServer().getClass().getPackage().getName();
        String version = packageName.substring(packageName.lastIndexOf('.') + 1);

        this.getLogger().info("Loading support for version: " + version);
        getLogger().info("Spawn naturally?: " + natural);
        boolean daylight = getConfig().getBoolean("daylight");
        getLogger().info("Spawn during day?: " + daylight);
        getLogger().info("Giants are hostile?: " + hostile);
        int frequency = config.frequency();
        if (frequency <= 0)
        {
            frequency = 5;
        }
        getLogger().info("Spawn frequency is: " + frequency);
        int minGroupAmount = config.minGroupAmount();
        if (minGroupAmount <= 0)
        {
            minGroupAmount = 1;
        }
        getLogger().info("Minimum group amount is: " + minGroupAmount);
        int maxGroupAmount = config.maxGroupAmount();
        if (maxGroupAmount <= 0 || maxGroupAmount < minGroupAmount)
        {
            maxGroupAmount = minGroupAmount + 1;
        }
        getLogger().info("Maximum group amount is: " + maxGroupAmount);

        try
        {
            final Class<?> clazzLoad = Class.forName("me.jjm_223.smartgiants.entities." + version + ".Load");
            final Class<?> clazzNaturalSpawns = Class.forName("me.jjm_223.smartgiants.entities." + version +
                    ".NaturalSpawns");
            final Class<?> clazzGiantTools = Class.forName("me.jjm_223.smartgiants.entities." + version + ".GiantTools");
            if (ILoad.class.isAssignableFrom(clazzLoad) && INaturalSpawns.class.isAssignableFrom(clazzNaturalSpawns)
                    && IGiantTools.class.isAssignableFrom(clazzGiantTools))
            {
                this.load = (ILoad) clazzLoad.getConstructor().newInstance();
                if (natural)
                {
                    this.naturalSpawns = (INaturalSpawns) clazzNaturalSpawns.getConstructor().newInstance();
                    this.naturalSpawns.load(hostile, daylight, frequency, minGroupAmount, maxGroupAmount);
                }
                this.giantTools = (IGiantTools) clazzGiantTools.getConstructor().newInstance();
                load.load(hostile);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            getLogger().severe("This Spigot version is not supported.");
            getLogger().info("Check for updates at https://www.spigotmc.org/resources/smartgiants.4882/");
            errorOnLoad = true;
        }
    }

    private void registerEvents()
    {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new EntityListener(this), this);
    }

    private void registerCommands()
    {
        getCommand("smartgiants").setExecutor(new CommandBase());
        new CommandAdd(this);
        new CommandRemove(this);
        new CommandReset(this);
        new CommandReloadDrops(this);
        new CommandReloadConfig();
    }

    @Override
    public void onDisable()
    {
        if (dropManager != null)
        {
            dropManager.shutdown();
        }

        if (load != null)
        {
            load.cleanup();
        }

        if (naturalSpawns != null)
        {
            naturalSpawns.cleanup();
        }
    }

    public IGiantTools getGiantTools()
    {
        return giantTools;
    }

    public DropManager getDropManager()
    {
        return dropManager;
    }
}
