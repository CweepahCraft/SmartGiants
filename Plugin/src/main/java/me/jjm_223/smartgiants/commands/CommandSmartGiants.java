package me.jjm_223.smartgiants.commands;

import me.jjm_223.smartgiants.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import static me.jjm_223.smartgiants.Messages.getLang;

/**
 * Command class for "/smartgiants"
 * 4/10/15
 */
public class CommandSmartGiants implements CommandExecutor {

    JavaPlugin plugin;
    ItemManager itemManager;

    public CommandSmartGiants(JavaPlugin plugin) {
        this.plugin = plugin;

        itemManager = new ItemManager();
    }

    public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getLabel().equalsIgnoreCase("smartgiants")) {
            if (sender.hasPermission("smartgiants.configure")) {
                if (args.length >= 1) {
                    if (args[0].equalsIgnoreCase("add")) {
                        if (sender instanceof Player) {
                            Player player = (Player) sender;

                            ItemStack newItem = player.getItemInHand().clone();

                            if (newItem.getType() == Material.AIR) {
                                sender.sendMessage(getLang("noItemInHand"));
                                return true;
                            }

                            itemManager.addItem(newItem);

                            sender.sendMessage(getLang("dropAdded"));
                        } else {
                            sender.sendMessage(getLang("mustBePlayer"));
                        }
                    } else if (args[0].equalsIgnoreCase("remove")) {
                        if (sender instanceof Player) {
                            Player player = (Player) sender;

                            final ItemStack newItem = player.getItemInHand();

                            if (newItem.getType() == Material.AIR) {
                                sender.sendMessage(getLang("noItemInHand"));
                                return true;
                            }

                            itemManager.removeItem(newItem);

                            sender.sendMessage(getLang("dropRemoved"));
                        } else {
                            sender.sendMessage(getLang("mustBePlayer"));
                        }
                    } else if (args[0].equalsIgnoreCase("reset")) {
                        itemManager.removeAllItems();
                        sender.sendMessage(getLang("allItemsRemoved"));
                    } else {
                        lecture(sender);
                    }
                } else {
                    lecture(sender);
                }
            } else {
                sender.sendMessage(getLang("noPermission"));
            }
            return true;
        }
        return false;
    }

    private void lecture(CommandSender sender) {
        sender.sendMessage(getLang("lecture1"));
        sender.sendMessage(getLang("lecture2"));
        sender.sendMessage(getLang("lecture3"));
        sender.sendMessage(getLang("lecture4"));
        sender.sendMessage(getLang("lecture5"));
    }
}
