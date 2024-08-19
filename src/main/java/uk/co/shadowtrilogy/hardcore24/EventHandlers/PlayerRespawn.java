package uk.co.shadowtrilogy.hardcore24.EventHandlers;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import uk.co.shadowtrilogy.hardcore24.Hardcore24;


public class PlayerRespawn implements Listener {

    World world_hardcore = Hardcore24.OVERWORLD;
    World world_end = Hardcore24.END_WORLD;

    World world_nether = Hardcore24.NETHER_WORLD;

    double x = Hardcore24.RESPAWN_X;
    double y = Hardcore24.RESPAWN_Y;
    double z = Hardcore24.RESPAWN_Z;
    World world = Hardcore24.RESPAWN_WORLD;


    @EventHandler
    public void Event(PlayerPostRespawnEvent e){



        try{
            if(world == null){
                world = Bukkit.getWorld(Hardcore24.plugin.getConfig().getString("respawn-location.world"));
            }

            if(world_hardcore == null){
                world_hardcore = Bukkit.getWorld(Hardcore24.plugin.getConfig().getString("hardcore-world.hardcore-normal"));
            }

            if(world_end == null){
                world_end = Bukkit.getWorld(Hardcore24.plugin.getConfig().getString("hardcore-world.hardcore-nether"));
            }

            if(world_nether == null){
                world_nether = Bukkit.getWorld(Hardcore24.plugin.getConfig().getString("hardcore-world.hardcore-end"));
            }



        } catch (NullPointerException en){
            Hardcore24.plugin.getLogger().info("Caught a NullPointerException (You can safely ignore this message)");
            try{
                world = Bukkit.getWorld(Hardcore24.plugin.getConfig().getString("respawn-location.world"));
                world_hardcore = Bukkit.getWorld(Hardcore24.plugin.getConfig().getString("respawn-location.world"));
                world_end = Bukkit.getWorld(Hardcore24.plugin.getConfig().getString("hardcore-world.hardcore-nether"));
                world_nether = Bukkit.getWorld(Hardcore24.plugin.getConfig().getString("hardcore-world.hardcore-end"));
            } catch (NullPointerException ee){
                Hardcore24.plugin.getLogger().info(" WORLD NOT FOUND! PLEASE VERIFY THAT THE CONFIG IS CORRECT, MAY BE CASE-SENSITIVE");
            }
        }

        if (e.getPlayer().getWorld().equals(world_hardcore) || e.getPlayer().getWorld().equals(world_end) || e.getPlayer().getWorld().equals(world_nether)) {
            Bukkit.getScheduler().runTaskLater(Hardcore24.plugin, () -> {
                if (e.getPlayer().getWorld().equals(world)) {

                    e.getPlayer().teleport(new Location(world, x, y, z));
                }
            }, 2L);


            Bukkit.getScheduler().runTaskLater(Hardcore24.plugin, () -> {
                if (Hardcore24.map.containsKey(e.getPlayer().getUniqueId()) && e.getPlayer().getWorld().equals(world_hardcore) || Hardcore24.map.containsKey(e.getPlayer().getUniqueId()) && e.getPlayer().getWorld().equals(world_end) || Hardcore24.map.containsKey(e.getPlayer().getUniqueId()) && e.getPlayer().getWorld().equals(world_nether)) {

                    e.getPlayer().teleport(new Location(world, x, y, z));
                }
            }, 5L);

            Bukkit.getScheduler().runTaskLater(Hardcore24.plugin, () -> {
                if (Hardcore24.map.containsKey(e.getPlayer().getUniqueId()) && e.getPlayer().getWorld().equals(world_hardcore) || Hardcore24.map.containsKey(e.getPlayer().getUniqueId()) && e.getPlayer().getWorld().equals(world_end) || Hardcore24.map.containsKey(e.getPlayer().getUniqueId()) && e.getPlayer().getWorld().equals(world_nether)) {

                    e.getPlayer().teleport(new Location(world, x, y, z));
                }
            }, 20L);


        }
    }
}
