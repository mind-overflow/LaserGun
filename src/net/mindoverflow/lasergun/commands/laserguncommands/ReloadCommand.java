package net.mindoverflow.lasergun.commands.laserguncommands;

import net.mindoverflow.lasergun.LaserGun;
import net.mindoverflow.lasergun.utils.*;
import org.bukkit.command.CommandSender;

import java.util.logging.Level;

public class ReloadCommand
{
    private static Debugger debugger = new Debugger(ReloadCommand.class.getName());

    public static void reloadCommand(CommandSender commandSender, LaserGun plugin)
    {

        if(!PermissionUtils.playerHasPermission(commandSender, Permissions.COMMAND_RELOAD))
        {

            MessageUtils.sendLocalizedMessage(commandSender, LocalizedMessages.ERROR_NO_PERMISSIONS);
            return;
        }

        debugger.sendDebugMessage(Level.INFO, "Reloading YAMLS...");
        MessageUtils.sendColorizedMessage(commandSender, "&7Reloading &e" + plugin.getName() + "&7 v.&e" + plugin.getDescription().getVersion() + "&7...");
        FileUtils.checkFiles();
        FileUtils.reloadYamls();
        MessageUtils.sendColorizedMessage(commandSender, "&eReloaded!");
        debugger.sendDebugMessage(Level.INFO, "Reloaded YAMLs!");

        // This method checks if player has permissions, checks error codes, and acts accordingly.
        plugin.updateChecker.playerMessage(commandSender);
    }
}
