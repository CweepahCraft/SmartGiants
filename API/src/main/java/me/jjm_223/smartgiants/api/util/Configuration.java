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
        return config.getBoolean("isHostile");
    }

    public double attackDamage()
    {
        verifyLoaded();
        return config.getDouble("attackDamage");
    }

    public boolean damageObeyGameDifficulty()
    {
        verifyLoaded();
        return config.getBoolean("damageObeyGameDifficulty");
    }

    public boolean handleDrops()
    {
        verifyLoaded();
        return config.getBoolean("handleDrops");
    }

    public double maxHealth()
    {
        verifyLoaded();
        return config.getDouble("maxHealth");
    }

    public boolean giantsTakeArrowDamage()
    {
        verifyLoaded();
        return config.getBoolean("giantsTakeArrowDamage");
    }

    public boolean giantsTakeTippedArrowDamage()
    {
        verifyLoaded();
        return config.getBoolean("giantsTakeTippedArrowDamage");
    }

    public boolean naturalSpawns()
    {
        verifyLoaded();
        return config.getBoolean("naturalSpawns");
    }

    public int frequency()
    {
        verifyLoaded();
        return config.getInt("frequency");
    }

    public int minGroupAmount()
    {
        verifyLoaded();
        return config.getInt("minGroupAmount");
    }

    public int maxGroupAmount()
    {
        verifyLoaded();
        return config.getInt("maxGroupAmount");
    }

    public boolean daylight()
    {
        verifyLoaded();
        return config.getBoolean("daylight");
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
