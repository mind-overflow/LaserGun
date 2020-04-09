package net.mindoverflow.lasergun.utils;

import net.mindoverflow.lasergun.LaserGun;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class PermissionUtils
{

    // Initialize the Debugger instance.
    private static Debugger debugger = new Debugger(PermissionUtils.class.getName());


    private static LaserGun plugin;
    public PermissionUtils(LaserGun givenPlugin)  { plugin = givenPlugin;  }

    // Method to get the permission string from the Permissions enum.
    public static boolean playerHasPermission(String username, Permissions permission)
    {
        debugger.sendDebugMessage(Level.INFO, "Permission: " + permission.permission + "; Player name is: " + username);
        Player user = plugin.getServer().getPlayer(username);
        {
            if (user != null && user.hasPermission(permission.permission))
            {
                return true;
            }
        }
        return false;
    }


    public static boolean playerHasPermission(CommandSender sender, Permissions permission)
    {
        debugger.sendDebugMessage(Level.INFO, "Permission: " + permission.permission + "; Player name is: " + sender.getName());
        {
            if(sender.hasPermission(permission.permission) || sender instanceof ConsoleCommandSender)
            {
                return true;
            }
        }
        return false;
    }
}
