package uk.co.shadowtrilogy.hardcore24.EventHandlers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import uk.co.shadowtrilogy.hardcore24.Hardcore24;


public class PlayerRespawn implements Listener {

    World world_end = Hardcore24.END_WORLD;
    World world = Hardcore24.RESPAWN_WORLD;
    World world_nether = Hardcore24.NETHER_WORLD;

    World world_hardcore = Hardcore24.OVERWORLD;
    double x = Hardcore24.RESPAWN_X;
    double y = Hardcore24.RESPAWN_Y;
    double z = Hardcore24.RESPAWN_Z;


    @EventHandler
    public void Event(PlayerRespawnEvent e) {

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