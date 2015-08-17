package me.jjm_223.smartgiants.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class CommandBase {

    private final String PERMISSION;

    List<String> aliases;

    public CommandBase(String permission, String... aliases) {
        this.PERMISSION = permission;
        this.aliases = Arrays.asList(aliases);
    }

    public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
        if (!aliases.contains(label)) {
            return false;
        }

        if (!sender.hasPermission(PERMISSION)) {
            return true;
        }
    }
}
