package me.jjm_223.smartgiants.commands;

import me.jjm_223.smartgiants.Drop;
import me.jjm_223.smartgiants.SmartGiants;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static me.jjm_223.smartgiants.Messages.getLang;

public class CommandAdd extends CommandBase {
    SmartGiants plugin;

    public CommandAdd(SmartGiants plugin) {
        super("Add", "smartgiants.configure", true, 3);
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        ItemStack newItem = player.getItemInHand().clone();

        if (newItem.getType() == Material.AIR) {
            sender.sendMessage(getLang("noItemInHand"));
            return true;
        }

        try {
            plugin.getDropManager().addDrop(new Drop(newItem, Integer.parseInt(args[0]),
                    Integer.parseInt(args[1]), Double.parseDouble(args[2])));
            sender.sendMessage(getLang("dropAdded"));
            return true;
        } catch (NumberFormatException e) {
            sender.sendMessage(getLang("lectureAdd"));
            return true;
        }
    }
}
