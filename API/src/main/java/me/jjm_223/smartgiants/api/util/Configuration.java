package me.jjm_223.smartgiants.api.util;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Configuration
{
    private static Configuration INSTANCE;

    private File file;
    private FileConfiguration config;

    private Configuration(File file) throws IOException, InvalidConfigurationException
    {
        this.file = file;
        this.config = new YamlConfiguration();
        this.config.load(this.file);
    }

    public boolean isHostile()
    {
        verifyLoaded();
        return config.getBoolean("isHostile", false);
    }

    public double attackDamage()
    {
        verifyLoaded();
        return config.getDouble("attackDamage", 5.0);
    }

    public boolean damageObeyGameDifficulty()
    {
        verifyLoaded();
        return config.getBoolean("damageObeyGameDifficulty", true);
    }

    public boolean handleDrops()
    {
        verifyLoaded();
        return config.getBoolean("handleDrops", true);
    }

    public double maxHealth()
    {
        verifyLoaded();
        return config.getDouble("maxHealth", 100.0);
    }

    public boolean giantsTakeArrowDamage()
    {
        verifyLoaded();
        return config.getBoolean("giantsTakeArrowDamage", true);
    }

    public boolean giantsTakeTippedArrowDamage()
    {
        verifyLoaded();
        return config.getBoolean("giantsTakeTippedArrowDamage", true);
    }

    public boolean naturalSpawns()
    {
        verifyLoaded();
        return config.getBoolean("naturalSpawns", false);
    }

    public int frequency()
    {
        verifyLoaded();
        return config.getInt("frequency", 5);
    }

    public int minGroupAmount()
    {
        verifyLoaded();
        return config.getInt("minGroupAmount", 1);
    }

    public int maxGroupAmount()
    {
        verifyLoaded();
        return config.getInt("maxGroupAmount", 1);
    }

    public boolean daylight()
    {
        verifyLoaded();
        return config.getBoolean("daylight", false);
    }

    public List<String> worlds()
    {
        verifyLoaded();
        return config.getStringList("worlds");
    }

    private void verifyLoaded()
    {
        if (INSTANCE == null)
        {
            throw new IllegalStateException("Config hasn't been loaded yet. Report this to the mod author.");
        }
    }

    public void reload() throws IOException, InvalidConfigurationException
    {
        this.config.load(this.file);
    }

    public static Configuration load(File file) throws IOException, InvalidConfigurationException
    {
        if (INSTANCE != null)
        {
            throw new IllegalStateException("Config has already been loaded. Report this to the mod author.");
        }

        INSTANCE = new Configuration(file);

        return INSTANCE;
    }

    public static Configuration getInstance()
    {
        return INSTANCE;
    }
}
