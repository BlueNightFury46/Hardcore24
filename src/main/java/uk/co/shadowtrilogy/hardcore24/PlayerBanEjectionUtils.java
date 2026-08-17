package uk.co.shadowtrilogy.hardcore24;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Utility class for handling player ejection related to hardcore bans.
 * Provides methods to warn players, schedule ejections, and clear pending ejections.
 */

public class PlayerBanEjectionUtils {

    // TODO: Consider making the ejection grace period adjustable via config.yaml
    private static final long EJECTION_GRACE_SECONDS = 30L;

    /*
     * Warns the player and schedules their ejection from the current world if they are banned.
     * Prevents player from loading into a banned world if they were admin-banned while offline.
     */
    public static void warnAndScheduleEjectionIfNeeded(Player player, PlayerDeathData banData){
        if(player == null || banData == null){
            return;
        }

        if(PlayerBanUtils.isBanElapsed(banData)){
            Hardcore24.deadPlayers.remove(player.getUniqueId());
            clearPendingEjection(player.getUniqueId());
            return;
        }

        String bannedGroupName = Hardcore24.worlds.get(banData.world);
        if(bannedGroupName == null){
            clearPendingEjection(player.getUniqueId());
            return;
        }

        String currentWorldName = player.getWorld() == null ? null : player.getWorld().getName();
        if(currentWorldName == null || !GroupWorldUtils.groupContainsWorld(bannedGroupName, currentWorldName)){
            clearPendingEjection(player.getUniqueId());
            return;
        }

        UUID playerId = player.getUniqueId();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime existingDeadline = Hardcore24.activeEjectionDeadlines.get(playerId);

        if(existingDeadline == null){
            LocalDateTime deadline = now.plusSeconds(EJECTION_GRACE_SECONDS);
            Hardcore24.activeEjectionDeadlines.put(playerId, deadline);
            player.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You will be ejected from this world in " + formatRemainingSeconds(deadline, now) + "...");
            scheduleEjection(playerId, EJECTION_GRACE_SECONDS);
            return;
        }

        if(existingDeadline.isAfter(now)){
            player.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You will be ejected from this world in " + formatRemainingSeconds(existingDeadline, now) + "...");
            return;
        }

        LocalDateTime immediateDeadline = now.plusSeconds(1L);
        Hardcore24.activeEjectionDeadlines.put(playerId, immediateDeadline);
        player.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "Your ejection grace period expired while you were offline. You will be ejected from this world now.");
        scheduleEjection(playerId, 1L);
    }

    public static boolean clearPendingEjection(UUID playerId){
        return Hardcore24.activeEjectionDeadlines.remove(playerId) != null;
    }

    private static void scheduleEjection(UUID playerId, long delaySeconds){
        Hardcore24.plugin.getServer().getScheduler().runTaskLater(Hardcore24.plugin, () -> {
            Player onlinePlayer = Hardcore24.plugin.getServer().getPlayer(playerId);
            if(onlinePlayer == null || !onlinePlayer.isOnline()){
                return;
            }

            PlayerDeathData activeBan = PlayerBanUtils.getActiveBan(playerId);
            if(activeBan == null){
                clearPendingEjection(playerId);
                return;
            }

            String activeGroupName = Hardcore24.worlds.get(activeBan.world);
            if(activeGroupName == null){
                clearPendingEjection(playerId);
                return;
            }

            String liveWorldName = onlinePlayer.getWorld() == null ? null : onlinePlayer.getWorld().getName();
            if(liveWorldName == null || !GroupWorldUtils.groupContainsWorld(activeGroupName, liveWorldName)){
                clearPendingEjection(playerId);
                return;
            }

            Location ejectionLocation = GroupWorldUtils.getGroupRespawnLocation(activeGroupName);
            if(ejectionLocation == null){
                Hardcore24.plugin.getLogger().severe("Failed to eject player \"" + onlinePlayer.getName() + "\" because group respawn location is missing for group \"" + activeGroupName + "\".");
                onlinePlayer.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "Ejection failed because this hardcore group's respawn world is invalid. Please contact an admin.");
                clearPendingEjection(playerId);
                return;
            }

            clearPendingEjection(playerId);
            onlinePlayer.teleport(ejectionLocation);
            onlinePlayer.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "You were ejected from a hardcore world due to an active ban.");
        }, 20L * delaySeconds);
    }

    private static String formatRemainingSeconds(LocalDateTime deadline, LocalDateTime now){
        long remainingMillis = Math.max(0L, Duration.between(now, deadline).toMillis());
        long remainingSeconds = Math.max(1L, (remainingMillis + 999L) / 1000L);
        return remainingSeconds + (remainingSeconds == 1L ? " second" : " seconds");
    }
}
