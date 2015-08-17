package me.jjm_223.smartgiants.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Reload implements Listener {

    JavaPlugin plugin;

    public Reload(JavaPlugin plugin) {
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onCmdPreProcess(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().equalsIgnoreCase("/reload")) {
            if (e.getPlayer().hasPermission("bukkit.command.reload")) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.RED + "This server is running smartgiants. Reloading with this " +
                        "plugin installed will break the server, which will require an actual restart anyway. Not to " +
                        "mention, reloading is pretty a pretty bad thing to do to begin with.");
            }
        }
    }
}
