package uk.co.shadowtrilogy.hardcore24.events;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import uk.co.shadowtrilogy.hardcore24.GroupWorldUtils;
import uk.co.shadowtrilogy.hardcore24.Hardcore24;
import uk.co.shadowtrilogy.hardcore24.PlayerDeathData;
import uk.co.shadowtrilogy.hardcore24.json.groups.groupdata;

import java.time.LocalDateTime;

public class PlayerDeathManager implements Listener {

    @EventHandler
    public void death(org.bukkit.event.entity.PlayerDeathEvent ev){





        if(!ev.getPlayer().hasPermission("hardcore.immortal")){
            // If the death occurred in a world that is not tracked by Hardcore24,
            // do not apply a ban and simply allow default death behavior to proceed
            String deathWorldName = ev.getPlayer().getWorld().getName();
            if(!Hardcore24.worlds.containsKey(deathWorldName)){
                return;
            }

            try {
                if (!Hardcore24.deadPlayers.containsKey(ev.getPlayer().getUniqueId())) {

                //    validDeathLogic(ev);
                    Hardcore24.deadPlayers.put(ev.getPlayer().getUniqueId(), new PlayerDeathData(ev.getPlayer().getWorld(), LocalDateTime.now()));

                    //teleport.teleportToRespawnPoint(ev.getPlayer().getWorld(), ev.getPlayer());

                    //TODO fix timezones probably
                    sendDeathBanNotification(ev.getPlayer());

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
                sendDeathBanNotification(ev.getPlayer());

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
                        if(g.respawn_location == null || !g.respawn_location.hasBeenSet){
                            if(player.isOp() && Hardcore24.WARNINGS){
                                player.sendMessage(ChatColor.RED+"Warning! The respawn location for group \"" + g.group_name + "\" has not been set...");
                                Hardcore24.plugin.getLogger().warning("Warning! The respawn location for group \"" + g.group_name + "\" has not been set...");

                            } else if(Hardcore24.WARNINGS) {
                                Hardcore24.plugin.getLogger().warning("Warning! The respawn location for group \"" + g.group_name + "\" has not been set...");
                            }
                            return;
                        }

                        Location respawnLocation = GroupWorldUtils.getGroupRespawnLocation(g.group_name);
                        if(respawnLocation == null){
                            Hardcore24.plugin.getLogger().severe("Warning! Respawn location for group \"" + g.group_name + "\" is invalid. Check groups.json and ensure respawn world exists.");
                            player.sendMessage(ChatColor.RED + "Warning! Hardcore respawn configuration is invalid. Using server default respawn...");
                            return;
                        }

                        ev.setRespawnLocation(respawnLocation);
                        return;

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

    private void sendDeathBanNotification(Player player){
        PlayerDeathData data = Hardcore24.deadPlayers.get(player.getUniqueId());
        if(data == null){
            return;
        }

        Hardcore24.pendingPlayerNotifications.remove(player.getUniqueId());
        LocalDateTime unbanTime = LocalDateTime.of(data.deathYear, data.deathMonth, data.deathDayOfMonth, data.deathHour, data.deathMinute, data.deathSecond);
        player.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You are now banned from hardcore. Unban time: " + unbanTime);
    }
}
