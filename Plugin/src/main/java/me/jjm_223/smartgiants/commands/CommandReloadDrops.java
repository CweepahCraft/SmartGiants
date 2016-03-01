package me.jjm_223.smartgiants.commands;

import me.jjm_223.smartgiants.SmartGiants;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import static me.jjm_223.smartgiants.Messages.getLang;

public class CommandReloadDrops extends CommandBase {
    private SmartGiants plugin;

    public CommandReloadDrops(SmartGiants plugin) {
        super("ReloadDrops", "smartgiants.configure", false, 0);
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
        if (plugin.reloadDrops()) {
            sender.sendMessage(getLang("dropsReloaded"));
        } else {
            sender.sendMessage(getLang("dropsFailedReload"));
        }

        return true;
    }
}
