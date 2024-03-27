package uk.co.shadowtrilogy.hardcore24.EventHandlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import uk.co.shadowtrilogy.hardcore24.Hardcore24;

public class InventoryOpenEvent implements Listener {

    @EventHandler
    public void Event(org.bukkit.event.inventory.InventoryOpenEvent event){


        //Checks the inventory type
        if(event.getInventory().getType().equals(InventoryType.CHEST) || event.getInventory().getType().equals(InventoryType.CHEST)){

            //Then checks if ShulkerPlacement is true
            if(Hardcore24.ShulkerPlacement == true){
                //Checks if the player recently took damage
                if(Hardcore24.DamagedPlayers.containsKey(event.getPlayer().getUniqueId())){

                    //Checks if the event was already cancelled
                    if(event.isCancelled() == false){
                        event.setCancelled(true);

                    } else {
                        Hardcore24.plugin.getLogger().info("Error code 555, an InventoryOpenEvent was cancelled by another plugin...");
                    }


                }



            }


        }



    }



}
