package net.mindoverflow.lasergun.listeners;

import net.mindoverflow.lasergun.LaserGun;
import net.mindoverflow.lasergun.utils.ConfigEntries;
import net.mindoverflow.lasergun.utils.Debugger;
import net.mindoverflow.lasergun.utils.FileUtils;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;
import java.util.logging.Level;

public class ArrowDamageListener implements Listener
{
    Debugger debugger = new Debugger(getClass().getName());

    private LaserGun plugin;
    public ArrowDamageListener(LaserGun givenPlugin)
    {
        plugin = givenPlugin;
    }
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event)
    {
        debugger.sendDebugMessage(Level.INFO, "Arrow damage!");
        debugger.sendDebugMessage(Level.INFO, "ID: " + event.getDamager().getEntityId());
        if(PlayerInteractListener.spawnedArrowsLocations.containsKey(event.getDamager().getEntityId()))
        {
            debugger.sendDebugMessage(Level.INFO, "Arrow is in list! ID: " + event.getDamager().getEntityId());



            Location currentLoc = event.getEntity().getLocation();

            Location spawnLoc = PlayerInteractListener.spawnedArrowsLocations.get(event.getDamager().getEntityId());

            debugger.sendDebugMessage(Level.INFO, "Dist: " + currentLoc.distance(spawnLoc));
            debugger.sendDebugMessage(Level.INFO, "Arrow loc: " + currentLoc);

            // Load the radius.
            int radius = FileUtils.FileType.CONFIG_YAML.yaml.getInt(ConfigEntries.RADIUS.path);
            if(currentLoc.distance(spawnLoc) > radius)
            {
                //debugger.sendDebugMessage(Level.INFO, "Radius too big!");

                event.setDamage(0.0);

                event.setCancelled(true);
            } else
            {
                // Set the arrow damage.
                double damage = FileUtils.FileType.CONFIG_YAML.yaml.getInt(ConfigEntries.DAMAGE.path);
                event.setDamage(damage);

                // Play a "hit" sound.
                Objects.requireNonNull(currentLoc.getWorld()).playSound(currentLoc, Sound.ENTITY_GENERIC_HURT, 1, 1);
                //Objects.requireNonNull(currentLoc.getWorld()).playSound(currentLoc, Sound.HURT_FLESH, 1, 1);  LEGACY!
            }


            PlayerInteractListener.spawnedArrowsLocations.remove(event.getDamager().getEntityId());
            debugger.sendDebugMessage(Level.INFO, "Removed from list!");
            new BukkitRunnable(){
                @Override
                public void run(){
                    event.getDamager().remove();
                    debugger.sendDebugMessage(Level.INFO, "Removed entity!");
                }
            }.runTaskLater(plugin, 1);
        }
    }
}
