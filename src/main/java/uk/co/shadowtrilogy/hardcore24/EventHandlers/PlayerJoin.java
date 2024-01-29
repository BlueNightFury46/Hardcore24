package uk.co.shadowtrilogy.hardcore24.EventHandlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.PermissionAttachment;
import uk.co.shadowtrilogy.hardcore24.Hardcore24;

import java.io.IOException;
import java.util.Scanner;

public class PlayerJoin implements Listener {


    //public static HashMap<String, UUID> M;
    Scanner s;


    @EventHandler
    public void Event(PlayerJoinEvent ev) throws IOException {


        String string = "permissions.players." + ev.getPlayer().getUniqueId();

            if (Hardcore24.configuration.get(string).equals("hardcore.commands")) {{
                PermissionAttachment permissionAttachment = ev.getPlayer().addAttachment(Hardcore24.plugin);
                permissionAttachment.setPermission("hardcore.commands", true);
            }

        }










            //Make sure the "banned" players stay "banned"
            FileConfiguration fileConfiguration = Hardcore24.plugin.getConfig();





            World world = Bukkit.getWorld(fileConfiguration.get("respawn-location.world").toString());

            World world_nether = Bukkit.getWorld(fileConfiguration.get("hardcore-world.hardcore-nether").toString());
            World world_hardcore = Bukkit.getWorld(fileConfiguration.get("hardcore-world.hardcore-normal").toString());
            World world_end = Bukkit.getWorld(fileConfiguration.get("hardcore-world.hardcore-end").toString());

            double x = fileConfiguration.getDouble("respawn-location.x");
            double y = fileConfiguration.getDouble("respawn-location.y");
            double z = fileConfiguration.getDouble("respawn-location.z");


          //  if (ev.getPlayer().getWorld().equals(world_hardcore) || ev.getPlayer().getWorld().equals(world_end) || ev.getPlayer().getWorld().equals(world_nether)) {
                Bukkit.getScheduler().runTaskLater(Hardcore24.plugin, () -> {
                    if (ev.getPlayer().getWorld().equals(world_hardcore) || ev.getPlayer().getWorld().equals(world_end) || ev.getPlayer().getWorld().equals(world_nether)) {
                        ev.getPlayer().teleport(new Location(world, x, y, z));
                        ev.getPlayer().sendMessage(ChatColor.RED + "You died in " + world_hardcore.getName() + ", don't worry you can join again tomorrow.");
                    }
                }, 20L);

                Bukkit.getScheduler().runTaskLater(Hardcore24.plugin, () -> {
                    if (ev.getPlayer().getWorld().equals(world_hardcore) || ev.getPlayer().getWorld().equals(world_end) || ev.getPlayer().getWorld().equals(world_nether)) {
                        ev.getPlayer().kickPlayer(ChatColor.BLUE + "Error 403\nForbidden");
                    }
                }, 240L);
            }
        }


