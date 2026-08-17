package uk.co.shadowtrilogy.hardcore24;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Utility class for handling player bans related to hardcore deaths.
 * Provides methods to check if a ban has elapsed and to retrieve active bans.
 */

public class PlayerBanUtils {

    public static LocalDateTime getUnbanTime(PlayerDeathData data){
        if(data == null){
            return null;
        }

        return LocalDateTime.of(data.deathYear, data.deathMonth, data.deathDayOfMonth, data.deathHour, data.deathMinute, data.deathSecond);
    }

    public static boolean isBanElapsed(PlayerDeathData data){
        LocalDateTime unbanTime = getUnbanTime(data);
        return unbanTime != null && LocalDateTime.now().isAfter(unbanTime);
    }

    // Retrieves the active ban for a player, if it exists and has not elapsed. Returns null otherwise.
    public static PlayerDeathData getActiveBan(UUID playerId){
        PlayerDeathData data = Hardcore24.deadPlayers.get(playerId);
        if(data == null){
            return null;
        }

        if(isBanElapsed(data)){
            Hardcore24.deadPlayers.remove(playerId);
            return null;
        }

        return data;
    }
}