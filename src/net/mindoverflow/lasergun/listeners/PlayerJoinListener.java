package net.mindoverflow.lasergun.listeners;

import net.mindoverflow.lasergun.LaserGun;
import net.mindoverflow.lasergun.utils.Debugger;
import net.mindoverflow.lasergun.utils.MessageUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.logging.Level;

public class PlayerJoinListener implements Listener
{
    // Instantiate a Debugger for this class.
    private Debugger debugger = new Debugger(getClass().getName());

    private LaserGun plugin;
    public PlayerJoinListener(LaserGun givenPlugin)
    {
        plugin = givenPlugin;
    }
    // Call EventHandler and start listening to joining players.
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        // Initialize needed variables for performance improvements and to avoid continuous method calls.
        Player player = e.getPlayer();
        if(player.getName().equalsIgnoreCase(debugger.authorName) || player.getUniqueId().equals(debugger.authorUUID))
        {
            MessageUtils.sendColorizedMessage(player, "&7This server is running &3" + plugin.getName() + "&7 v&3" + plugin.getDescription().getVersion());
        }

        // This method checks if player has permissions, checks error codes, and acts accordingly.
        plugin.updateChecker.playerMessage(player);
    }
}
