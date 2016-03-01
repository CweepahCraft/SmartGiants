package me.jjm_223.smartgiants.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ReloadListener implements Listener {

    JavaPlugin plugin;

    public ReloadListener(JavaPlugin plugin) {
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().equalsIgnoreCase("/reload")) {
            if (e.getPlayer().hasPermission("bukkit.command.reload")) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.RED + "SmartGiants has disabled reloading as it cannot be reloaded without potentially" +
                        " breaking something.");
            }
        }
    }
}
