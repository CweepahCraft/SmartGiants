package me.jjm_223.smartgiants;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DropManager
{
    private FileConfiguration config = new YamlConfiguration();
    private List<Drop> drops = new ArrayList<>();
    private File dropsFile;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private Random random = new Random();

    public DropManager(SmartGiants plugin) throws IOException
    {
        dropsFile = new File(plugin.getDataFolder(), "drops.yml");

        dropsFile.createNewFile();

        try
        {
            config.load(dropsFile);
        }
        catch (InvalidConfigurationException e)
        {
            e.printStackTrace();
            plugin.getLogger().severe("Invalid configuration: drops.yml. Feel free to start fresh if you aren't sure" +
                    " how to fix this. You can reload this config with /smartgiants reloaddrops");
        }
        loadDrops();
    }

    public List<ItemStack> getRandomDrops()
    {

        List<ItemStack> results = new ArrayList<>();

        for (Drop drop : drops)
        {
            if (random.nextInt((int) Math.ceil(100 / drop.getPercentChance())) == 0)
            {
                ItemStack item = drop.getItem();
                item.setAmount(random.nextInt(Math.max(drop.getMaxAmount() - drop.getMinAmount(), 1)) + drop.getMinAmount());
                results.add(item);
            }
        }

        return results;
    }

    public boolean deleteDrop(ItemStack item)
    {
        for (Drop drop : drops)
        {
            if (drop.getItem().isSimilar(item))
            {
                drops.remove(drop);
                saveFile();
                return true;
            }
        }

        return false;
    }

    public void addDrop(Drop drop)
    {
        drops.add(drop);
        saveFile();
    }

    public void resetDrops()
    {
        drops.clear();
        saveFile();
    }

    private void updateConfig()
    {
        config = new YamlConfiguration();
        for (Drop drop : drops)
        {
            String base = "drops." + drops.indexOf(drop);
            config.set(base + ".itemStack", drop.getItem());
            config.set(base + ".minAmount", drop.getMinAmount());
            config.set(base + ".maxAmount", drop.getMaxAmount());
            config.set(base + ".chance", drop.getPercentChance());
        }
    }

    private void loadDrops()
    {
        drops.clear();
        ConfigurationSection section = config.getConfigurationSection("drops");
        if (section == null)
        {
            return;
        }
        for (String string : section.getKeys(false))
        {
            String base = "drops." + string;
            ItemStack item = config.getItemStack(base + ".itemStack");
            int minAmount = config.getInt(base + ".minAmount");
            int maxAmount = config.getInt(base + ".maxAmount");
            double percentChance = config.getDouble(base + ".chance");

            drops.add(new Drop(item, minAmount, maxAmount, percentChance));
        }
    }

    private void saveFile()
    {
        updateConfig();
        executor.submit(new Runnable()
        {
            public void run()
            {
                try
                {
                    config.save(dropsFile);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    public void shutdown()
    {
        saveFile();
        executor.shutdown();
    }
}
