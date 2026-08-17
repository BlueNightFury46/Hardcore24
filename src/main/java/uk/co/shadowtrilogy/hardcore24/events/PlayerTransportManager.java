package uk.co.shadowtrilogy.hardcore24.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import uk.co.shadowtrilogy.hardcore24.GroupWorldUtils;
import uk.co.shadowtrilogy.hardcore24.Hardcore24;
import uk.co.shadowtrilogy.hardcore24.PlayerBanEjectionUtils;
import uk.co.shadowtrilogy.hardcore24.PlayerBanUtils;
import uk.co.shadowtrilogy.hardcore24.PlayerDeathData;

import java.time.LocalDateTime;

/**
 * Listens for player teleport events and prevents players from entering worlds where they are currently banned.
 * Notifies players when their ban has elapsed.
 */

public class PlayerTransportManager implements Listener {

    @EventHandler
    public void world_move(PlayerTeleportEvent ev){

        try{
            if(Hardcore24.deadPlayers.containsKey(ev.getPlayer().getUniqueId())){


                PlayerDeathData data = PlayerBanUtils.getActiveBan(ev.getPlayer().getUniqueId());

                // If there is no active ban, clear any pending ejection and notify the player of their unban.
                if(data == null){
                    PlayerBanEjectionUtils.clearPendingEjection(ev.getPlayer().getUniqueId());
                    ev.getPlayer().sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "Congratulations, you have been unbanned from hardcore... Good luck");
                    return;
                }

                String playerName = ev.getPlayer().getName();

                LocalDateTime deathTime = PlayerBanUtils.getUnbanTime(data);

                String group = Hardcore24.worlds.get(data.world);

                LocalDateTime now = LocalDateTime.now();
                boolean banElapsed = PlayerBanUtils.isBanElapsed(data);
                Hardcore24.plugin.getLogger().info("Teleport ban-time check for " + playerName + ": expires=" + deathTime + ", now=" + now + ", elapsed=" + banElapsed);


                if(banElapsed){
                    // Remove the player from the list of dead players as their ban has elapsed, and notify
                    Hardcore24.deadPlayers.remove(ev.getPlayer().getUniqueId());
                    ev.getPlayer().sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "Congratulations, you have been unbanned from hardcore... Good luck");


                } else {
                    // Check if the player is attempting to teleport to a world within the banned group.
                    // If so, cancel the teleport and notify the player of their ban.
                    String destinationWorld = ev.getTo() != null && ev.getTo().getWorld() != null
                    ? ev.getTo().getWorld().getName() : null;

                    boolean destinationInBannedGroup = destinationWorld != null && GroupWorldUtils.groupContainsWorld(group, destinationWorld);
                    Hardcore24.plugin.getLogger().info("Teleport destination group check for " + playerName + ": destination=" + destinationWorld + ", bannedGroup=" + group + ", inBannedGroup=" + destinationInBannedGroup);

                    if (destinationInBannedGroup) {
                       ev.setCancelled(true);
                        ev.getPlayer().sendMessage(ChatColor.RED + "" + ChatColor.ITALIC
                            + "You died in hardcore... You will be unbanned at " + deathTime);
                    }

                }

            }

        }catch(NullPointerException ex){

        }




    }
}
