package uk.co.shadowtrilogy.hardcore24.EventHandlers;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionAttachment;
import uk.co.shadowtrilogy.hardcore24.Hardcore24;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class PlayerDeath implements Listener {





    @EventHandler
    public void Event(PlayerDeathEvent e){


        FileConfiguration fileConfiguration = Hardcore24.plugin.getConfig();

        //corrector variables
        World world = Bukkit.getWorld(fileConfiguration.get("respawn-location.world").toString());
        double x = fileConfiguration.getDouble("respawn-location.x");
        double y = fileConfiguration.getDouble("respawn-location.y");
        double z = fileConfiguration.getDouble("respawn-location.z");

        //if variables
        World world_nether = Bukkit.getWorld(fileConfiguration.get("hardcore-world.hardcore-nether").toString());
        World world_hardcore = Bukkit.getWorld(fileConfiguration.get("hardcore-world.hardcore-normal").toString());
        World world_end = Bukkit.getWorld(fileConfiguration.get("hardcore-world.hardcore-end").toString());


        Player player = e.getPlayer();
        World PlayerWorld = player.getWorld();
        if(player.getWorld().equals(world_nether) || player.getWorld().equals(world_hardcore) || player.getWorld().equals(world_end)){
        if(!player.isOp()){
            if(Hardcore24.map.containsKey(player.getUniqueId())){
                e.setCancelled(true);
                player.sendMessage(ChatColor.BLUE + "409 Conflict");
            }
            else if(!Hardcore24.map.containsKey(player.getUniqueId())){

                Hardcore24.map.put(player.getUniqueId(), true);

                for(ItemStack itemStack : player.getEnderChest().getContents()){
                    e.getDrops().add(itemStack);

                }
                player.getEnderChest().clear();


            Bukkit.getScheduler().runTaskLater(Hardcore24.plugin, () -> {
                Hardcore24.map.remove(player.getUniqueId(), true);

            }, 1728000L);


            try{
                if(!player.getBedSpawnLocation().getWorld().equals(world)){
                    player.setBedSpawnLocation(new Location(world, x, y, z));
                }
            }
            catch (NullPointerException exception){
                Hardcore24.plugin.getLogger().info(player.getName() + "'s bed spawn location is set to null");
            }

            }}}








        //HardcoreDeathBan.plugin.getConfig().set("UUID." + player.getUniqueId().toString(), player.getUniqueId().toString());
        //HardcoreDeathBan.plugin.saveConfig();

        //Logs the banned players uuids, and double-checks that the file exists



    }
}
