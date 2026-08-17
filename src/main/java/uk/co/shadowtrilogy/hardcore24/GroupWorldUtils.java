package uk.co.shadowtrilogy.hardcore24;

import org.bukkit.Location;
import org.bukkit.World;
import uk.co.shadowtrilogy.hardcore24.json.groups.groupdata;
import uk.co.shadowtrilogy.hardcore24.json.groups.json_location;

/*  Utility class for handling group-related world operations. */

public class GroupWorldUtils {

    // Checks if a group contains a specified world.
    // TODO: PlayerDeathManager.respawn could probably use this method as well?

    public static boolean groupContainsWorld(String groupName, String worldName){
        for(groupdata group : Hardcore24.groups){
            if(group.group_name != null && group.group_name.equalsIgnoreCase(groupName) && group.worlds != null){
                for(String world : group.worlds){
                    if(world != null && world.equalsIgnoreCase(worldName)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Returns the respawn location for a specific group with inbuilt null checks.
    
    public static Location getGroupRespawnLocation(String groupName){
        for(groupdata group : Hardcore24.groups){
            if(group.group_name != null && group.group_name.equalsIgnoreCase(groupName)){
                json_location respawn = group.respawn_location;
                if(respawn == null || !respawn.hasBeenSet){
                    return null;
                }

                World world = Hardcore24.plugin.getServer().getWorld(respawn.world);
                if(world == null){
                    return null;
                }

                return new Location(world, respawn.x, respawn.y, respawn.z);
            }
        }
        return null;
    }
}
