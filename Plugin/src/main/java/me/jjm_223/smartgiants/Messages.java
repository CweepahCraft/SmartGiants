package me.jjm_223.smartgiants;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class Messages
{
    private static FileConfiguration config = null;
    private static FileConfiguration fallback = null;

    public static String getLang(String section)
    {
        String string = config.getString(section);
        if (string == null)
        {
            string = fallback.getString(section);
        }

        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static void loadMessages(SmartGiants plugin)
    {
        Logger logger = JavaPlugin.getPlugin(SmartGiants.class).getLogger();

        fallback = new YamlConfiguration();
        InputStream stream = Messages.class.getClassLoader().getResourceAsStream("lang.yml");
        try
        {
            fallback.load(new InputStreamReader(stream));
        }
        catch (IOException | InvalidConfigurationException e)
        {
            logger.severe("Failed to read bundled lang file!");
            e.printStackTrace();
        }

        File configFile = new File(plugin.getDataFolder(), "lang.yml");
        config = new YamlConfiguration();
        try
        {
            config.load(configFile);
        }
        catch (IOException | InvalidConfigurationException ex)
        {
            logger.severe("Failed to read current language file.");
            ex.printStackTrace();
        }

        if (config != null && fallback != null)
        {
            try
            {
                updateCurrentLangFile(configFile);
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
                logger.severe("Failed to update current language file.");
            }
        }
    }

    private static void updateCurrentLangFile(File file) throws IOException
    {
        for (String string : fallback.getKeys(true))
        {
            if (!config.contains(string))
            {
                config.set(string, fallback.getString(string));
            }
        }

        config.save(file);
    }
}
