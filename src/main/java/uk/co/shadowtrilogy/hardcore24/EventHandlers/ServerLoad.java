package uk.co.shadowtrilogy.hardcore24.EventHandlers;

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



    @EventHandler
    public void Event(PlayerChangedWorldEvent ev) throws FileNotFoundException, InterruptedException {




        if(Hardcore24.map.containsKey(ev.getPlayer().getUniqueId())) {
            //Make sure the "banned" players stay "banned"
            FileConfiguration fileConfiguration = Hardcore24.plugin.getConfig();

            //

            //corrector variables
            World world = Bukkit.getWorld(fileConfiguration.get("respawn-location.world").toString());

            double x = fileConfiguration.getDouble("respawn-location.x");
            double y = fileConfiguration.getDouble("respawn-location.y");
            double z = fileConfiguration.getDouble("respawn-location.z");

            double d = Hardcore24.plugin.getConfig().getDouble("hardcore-config.death-ban-time");
            LocalDateTime t = (LocalDateTime) Hardcore24.map.get(ev.getPlayer().getUniqueId());
            double dhrs = t.getHour() + d;

            double days = t.getDayOfMonth() + dhrs / 24;


            //if variables
            World world_nether = Bukkit.getWorld(fileConfiguration.get("hardcore-world.hardcore-nether") + "");
            World world_hardcore = Bukkit.getWorld(fileConfiguration.get("hardcore-world.hardcore-normal") + "");
            World world_end = Bukkit.getWorld(fileConfiguration.get("hardcore-world.hardcore-end").toString());


            if (ev.getPlayer().getWorld().equals(world_hardcore) || ev.getPlayer().getWorld().equals(world_end) || ev.getPlayer().getWorld().equals(world_nether)) {
                ev.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 80, 255));
            }


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

                    ev.getPlayer().sendMessage(ChatColor.GOLD + "You died in " + world_hardcore.getName() + "! Don't worry you can join again on the " + ((int) days) + "th of " + t.getMonth().name().toLowerCase() + " at " + hrs);
             }
            }, 40L);


            //Backup Check
            Bukkit.getScheduler().runTaskLater(Hardcore24.plugin, () -> {
                if(ev.getPlayer().getWorld().equals(world_hardcore) || ev.getPlayer().getWorld().equals(world_end) || ev.getPlayer().getWorld().equals(world_nether)){
                ev.getPlayer().teleport(new Location(world, x, y, z));

                    double time_hrs = t.getHour() + d;
                    while(time_hrs >= 24){
                        if(time_hrs >= 48){
                            time_hrs -= 48;
                        } else {
                            time_hrs -= 24;
                        }
                    }

                    if(days > days + .5){
                        System.out.println(days + .5);
                    }

                    double hrs = time_hrs;
                    ev.getPlayer().sendMessage(ChatColor.BLUE + "You died in " + world_hardcore.getName() + "! Don't worry you can join again on the " + ((int) days) + "th of " + t.getMonth().name().toLowerCase() + " at " + hrs);



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
