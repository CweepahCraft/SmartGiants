package me.jjm_223.smartgiants.commands;

import me.jjm_223.smartgiants.SmartGiants;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import static me.jjm_223.smartgiants.Messages.getLang;

public class CommandReset extends CommandBase {
    private SmartGiants plugin;

    public CommandReset(SmartGiants plugin) {
        super("Reset", "smartgiants.configure", false, 0);
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
        plugin.getDropManager().resetDrops();

        sender.sendMessage(getLang("allItemsRemoved"));

        return true;
    }
}
