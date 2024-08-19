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
import java.time.LocalDateTime;
import java.util.Scanner;

public class PlayerJoin implements Listener {

    World world_hardcore = Hardcore24.OVERWORLD;
    World world_end = Hardcore24.END_WORLD;

    World world_nether = Hardcore24.NETHER_WORLD;

    double x = Hardcore24.RESPAWN_X;
    double y = Hardcore24.RESPAWN_Y;
    double z = Hardcore24.RESPAWN_Z;
    World world = Hardcore24.RESPAWN_WORLD;

    double d = Hardcore24.DEATH_BAN_TIME;
    boolean deathbanExclude = Hardcore24.DEATHBAN_EXCLUDE_SETTING;


    //public static HashMap<String, UUID> M;
    Scanner s;


    @EventHandler
    public void Event(PlayerJoinEvent ev) throws IOException {


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



        } catch (NullPointerException e){
            try{
                world = Bukkit.getWorld(Hardcore24.plugin.getConfig().getString("respawn-location.world"));
                world_hardcore = Bukkit.getWorld(Hardcore24.plugin.getConfig().getString("respawn-location.world"));
                world_end = Bukkit.getWorld(Hardcore24.plugin.getConfig().getString("hardcore-world.hardcore-nether"));
                world_nether = Bukkit.getWorld(Hardcore24.plugin.getConfig().getString("hardcore-world.hardcore-end"));
            } catch (NullPointerException ee){
                Hardcore24.plugin.getLogger().info(" WORLD NOT FOUND! PLEASE VERIFY THAT THE CONFIG IS CORRECT, MAY BE CASE-SENSITIVE");
            }
        }


        if (Hardcore24.map.containsKey(ev.getPlayer().getUniqueId())) {
            double d = Hardcore24.plugin.getConfig().getDouble("hardcore-config.death-ban-time");
            int i = (int) Math.round(d);
            String iString = i + "";
            ev.getPlayer().setPlayerListName(ChatColor.BLUE + iString + "hrs " + "§9⌚§9" + net.md_5.bungee.api.ChatColor.RESET + ": " + ev.getPlayer().getName());

        } else {
            ev.getPlayer().setPlayerListName(ev.getPlayer().getName());
        }


        //Make sure the "banned" players stay "banned"


       // if (ev.getPlayer().getWorld().equals(world_hardcore) || ev.getPlayer().getWorld().equals(world_end) || ev.getPlayer().getWorld().equals(world_nether)) {
            Bukkit.getScheduler().runTaskLater(Hardcore24.plugin, () -> {
                if (Hardcore24.map.containsKey(ev.getPlayer().getUniqueId()) && ev.getPlayer().getWorld().equals(world_hardcore) || Hardcore24.map.containsKey(ev.getPlayer().getUniqueId()) && ev.getPlayer().getWorld().equals(world_end) || Hardcore24.map.containsKey(ev.getPlayer().getUniqueId()) && ev.getPlayer().getWorld().equals(world_nether)) {
                    ev.getPlayer().teleport(new Location(world, x, y, z));
                    ev.getPlayer().sendMessage(ChatColor.RED + "You died in " + world_hardcore.getName() + ", don't worry you can join again tomorrow.");
                }
            }, 20L);


            Bukkit.getScheduler().runTaskLater(Hardcore24.plugin, () -> {
                if (Hardcore24.map.containsKey(ev.getPlayer().getUniqueId()) && ev.getPlayer().getWorld().equals(world_hardcore) || Hardcore24.map.containsKey(ev.getPlayer().getUniqueId()) && ev.getPlayer().getWorld().equals(world_end) || Hardcore24.map.containsKey(ev.getPlayer().getUniqueId()) && ev.getPlayer().getWorld().equals(world_nether)) {
                    ev.getPlayer().kickPlayer(ChatColor.BLUE + "Error 403\nForbidden");
                }
            }, 240L);
        }
    }


