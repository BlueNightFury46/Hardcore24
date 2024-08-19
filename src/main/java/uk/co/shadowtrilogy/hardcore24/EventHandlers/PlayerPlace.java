package uk.co.shadowtrilogy.hardcore24.EventHandlers;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import uk.co.shadowtrilogy.hardcore24.Hardcore24;

public class PlayerPlace implements Listener {

    @EventHandler
    public void Event(BlockPlaceEvent event){



        //Checks if ShulkerPlacement is true
        if(Hardcore24.ShulkerPlacement == true){
            Player player = event.getPlayer();
            //Then gets the player

            //Then checks if the placed block is a shulker box
            if(event.getBlockPlaced().getType().equals(Material.SHULKER_BOX)) {

                //Then checks if they're on the player list
               if(Hardcore24.DamagedPlayers.containsKey(player.getUniqueId())){

                   //Double checks if the event is cancelled
                    if(event.isCancelled() == false) {
                        //Then cancels it
                        event.setCancelled(true);
                    } else {
                        Hardcore24.plugin.getLogger().info("Error code 556, a BlockPlaceEvent was cancelled by another plugin...");
                    }
                }


            }


        }


    }

}
