package uk.co.shadowtrilogy.hardcore24.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import uk.co.shadowtrilogy.hardcore24.Hardcore24;
import uk.co.shadowtrilogy.hardcore24.PlayerDeathData;
import uk.co.shadowtrilogy.hardcore24.json.groups.groupdata;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class PlayerTransportManager implements Listener {

    @EventHandler
    public void world_move(PlayerTeleportEvent ev){

        try{
            if(Hardcore24.deadPlayers.containsKey(ev.getPlayer().getUniqueId())){


                PlayerDeathData data = Hardcore24.deadPlayers.get(ev.getPlayer().getUniqueId());

                LocalDateTime deathTime = LocalDateTime.of(data.deathYear, data.deathMonth, data.deathDayOfMonth, data.deathHour, data.deathMinute, data.deathSecond);

                String group = Hardcore24.worlds.get(data.world);

                System.out.println(deathTime.isAfter(LocalDateTime.now()));
                System.out.println(deathTime.isBefore(LocalDateTime.now()));


                if(LocalDateTime.now().isAfter(deathTime)){

                    Hardcore24.deadPlayers.remove(ev.getPlayer().getUniqueId());
                    ev.getPlayer().sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "Congratulations, you have been unbanned from hardcore... Good luck");


                } else {
                    if(doesGroupContainWorld(group, data.world)) {
                        ev.setCancelled(true);

                        ev.getPlayer().sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You died in hardcore... You will be unbanned at " + deathTime.toString());

                    }

                }



            }

        }catch(NullPointerException ex){

        }




    }


    boolean doesGroupContainWorld(String group, String world){
        ArrayList<groupdata> groups = new ArrayList<>(Hardcore24.groups);
        for(groupdata g : groups){
            if(g.group_name.equalsIgnoreCase(group)){
                ArrayList<String> worlds = new ArrayList<>(g.worlds);
                for(String str : worlds){
                    if(str.equalsIgnoreCase(world)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
