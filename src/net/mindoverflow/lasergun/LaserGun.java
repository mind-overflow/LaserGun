package net.mindoverflow.lasergun;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import net.mindoverflow.lasergun.commands.LaserGunCommand;
import net.mindoverflow.lasergun.completers.InfoCompleter;
import net.mindoverflow.lasergun.listeners.*;
import net.mindoverflow.lasergun.utils.Debugger;
import net.mindoverflow.lasergun.utils.FileUtils;
import net.mindoverflow.lasergun.utils.MessageUtils;
import net.mindoverflow.lasergun.utils.stats.UpdateChecker;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LaserGun extends JavaPlugin
{
    // Instantiate a Debugger for this class.
    private Debugger debugger = new Debugger(getClass().getName());

    // Initializing needed variables.
    public static Logger logger;
    private PluginManager pluginManager;
    public ProtocolManager protocolManager;
    public UpdateChecker updateChecker;

    // Method called when the plugin is being loaded.
    @Override
    public void onEnable()
    {

        // Give the initialized variables their respective values. Absolutely don't do this before as they will look good in the IDE but will result null.
        logger = getLogger();
        pluginManager = getServer().getPluginManager();

        // Check and report if the Debugger is enabled (the method itself does the checking). Would do it before but we need the logger to be initialized! :(
        debugger.sendDebugMessage(Level.WARNING, "---[ DEBUGGER IS ENABLED! ]---");
        debugger.sendDebugMessage(Level.WARNING, "---[ INITIALIZING PLUGIN ]---");
        debugger.sendDebugMessage(Level.INFO, "Logger and PluginManager already initialized.");

        // Register instances and give them the plugin parameter (this, because this class is the JavaPlugin) so they can access all of its info.
        debugger.sendDebugMessage(Level.INFO, "Instantiating some classes that need to access to plugin data...");
        FileUtils fileUtilsInstance = new FileUtils(this);
        PlayerJoinListener playerJoinListenerInstance = new PlayerJoinListener(this);
        LaserGunCommand laserGunCommandInstance = new LaserGunCommand(this);
        MessageUtils messageUtils = new MessageUtils(this);
        updateChecker = new UpdateChecker(this);
        debugger.sendDebugMessage(Level.INFO, "Done instantiating classes!");

        // Register Listeners
        debugger.sendDebugMessage(Level.INFO, "Registering listeners...");
        pluginManager.registerEvents(playerJoinListenerInstance, this);
        pluginManager.registerEvents(new PlayerInteractListener(this), this);
        pluginManager.registerEvents(new ArrowDamageListener(this), this);
        pluginManager.registerEvents(new ArrowHitListener(), this);
        debugger.sendDebugMessage(Level.INFO, "Done registering listeners!");

        // Register Commands
        debugger.sendDebugMessage(Level.INFO, "Registering commands...");
        getCommand("lasergun").setExecutor(laserGunCommandInstance);
        getCommand("lasergun").setTabCompleter(new InfoCompleter());
        debugger.sendDebugMessage(Level.INFO, "Done registering commands!");

        // Check if all needed files exist and work correctly, also loading their YAMLs.
        debugger.sendDebugMessage(Level.INFO, "Checking files...");
        FileUtils.checkFiles();
        debugger.sendDebugMessage(Level.INFO, "Done checking files!");

        /*
        Load all the YAML files. We are already loading them in FileUtils's checkFiles() method but we are loading them singularly.
        With this method we are sure that all the files get successfully loaded. Better twice than never...
         */
        debugger.sendDebugMessage(Level.INFO, "Reloading YAML config...");
        FileUtils.reloadYamls();
        debugger.sendDebugMessage(Level.INFO, "Done!");

        // Loading ProtocolManager
        debugger.sendDebugMessage(Level.INFO, "Loading ProtocolManager...!");
        if(pluginManager.getPlugin("ProtocolLib") == null)
        {
            getServer().getScheduler().runTask(this, () ->
            {
                CommandSender console = getServer().getConsoleSender();
                String pluginName = getName();
                MessageUtils.sendColorizedMessage(console, pluginName + ": &c-------------[ &4" + pluginName + "&c ]-------------");
                MessageUtils.sendColorizedMessage(console, pluginName + ": &cWARNING! This plugin needs ProtocolLib to work correctly.");
                MessageUtils.sendColorizedMessage(console, pluginName + ": &cThe plugin will now be disabled.");
                MessageUtils.sendColorizedMessage(console, pluginName + ": &cDownload ProtocolLib at: &nhttp://bit.ly/38PMAy3");
                pluginManager.disablePlugin(this);
            });
            return;
        }

        protocolManager = ProtocolLibrary.getProtocolManager();
        debugger.sendDebugMessage(Level.INFO, "Done!");

        // Send success output message to console.
        logger.log(Level.INFO, "Plugin " + getDescription().getName() + " Successfully Loaded!");
        debugger.sendDebugMessage(Level.WARNING, "---[ INITIALIZATION DONE ]---");

    }

    // Method called when the plugin is being unloaded.
    @Override
    public void onDisable() {
        debugger.sendDebugMessage(Level.WARNING, "---[ DEBUGGER IS ENABLED! ]---");
        debugger.sendDebugMessage(Level.WARNING, "---[ DISABLING PLUGIN ]---");
        logger.log(Level.INFO, "Plugin " + getDescription().getName() + " Successfully Unloaded!");
        debugger.sendDebugMessage(Level.WARNING, "---[ PLUGIN DISABLED ]---");
    }


}