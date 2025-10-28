package uk.co.shadowtrilogy.hardcore24.events;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import uk.co.shadowtrilogy.hardcore24.Hardcore24;
import uk.co.shadowtrilogy.hardcore24.PlayerDeathData;
import uk.co.shadowtrilogy.hardcore24.json.groups.groupdata;
import uk.co.shadowtrilogy.hardcore24.json.groups.json_location;

import java.time.LocalDateTime;

public class PlayerDeathManager implements Listener {

    @EventHandler
    public void death(org.bukkit.event.entity.PlayerDeathEvent ev){





        if(!ev.getPlayer().hasPermission("hardcore.immortal")){
            try {
                if (!Hardcore24.deadPlayers.containsKey(ev.getPlayer().getUniqueId())) {

                //    validDeathLogic(ev);
                    Hardcore24.deadPlayers.put(ev.getPlayer().getUniqueId(), new PlayerDeathData(ev.getPlayer().getWorld(), LocalDateTime.now()));

                    //teleport.teleportToRespawnPoint(ev.getPlayer().getWorld(), ev.getPlayer());

                    //TODO fix timezones probably
                    Hardcore24.deadPlayers.put(ev.getPlayer().getUniqueId(), new PlayerDeathData(ev.getPlayer().getWorld(), LocalDateTime.now()));

                    try {
                        if (Hardcore24.DROP_ENDERCHEST && !ev.getKeepInventory()) {

                            if(ev.getPlayer().getEnderChest().isEmpty()){
                                return;
                            }

                            for (ItemStack i : ev.getPlayer().getEnderChest()) {
                                ev.getPlayer().getEnderChest().removeItem(i);
                                ev.getDrops().add(i);
                            }

                        }
                    } catch(NullPointerException ex){

                    }

                }

            } catch(NullPointerException ex){
            //    validDeathLogic(ev);
                Hardcore24.deadPlayers.put(ev.getPlayer().getUniqueId(), new PlayerDeathData(ev.getPlayer().getWorld(), LocalDateTime.now()));
                //teleport.teleportToRespawnPoint(ev.getPlayer().getWorld(), ev.getPlayer());

                //TODO fix timezones probably
                Hardcore24.deadPlayers.put(ev.getPlayer().getUniqueId(), new PlayerDeathData(ev.getPlayer().getWorld(), LocalDateTime.now()));

                try {
                    if (Hardcore24.DROP_ENDERCHEST && !ev.getKeepInventory()) {

                        if(ev.getPlayer().getEnderChest().isEmpty()){
                            return;
                        }

                        for (ItemStack i : ev.getPlayer().getEnderChest()) {
                            ev.getPlayer().getEnderChest().removeItem(i);
                            ev.getDrops().add(i);
                        }

                    }
                } catch(NullPointerException e){

                }

            }




        }


    }

    @EventHandler
    public void respawn(PlayerRespawnEvent ev){


        Player player = ev.getPlayer();
        World world = player.getWorld();

        try {
            if (Hardcore24.worlds.containsKey(world.getName())) {

                String group_name = Hardcore24.worlds.get(world.getName());

                for(groupdata g : Hardcore24.groups){
                    if(g.group_name.equalsIgnoreCase(group_name)){
                        if(!g.respawn_location.hasBeenSet){
                            if(player.isOp() && Hardcore24.WARNINGS){
                                player.sendMessage(ChatColor.RED+"Warning! The respawn location for group \"" + g.group_name + "\" has not been set...");
                                Hardcore24.plugin.getLogger().warning("Warning! The respawn location for group \"" + g.group_name + "\" has not been set...");

                            } else if(Hardcore24.WARNINGS) {
                                Hardcore24.plugin.getLogger().warning("Warning! The respawn location for group \"" + g.group_name + "\" has not been set...");
                            }
                        }

                        json_location loc = g.respawn_location;

                        try{
                            World w = Hardcore24.plugin.getServer().getWorld(loc.world);
                            ev.setRespawnLocation(new Location(w, loc.x, loc.y, loc.z));

                        }catch(NullPointerException ex){
                            Hardcore24.plugin.getLogger().severe("Warning! World for teleport does not exist (how have you managed that then?)");
                            return;
                        }

                    }
                }



            }
        }catch(NullPointerException ex){
            if(player.isOp() && Hardcore24.WARNINGS){
                player.sendMessage(ChatColor.YELLOW+"Warning! world \"" + world.getName() + "\" has not been configured as part of a group...");
                Hardcore24.plugin.getLogger().warning("Warning! world \"" + world.getName() + "\" has not been configured as part of a group...");

            } else if(Hardcore24.WARNINGS) {
                Hardcore24.plugin.getLogger().warning("Warning! world \"" + world.getName() + "\" has not been configured as part of a group...");
            }
        }

    }
}
