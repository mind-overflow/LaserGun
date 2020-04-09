package net.mindoverflow.lasergun.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import net.mindoverflow.lasergun.LaserGun;
import net.mindoverflow.lasergun.utils.*;
import org.bukkit.*;
import org.bukkit.block.Block;
//import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.logging.Level;

public class PlayerInteractListener implements Listener
{

    Debugger debugger = new Debugger(getClass().getName());

    private LaserGun plugin;
    public PlayerInteractListener(LaserGun givenPlugin)
    {
        plugin = givenPlugin;
    }
    public static HashMap<Integer, Location>spawnedArrowsLocations = new HashMap<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {

        // Check if the player is right clicking.
        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
        {
            // Initialize the player variable.
            Player player = event.getPlayer();

            // Load the gun name.
            String gunName = MessageUtils.getLocalizedMessage(LocalizedMessages.LASERGUN_NAME, true);

            // Check if the player is holding the laser gun.
            if(player.getInventory().getItemInMainHand().hasItemMeta() && Objects.requireNonNull(player.getInventory().getItemInMainHand().getItemMeta()).getDisplayName().contains(gunName) ||
                    player.getInventory().getItemInOffHand().hasItemMeta() && Objects.requireNonNull(player.getInventory().getItemInOffHand().getItemMeta()).getDisplayName().contains(gunName))
            {
            /*if(player.getInventory().getItemInHand().hasItemMeta() && Objects.requireNonNull(player.getInventory().getItemInHand().getItemMeta()).getDisplayName().contains(gunName))
            {*/
                // Cancel the event.
                event.setCancelled(true);

                // Check if player does not have permissions to use the item and stop.
                if(!PermissionUtils.playerHasPermission(player, Permissions.ITEM_USE))
                {
                    MessageUtils.sendLocalizedMessage(player, LocalizedMessages.ERROR_NO_PERMISSIONS);
                    return;
                }
                // Load the player location and increase it by 1, so it's in the middle of his body.
                Location playerLocation = player.getLocation();
                playerLocation.setY(playerLocation.getY() + 1);

                // Play a sound at the player location, if enabled.
                if(FileUtils.FileType.CONFIG_YAML.yaml.getBoolean(ConfigEntries.SOUND.path))
                {
                    Objects.requireNonNull(playerLocation.getWorld()).playSound(playerLocation, Sound.ENTITY_GHAST_SHOOT, (float)0.5, 2);
                }

                // Create a set of Materials we should be able to shoot through.
                Set<Material> transparent = new HashSet<Material>();
                transparent.add(Material.WATER);
                transparent.add(Material.AIR);
                transparent.add(Material.VOID_AIR);
                transparent.add(Material.CAVE_AIR);
                transparent.add(Material.GRASS);
                transparent.add(Material.TALL_GRASS);
                transparent.add(Material.BAMBOO);

                // Load the target block location (with a max radius) and move it to the middle.
                int radius = FileUtils.FileType.CONFIG_YAML.yaml.getInt(ConfigEntries.RADIUS.path);
                Block targetBlock = player.getTargetBlock(transparent, radius);
                Location targetLocation = targetBlock.getLocation();
                targetLocation.setX(targetLocation.getX() + 0.5);
                targetLocation.setY(targetLocation.getY() + 0.5);
                targetLocation.setZ(targetLocation.getZ() + 0.5);
                debugger.sendDebugMessage(Level.INFO, "Target block: " +targetBlock.getType());

                // Create a vector from the player to the block.
                Vector vector = getDirectionBetweenLocations(playerLocation, targetLocation);

                //vector.multiply(-1); LEGACY!
                // Spawn an arrow to damage entities.
                //Arrow arrow = Objects.requireNonNull(playerLocation.getWorld()).spawnArrow(player.getEyeLocation(), vector, 100, 0); LEGACY!
                Arrow arrow = Objects.requireNonNull(playerLocation.getWorld()).spawnArrow(player.getEyeLocation(), vector, 30, 0);
                //vector.multiply(-1); LEGACY!
                // Set the arrow name.
                arrow.setCustomName("Laser Gun");
                // Remove the arrow sound.
                arrow.setSilent(true);

                // Add the arrow to the spawned list.
                spawnedArrowsLocations.put(arrow.getEntityId(), playerLocation);
                debugger.sendDebugMessage(Level.INFO,"Player loc: " + playerLocation);
                debugger.sendDebugMessage(Level.INFO, "Size: " + spawnedArrowsLocations.size() + ", put ID: " + spawnedArrowsLocations.get(arrow.getEntityId()) + ", ID: " + arrow.getEntityId());
                // Disable arrow pickup.
                arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
                //arrow.setPickupStatus(Arrow.PickupStatus.DISALLOWED); LEGACY!

                // hide the arrow to everyone using packets.
                PacketContainer con = plugin.protocolManager.createPacket(PacketType.Play.Server.ENTITY_DESTROY);
                int[]dest = {arrow.getEntityId()};
                con.getIntegerArrays().write(0, dest);

                try {
                    for(Player p : plugin.getServer().getOnlinePlayers())
                    {
                        plugin.protocolManager.sendServerPacket(p, con);
                        debugger.sendDebugMessage(Level.INFO, "Hidden arrow!");
                    }
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

                // Iterate through every vector position to draw the particles line.
                for(double i = 1.0; i < playerLocation.distance(targetLocation); i += 0.1)
                {
                    vector.multiply(i);
                    playerLocation.add(vector);
                    playerLocation.getWorld().spawnParticle(Particle.REDSTONE, playerLocation, 1, new Particle.DustOptions(Color.RED, 1));
                    //playerLocation.getWorld().spawnParticle(Particle.REDSTONE, playerLocation, 0, 255, 0, 0); LEGACY!
                    playerLocation.subtract(vector);
                    vector.normalize();
                }
            }

        }

    }

    Vector getDirectionBetweenLocations(Location start, Location end) {
        Vector from = start.toVector();
        Vector to = end.toVector();
        return to.subtract(from);
    }


}
