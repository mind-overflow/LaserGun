package net.mindoverflow.lasergun.commands.laserguncommands;

import net.mindoverflow.lasergun.LaserGun;
import net.mindoverflow.lasergun.utils.ConfigEntries;
import net.mindoverflow.lasergun.utils.FileUtils;
import net.mindoverflow.lasergun.utils.LocalizedMessages;
import net.mindoverflow.lasergun.utils.MessageUtils;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class HelpCommand
{
    public static void infoCommand(CommandSender commandSender, LaserGun plugin)
    {

        String cmdName = "&e/" + plugin.getName().toLowerCase();
        commandSender.sendMessage(MessageUtils.colorize("&7---------[&c " + plugin.getName() + " " + plugin.getDescription().getVersion() + " &7]---------"));
        commandSender.sendMessage(MessageUtils.colorize(cmdName + " help&7: see this help page"));
        commandSender.sendMessage(MessageUtils.colorize(cmdName + " get&7: get the Laser Gun&8 - lasergun.getgun"));
        commandSender.sendMessage(MessageUtils.colorize(cmdName + " reload&7: reload the config&8 - lasergun.reload"));
    }
}
