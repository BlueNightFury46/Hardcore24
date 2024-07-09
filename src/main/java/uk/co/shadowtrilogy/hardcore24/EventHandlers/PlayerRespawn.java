package uk.co.shadowtrilogy.hardcore24.EventHandlers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import uk.co.shadowtrilogy.hardcore24.Hardcore24;


public class PlayerRespawn implements Listener {


    @EventHandler
    public void Event(EntityDamageEvent e){


        
        
        Player _player = (Player) e.getEntity();
        FileConfiguration fileConfiguration = Hardcore24.plugin.getConfig();

        World world = Bukkit.getWorld(fileConfiguration.get("respawn-location.world").toString());
        double x = fileConfiguration.getDouble("respawn-location.x");
        double y = fileConfiguration.getDouble("respawn-location.y");
        double z = fileConfiguration.getDouble("respawn-location.z");

        World world_nether = Bukkit.getWorld(fileConfiguration.getString("hardcore-world.hardcore-nether"));
        World world_hardcore = Bukkit.getWorld(fileConfiguration.getString("hardcore-world.hardcore-normal"));
        World world_end = Bukkit.getWorld(fileConfiguration.getString("hardcore-world.hardcore-end"));


        if (_player.getWorld().equals(world_hardcore) || _player.getWorld().equals(world_end) || _player.getWorld().equals(world_nether)) {
            Bukkit.getScheduler().runTaskLater(Hardcore24.plugin, () -> {
                if (_player.getWorld().equals(world)) {

                    _player.teleport(new Location(world, x, y, z));
                }
            }, 2L);

          //Second telport check, if player is in one of the 3 hardcore worlds and dead, teleport back
            Bukkit.getScheduler().runTaskLater(Hardcore24.plugin, () -> {
                if (Hardcore24.map.containsKey(_player.getUniqueId()) && _player.getWorld().equals(world_hardcore) || Hardcore24.map.containsKey(_player.getUniqueId()) && _player.getWorld().equals(world_end) || Hardcore24.map.containsKey(_player.getUniqueId()) && _player.getWorld().equals(world_nether)) {

                    _player.teleport(new Location(world, x, y, z));
                }
            }, 5L);

            Bukkit.getScheduler().runTaskLater(Hardcore24.plugin, () -> {
                if (Hardcore24.map.containsKey(_player.getUniqueId()) && _player.getWorld().equals(world_hardcore) || Hardcore24.map.containsKey(_player.getUniqueId()) && _player.getWorld().equals(world_end) || Hardcore24.map.containsKey(_player.getUniqueId()) && _player.getWorld().equals(world_nether)) {

                    _player.teleport(new Location(world, x, y, z));
                }
            }, 20L);


        }
    }
}
