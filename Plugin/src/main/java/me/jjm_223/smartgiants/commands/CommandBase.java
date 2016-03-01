package me.jjm_223.smartgiants.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static me.jjm_223.smartgiants.Messages.getLang;

public class CommandBase implements CommandExecutor {
    private static List<CommandBase> subCommands = new ArrayList<CommandBase>();

    private final String name;
    private final String permission;
    private final boolean playerOnly;
    private final int minArgs;

    public CommandBase(String name, String permission, boolean playerOnly, int minArgs) {
        this.name = name;
        this.permission = permission;
        this.playerOnly = playerOnly;
        this.minArgs = minArgs;
        subCommands.add(this);
    }

    public CommandBase() {
        this.name = null;
        this.permission = null;
        this.playerOnly = false;
        this.minArgs = 0;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length < 1) {
            lecture(commandSender);
            return true;
        }
        List<String> args = Arrays.asList(strings).subList(1, strings.length);
        CommandBase chosen = null;

        for (CommandBase subCommand : subCommands) {
            if (subCommand.getName().equalsIgnoreCase(strings[0])) {
                chosen = subCommand;
                break;
            }
        }

        if (chosen == null) {
            lecture(commandSender);
            return true;
        }

        if (!(commandSender instanceof Player) && chosen.getPlayerRequired()) {
            commandSender.sendMessage(getLang("mustBePlayer"));
            return true;
        }

        if (chosen.getPermission() != null && !commandSender.hasPermission(chosen.getPermission())) {
            commandSender.sendMessage(getLang("noPermission"));
            return true;
        }

        if (args.size() < chosen.getMinArgs()) {
            lecture(commandSender);
            return true;
        }

        String[] newArgs = new String[args.size()];
        return chosen.execute(commandSender, command, s, args.toArray(newArgs));
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public boolean getPlayerRequired() {
        return playerOnly;
    }

    public int getMinArgs() {
        return minArgs;
    }

    public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
        return false;
    };

    protected void lecture(CommandSender sender) {
        sender.sendMessage(getLang("lectureBar"));
        sender.sendMessage(getLang("lectureAdd"));
        sender.sendMessage(getLang("lectureRemove"));
        sender.sendMessage(getLang("lectureReset"));
        sender.sendMessage(getLang("lectureReload"));
        sender.sendMessage(getLang("lectureBar"));
    }
}
