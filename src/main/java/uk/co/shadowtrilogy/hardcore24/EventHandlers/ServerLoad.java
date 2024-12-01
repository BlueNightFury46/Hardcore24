package uk.co.shadowtrilogy.hardcore24.EventHandlers;

import org.apache.commons.lang3.ObjectUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import uk.co.shadowtrilogy.hardcore24.Hardcore24;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;

public class ServerLoad implements Listener {

        World world_hardcore = Hardcore24.OVERWORLD;
        World world_end = Hardcore24.END_WORLD;

        World world_nether = Hardcore24.NETHER_WORLD;


    double x = Hardcore24.RESPAWN_X;
    double y = Hardcore24.RESPAWN_Y;
    double z = Hardcore24.RESPAWN_Z;
    World world = Hardcore24.RESPAWN_WORLD;

    double d = Hardcore24.DEATH_BAN_TIME;
    boolean deathbanExclude = Hardcore24.DEATHBAN_EXCLUDE_SETTING;

    @EventHandler
    public void Event(PlayerChangedWorldEvent ev) throws FileNotFoundException, InterruptedException {


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



        if(Hardcore24.map.containsKey(ev.getPlayer().getUniqueId())) {
            //Make sure the "banned" players stay "banned"
          //  FileConfiguration fileConfiguration = Hardcore24.plugin.getConfig();
         //The above line of code was still in this plugin until version 1.11c???? How and why?
            //

            //corrector variables

            LocalDateTime t = (LocalDateTime) Hardcore24.map.get(ev.getPlayer().getUniqueId());
            double dhrs = t.getHour() + d;

            double days = t.getDayOfMonth() + dhrs / 24;

            //Well this isn't optimized at all, but I'm not changing it
            String timezone = Hardcore24.plugin.getConfig().getString("hardcore-config.server-timezone");

            if (ev.getPlayer().getWorld().equals(world_hardcore) || ev.getPlayer().getWorld().equals(world_end) || ev.getPlayer().getWorld().equals(world_nether)) {
                ev.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 80, 255));
            }


            //Gosh the below code is messy... The only thing worse than this is IPV4

            //First Check
            if (ev.getPlayer().getWorld().equals(world_hardcore) || ev.getPlayer().getWorld().equals(world_end) || ev.getPlayer().getWorld().equals(world_nether)) {
            Bukkit.getScheduler().runTaskLater(Hardcore24.plugin, () -> {
                if(ev.getPlayer().getWorld().equals(world_hardcore) || ev.getPlayer().getWorld().equals(world_end) || ev.getPlayer().getWorld().equals(world_nether))
                { ev.getPlayer().teleport(new Location(world, x, y, z));
                    double time_hrs = t.getHour() + d;
                    while(time_hrs >= 24){
                        if(time_hrs >= 48){
                            time_hrs -= 48;
                        } else {
                            time_hrs -= 24;
                        }
                    }
                    double hrs = time_hrs;

                    if(t.getMinute() < 10){
                        ev.getPlayer().sendMessage(ChatColor.GOLD + "You died in " + world_hardcore.getName() + "! Don't worry you can join again on the " + ((int) days) + " of " + t.getMonth().name().toLowerCase() + " at " + ((int) hrs) + ":0" + t.getMinute() + " (" + timezone + ")");

                    } else {
                        ev.getPlayer().sendMessage(ChatColor.GOLD + "You died in " + world_hardcore.getName() + "! Don't worry you can join again on the " + ((int) days) + " of " + t.getMonth().name().toLowerCase() + " at " + ((int) hrs) + ":" + t.getMinute() + " (" + timezone + ")");
                    }
             }
            }, 40L);


            //Backup Check
            Bukkit.getScheduler().runTaskLater(Hardcore24.plugin, () -> {
                if(ev.getPlayer().getWorld().equals(world_hardcore) || ev.getPlayer().getWorld().equals(world_end) || ev.getPlayer().getWorld().equals(world_nether)){
                ev.getPlayer().teleport(new Location(world, x, y, z));

                    double time_hrs = t.getHour() + d;
                    //Why isn't this pre-calculated? You know what, I don't want to know...
                    while(time_hrs >= 24){
                        if(time_hrs >= 48){
                            time_hrs -= 48;
                        } else {
                            time_hrs -= 24;
                        }
                    }



                    double hrs = time_hrs;
                    if(t.getMinute() < 10){
                        ev.getPlayer().sendMessage(ChatColor.BLUE + "You died in " + world_hardcore.getName() + "! Don't worry you can join again on the " + ((int) days) + " of " + t.getMonth().name().toLowerCase() + " at " + ((int) hrs) + ":0" + t.getMinute() + " (" + timezone + ")");

                    } else {
                        ev.getPlayer().sendMessage(ChatColor.BLUE + "You died in " + world_hardcore.getName() + "! Don't worry you can join again on the " + ((int) days) + "" +
                                " of " + t.getMonth().name().toLowerCase() + " at " + ((int) hrs) + ":" + t.getMinute() + " (" + timezone + ")");
                    }



                }
            }, 60L);

                Bukkit.getScheduler().runTaskLater(Hardcore24.plugin, () -> {
                    if(ev.getPlayer().getWorld().equals(world_hardcore) || ev.getPlayer().getWorld().equals(world_end) || ev.getPlayer().getWorld().equals(world_nether)){
                        ev.getPlayer().teleport(new Location(world, x, y, z));
                        ev.getPlayer().sendMessage(ChatColor.DARK_RED + "You died in " + world_hardcore.getName() + "! Don't worry, you can join back soon...");
                    }
                }, 90L);



            //Something is going seriously wrong if we reach this
            Bukkit.getScheduler().runTaskLater(Hardcore24.plugin, () -> {
                if(ev.getPlayer().getWorld().equals(world_hardcore) || ev.getPlayer().getWorld().equals(world_end) || ev.getPlayer().getWorld().equals(world_nether))
                { ev.getPlayer().teleport(new Location(world, x, y, z));
                     ev.getPlayer().kickPlayer(ChatColor.RED + "Error 420\nEnhance your cool" );
                }
            }, 120L);

        }}
    }
}
