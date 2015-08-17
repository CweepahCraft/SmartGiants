package me.jjm_223.smartgiants.listeners;

import me.jjm_223.smartgiants.ItemManager;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;


/**
 * Drops the items on death
 * 4/10/15
 */
public class GiantDeath implements Listener {

    JavaPlugin plugin;

    public GiantDeath(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDeath(final EntityDeathEvent e) {
        if (e.getEntity().getType().equals(EntityType.GIANT)) {

            new BukkitRunnable(){
                public void run() {
                    final ItemManager config = new ItemManager(plugin);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            for (ItemStack item : config.getItems()) {
                                e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), item);
                            }
                        }
                    }.runTask(plugin);
                }
            }.runTaskAsynchronously(plugin);
        }
    }
}
