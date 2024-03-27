package uk.co.shadowtrilogy.hardcore24.EventHandlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import uk.co.shadowtrilogy.hardcore24.Hardcore24;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDamage implements Listener {




    @EventHandler
    public void event(EntityDamageEvent event){
        //Checks if it's a player
        if(event.getEntityType().equals(EntityType.PLAYER)){
            //If true double checks if ShulkerPlacement is true
            if(Hardcore24.ShulkerPlacement == true) {
                //Then gets the player
                Player player = (Player) event.getEntity();
                //After that checks if it's an operator
                if (player.isOp()) {
                    //Then gets their ping
                    long l = player.getPing() / 1000;
                    l *= 20;

                    //Then saves them to a list
                    Hardcore24.DamagedPlayers.put(player.getUniqueId(), player.getName());


                    Bukkit.getScheduler().runTaskLater(Hardcore24.plugin, () -> {
                        Hardcore24.DamagedPlayers.remove(player.getUniqueId());


                    }, 80L + l);
                    //and removes them after 4 seconds (plus their ping in ticks per second)

                }
            }

        }



    }
}
