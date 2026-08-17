package uk.co.shadowtrilogy.hardcore24.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import uk.co.shadowtrilogy.hardcore24.Hardcore24;
import uk.co.shadowtrilogy.hardcore24.PlayerBanUtils;
import uk.co.shadowtrilogy.hardcore24.PlayerBanEjectionUtils;
import uk.co.shadowtrilogy.hardcore24.PlayerDeathData;

import java.util.UUID;

/**
 * Manages player notifications related to hardcore bans and ejections.
 * Listens for player join events and delivers any pending messages or handles ban status.
 */

public class PlayerNotificationManager implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent ev){
        UUID playerId = ev.getPlayer().getUniqueId();
        PlayerDeathData rawBan = Hardcore24.deadPlayers.get(playerId);

        // Check if the player had a recent ban that has now expired. If so, notify them of their unban.
        if(rawBan != null && PlayerBanUtils.getActiveBan(playerId) == null){
            Hardcore24.pendingPlayerNotifications.remove(playerId);
            PlayerBanEjectionUtils.clearPendingEjection(playerId);
            ev.getPlayer().sendMessage("Congratulations, you have been unbanned from hardcore... Good luck");
            return;
        }

        // Retrieve any pending notification message for the player.
        String pendingMessage = Hardcore24.pendingPlayerNotifications.remove(playerId);
        if(pendingMessage != null && !pendingMessage.isEmpty()){
            ev.getPlayer().sendMessage(pendingMessage);
        }

        // If player is loading into a world where they are currently banned, handle their ejection.
        PlayerDeathData activeBan = PlayerBanUtils.getActiveBan(playerId);
        if(activeBan != null){
            PlayerBanEjectionUtils.warnAndScheduleEjectionIfNeeded(ev.getPlayer(), activeBan);
        }
    }
}
