package net.mindoverflow.lasergun.commands.laserguncommands;

import net.mindoverflow.lasergun.utils.LocalizedMessages;
import net.mindoverflow.lasergun.utils.MessageUtils;
import net.mindoverflow.lasergun.utils.PermissionUtils;
import net.mindoverflow.lasergun.utils.Permissions;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GetCommand
{

    public static void getCommand(Player player)
    {
        if(!PermissionUtils.playerHasPermission(player, Permissions.ITEM_GET))
        {
            MessageUtils.sendLocalizedMessage(player, LocalizedMessages.ERROR_NO_PERMISSIONS);
            return;
        }

        /*Material itemMat;
        if(Material.getMaterial("REPEATER") != null)
        {
            itemMat = Material.getMaterial("REPEATER");
        }
        else
        {
            itemMat = Material.getMaterial("REDSTONE_COMPARATOR");
        }*/

        // Material itemMat = Material.REDSTONE_COMPARATOR; LEGACY!
        Material itemMat = Material.REPEATER;
        ItemStack lasergunItem = new ItemStack(itemMat);
        ItemMeta lasergunIM = lasergunItem.getItemMeta();

        lasergunIM.setDisplayName(MessageUtils.getLocalizedMessage(LocalizedMessages.LASERGUN_NAME, true));

        ArrayList<String>lore = new ArrayList<String>();
        lore.add(MessageUtils.getLocalizedMessage(LocalizedMessages.LASERGUN_LORE, true));
        lasergunIM.setLore(lore);



        lasergunItem.setItemMeta(lasergunIM);
        player.getInventory().addItem(lasergunItem);
    }
}
