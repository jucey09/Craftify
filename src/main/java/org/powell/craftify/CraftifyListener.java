package org.powell.craftify;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ShapedRecipe;

public class CraftifyListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){


    if (e.getInventory().getSize() == 45 && e.getCurrentItem() != null && e.getView().getTitle() == ChatColor.GOLD + "Craftify Menu") {
        Player player = (Player) e.getWhoClicked();

        e.setCancelled(true);
        switch (e.getRawSlot()){
            case 0:
                break;
            case 44:

                break;
            default:
                return;

            }
            player.closeInventory();
        }
    }
    //ShapedRecipe recipe = new ShapedRecipe()
    //10,11,12,19,20,21,28,29,30 output 24
}
