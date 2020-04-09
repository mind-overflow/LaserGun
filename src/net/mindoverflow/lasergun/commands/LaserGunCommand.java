package net.mindoverflow.lasergun.commands;

import net.mindoverflow.lasergun.LaserGun;
import net.mindoverflow.lasergun.commands.laserguncommands.GetCommand;
import net.mindoverflow.lasergun.commands.laserguncommands.HelpCommand;
import net.mindoverflow.lasergun.commands.laserguncommands.ReloadCommand;
import net.mindoverflow.lasergun.utils.Debugger;
import net.mindoverflow.lasergun.utils.MessageUtils;
import net.mindoverflow.lasergun.utils.LocalizedMessages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class LaserGunCommand implements CommandExecutor
{

    // Initialize the plugin variable so we can access all of the plugin's data.
    private LaserGun plugin;

    // Initialize the debugger so I can debug the plugin.
    private Debugger debugger = new Debugger(getClass().getName());

    // Constructor to actually give "plugin" a value.
    public LaserGunCommand(LaserGun givenPlugin)
    {
        plugin = givenPlugin;
    }



    // Override the default command. Set the instructions for this particular command (registered in the main class).
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args)
    {
        // Log who is using the command.
        debugger.sendDebugMessage(Level.INFO, "Sender is instance of: " + commandSender.getClass().getName());

        // If the command comes from Console, give a warning.
        boolean senderIsConsole = !(commandSender instanceof Player);

        // Check if there are any args.
        if(args.length == 0)
        {
            MessageUtils.sendColorizedMessage(commandSender, "&7"  + plugin.getName() + " version &6" + plugin.getDescription().getVersion());
            MessageUtils.sendColorizedMessage(commandSender, "&7Coded by &6mind_overflow&7, all rights reserved (&6Copyright © '20&7).");
            commandSender.sendMessage("");
            MessageUtils.sendColorizedMessage(commandSender, "&7Write &6/" + plugin.getName().toLowerCase() + " help&7 to see plugin commands.");
        }
        // Check if there is a single argument after the command itself.
        else if (args.length == 1)
        {
            // Check if the args are "help" and send help message.
            if(args[0].equalsIgnoreCase("help")) {
                HelpCommand.infoCommand(commandSender, plugin);
            }
            // Check if the args are "reload" and in case, do it.
            else if(args[0].equalsIgnoreCase("reload"))
            {
                ReloadCommand.reloadCommand(commandSender, plugin);
            }
            // Check if the args are "textcomponent" and run that command.
            else if (args[0].equalsIgnoreCase("get"))
            {
                // We do not want the console to receive the item, so we're gonna disable it.
                if(senderIsConsole)
                {
                    MessageUtils.sendLocalizedMessage(commandSender, LocalizedMessages.ERROR_CONSOLE_ACCESS_BLOCKED);
                    return true;
                }
                GetCommand.getCommand((Player)commandSender);
            }
        }
        return true;
    }
}
