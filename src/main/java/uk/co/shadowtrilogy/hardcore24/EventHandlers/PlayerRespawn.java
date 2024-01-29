package uk.co.shadowtrilogy.hardcore24.EventHandlers;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import uk.co.shadowtrilogy.hardcore24.Hardcore24;


public class PlayerRespawn implements Listener {


    @EventHandler
    public void Event(PlayerPostRespawnEvent e){
        FileConfiguration fileConfiguration = Hardcore24.plugin.getConfig();

        World world = Bukkit.getWorld(fileConfiguration.get("respawn-location.world").toString());
        double x = fileConfiguration.getDouble("respawn-location.x");
        double y = fileConfiguration.getDouble("respawn-location.y");
        double z = fileConfiguration.getDouble("respawn-location.z");

        World world_nether = Bukkit.getWorld(fileConfiguration.get("hardcore-world.hardcore-nether").toString());
        World world_hardcore = Bukkit.getWorld(fileConfiguration.get("hardcore-world.hardcore-normal").toString());
        World world_end = Bukkit.getWorld(fileConfiguration.get("hardcore-world.hardcore-end").toString());


        if (e.getPlayer().getWorld().equals(world_hardcore) || e.getPlayer().getWorld().equals(world_end) || e.getPlayer().getWorld().equals(world_nether)) {
            Bukkit.getScheduler().runTaskLater(Hardcore24.plugin, () -> {
                if (e.getPlayer().getWorld().equals(world)) {

                    e.getPlayer().teleport(new Location(world, x, y, z));
                }
            }, 2L);

            Bukkit.getScheduler().runTaskLater(Hardcore24.plugin, () -> {
                if (e.getPlayer().getWorld().equals(world_hardcore) || e.getPlayer().getWorld().equals(world_end) || e.getPlayer().getWorld().equals(world_nether)) {

                    e.getPlayer().teleport(new Location(world, x, y, z));
                }
            }, 5L);

            Bukkit.getScheduler().runTaskLater(Hardcore24.plugin, () -> {
                if (e.getPlayer().getWorld().equals(world_hardcore) || e.getPlayer().getWorld().equals(world_end) || e.getPlayer().getWorld().equals(world_nether)) {

                    e.getPlayer().teleport(new Location(world, x, y, z));
                }
            }, 20L);


        }
    }
}
