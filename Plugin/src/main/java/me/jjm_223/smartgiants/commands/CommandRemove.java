package me.jjm_223.smartgiants.commands;

import me.jjm_223.smartgiants.SmartGiants;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static me.jjm_223.smartgiants.Messages.getLang;

public class CommandRemove extends CommandBase {
    private SmartGiants plugin;

    public CommandRemove(SmartGiants plugin) {
        super("Remove", "smartgiants.configure", true, 0);
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        if (player.getItemInHand() == null) {
            player.sendMessage(getLang("noItemInHand"));
            return true;
        }

        ItemStack itemToRemove = player.getItemInHand().clone();

        if (!plugin.getDropManager().deleteDrop(itemToRemove)) {
            sender.sendMessage(getLang("dropNoExist"));
            return true;
        }

        player.sendMessage(getLang("dropRemoved"));

        return true;
    }
}
