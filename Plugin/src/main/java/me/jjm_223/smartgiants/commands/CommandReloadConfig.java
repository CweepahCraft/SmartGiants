package me.jjm_223.smartgiants.commands;

import me.jjm_223.smartgiants.api.util.Configuration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.IOException;

import static me.jjm_223.smartgiants.Messages.getLang;

public class CommandReloadConfig extends CommandBase
{
    public CommandReloadConfig()
    {
        super("ReloadConfig", "smartgiants.configure", false, 0);
    }

    @Override
    public boolean execute(CommandSender sender, Command cmd, String label, String[] args)
    {
        try
        {
            Configuration.getInstance().reload();
            sender.sendMessage(getLang("configReloadSuccess"));
        }
        catch (IOException | InvalidConfigurationException e)
        {
            e.printStackTrace();
            sender.sendMessage(getLang("configReloadFail"));
        }

        return true;
    }
}
