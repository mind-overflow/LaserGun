package net.mindoverflow.lasergun.listeners;

import net.mindoverflow.lasergun.utils.Debugger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.logging.Level;

public class ArrowHitListener implements Listener
{
    Debugger debugger = new Debugger(getClass().getName());
    @EventHandler
    public void onArrowJust(ProjectileHitEvent event)
    {
        debugger.sendDebugMessage(Level.INFO, "Entity type: " + event.getHitEntity());
        if(event.getHitEntity() == null && PlayerInteractListener.spawnedArrowsLocations.containsKey(event.getEntity().getEntityId()))
        {
            PlayerInteractListener.spawnedArrowsLocations.remove(event.getEntity().getEntityId());
            debugger.sendDebugMessage(Level.INFO, "Removed entity as it hit NULL!");
        }
    }
}
