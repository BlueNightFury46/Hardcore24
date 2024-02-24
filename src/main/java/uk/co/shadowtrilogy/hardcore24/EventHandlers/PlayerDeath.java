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
import org.bukkit.util.ChatPaginator;
import uk.co.shadowtrilogy.hardcore24.Hardcore24;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class PlayerDeath implements Listener {

    public static HashMap<UUID, String> PlayerLogg = new HashMap<>();
    public static Map PlayerLog = PlayerLogg;
    String string;
    public static final long time = Hardcore24.plugin.getConfig().getLong("hardcore-config.death-ban-time") * 60 * 60 * 20;



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

                //TODO Set 409 conflict in logs
            }
            else if(!Hardcore24.map.containsKey(player.getUniqueId())){

                Hardcore24.map.put(player.getUniqueId(), true);

                for(ItemStack itemStack : player.getEnderChest().getContents()){
                    e.getDrops().add(itemStack);

                }
                player.getEnderChest().clear();

                player.setPlayerListName(ChatColor.BLUE + "24hrs " + "§9⌚§9" + net.md_5.bungee.api.ChatColor.RESET + ": " + player.getName());

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                if(player.getKiller() != null) {
                    string = ChatColor.BLUE + "#####################################\n#Player Death Date & Time: " + ChatColor.GREEN + LocalDateTime.now().format(formatter) + ChatColor.BLUE + "\n#Player Death Message: " + ChatColor.LIGHT_PURPLE + e.getDeathMessage() + ChatColor.BLUE + "\n#Player 'Killer': " + ChatColor.GREEN + player.getKiller() + ChatColor.BLUE + "\n#####################################";
                    PlayerLog.put(player.getUniqueId(), string);
                }
                else {
                    string = ChatColor.BLUE + "#####################################\n#Player Death Date & Time: " + ChatColor.GREEN + LocalDateTime.now().format(formatter) + ChatColor.BLUE + "\n#Player Death Message: " + ChatColor.LIGHT_PURPLE + e.getDeathMessage() + ChatColor.BLUE + "\n#####################################";
                    PlayerLog.put(player.getUniqueId(), string);
                }


            Bukkit.getScheduler().runTaskLater(Hardcore24.plugin, () -> {
                if(Hardcore24.map.containsKey(player.getUniqueId())) {
                    Hardcore24.map.remove(player.getUniqueId(), true);
                }

            }, time);


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
