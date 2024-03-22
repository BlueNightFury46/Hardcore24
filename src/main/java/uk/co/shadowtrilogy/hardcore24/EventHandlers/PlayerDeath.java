package uk.co.shadowtrilogy.hardcore24.EventHandlers;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import uk.co.shadowtrilogy.hardcore24.Hardcore24;

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



    @EventHandler
    public void Event(PlayerDeathEvent e) throws IOException, InvalidConfigurationException {

        //Loads the death-ban-time from the config
        boolean deathbanExclude = Hardcore24.plugin.getConfig().getBoolean("hardcore-config.death-ban-exclude-ops");
        double d = Hardcore24.plugin.getConfig().getDouble("hardcore-config.death-ban-time");

        long time = (long) d * 60 * 60 * 20;


        //Loads the respawn worlds from the config
        FileConfiguration fileConfiguration = Hardcore24.plugin.getConfig();
        World world = Bukkit.getWorld(fileConfiguration.get("respawn-location.world").toString());
        double x = fileConfiguration.getDouble("respawn-location.x");
        double y = fileConfiguration.getDouble("respawn-location.y");
        double z = fileConfiguration.getDouble("respawn-location.z");

        World world_nether = Bukkit.getWorld(fileConfiguration.getString("hardcore-world.hardcore-nether"));
        World world_hardcore = Bukkit.getWorld(fileConfiguration.getString("hardcore-world.hardcore-normal"));
        World world_end = Bukkit.getWorld(fileConfiguration.getString("hardcore-world.hardcore-end"));


        //corrector variables


        if (deathbanExclude == true) {



            Player player = e.getPlayer();


            if(player.isOp() == false) {
                if (player.getWorld().equals(world_nether) || player.getWorld().equals(world_hardcore) || player.getWorld().equals(world_end)) {

                    if (Hardcore24.map.containsKey(player.getUniqueId())) {
                        e.setCancelled(true);
                        player.sendMessage(ChatColor.BLUE + "409 Conflict");

                    } else if (!Hardcore24.map.containsKey(player.getUniqueId())) {

                        Hardcore24.map.put(player.getUniqueId(), true);

                        for (ItemStack itemStack : player.getEnderChest().getContents()) {
                            e.getDrops().add(itemStack);

                        }
                        player.getEnderChest().clear();
                        double d2 = Hardcore24.plugin.getConfig().getDouble("hardcore-config.death-ban-time");
                        int i = (int) Math.round(d2);
                        String iString = i + "";
                        player.setPlayerListName(ChatColor.BLUE + iString + "hrs " + "§9⌚§9" + net.md_5.bungee.api.ChatColor.RESET + ": " + player.getName());

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                        if (player.getKiller() != null) {
                            string = ChatColor.BLUE + "#####################################\n#Player Death Date & Time: " + ChatColor.GREEN + LocalDateTime.now().format(formatter) + ChatColor.BLUE + "\n#Player Death Message: " + ChatColor.LIGHT_PURPLE + e.getDeathMessage() + ChatColor.BLUE + "\n#Player 'Killer': " + ChatColor.GREEN + player.getKiller() + ChatColor.BLUE + "\n#X:" + ChatColor.LIGHT_PURPLE + player.getX() + ChatColor.BLUE + " Y: " + ChatColor.GREEN  + player.getY()  + ChatColor.BLUE + " Z: " + ChatColor.LIGHT_PURPLE + player.getZ() + ChatColor.BLUE + "\n#####################################";
                            PlayerLog.put(player.getUniqueId(), string);
                        } else {
                            string = ChatColor.BLUE + "#####################################\n#Player Death Date & Time: " + ChatColor.GREEN + LocalDateTime.now().format(formatter) + ChatColor.BLUE + "\n#Player Death Message: " + ChatColor.LIGHT_PURPLE + e.getDeathMessage() + ChatColor.BLUE + ChatColor.BLUE + "\n#X:" + ChatColor.LIGHT_PURPLE + player.getX() + ChatColor.BLUE + " Y: " + ChatColor.GREEN  + player.getY() + ChatColor.BLUE + " Z: " + ChatColor.LIGHT_PURPLE + player.getZ() + ChatColor.BLUE + "\n#####################################";
                            PlayerLog.put(player.getUniqueId(), string);
                        }




                        Bukkit.getScheduler().runTaskLater(Hardcore24.plugin, () -> {
                            if (Hardcore24.map.containsKey(player.getUniqueId())) {
                                Hardcore24.map.remove(player.getUniqueId(), true);
                            }

                        }, time);


                        try {
                            if (!player.getBedSpawnLocation().getWorld().equals(world)) {
                                player.setBedSpawnLocation(new Location(world, x, y, z));
                            }
                        } catch (NullPointerException exception) {
                            Hardcore24.plugin.getLogger().info(player.getName() + "'s bed spawn location is set to null");
                        }

                    }
                }

            }
        } else if (deathbanExclude == false){



            Player player = e.getPlayer();
            if (player.getWorld().equals(world_nether) || player.getWorld().equals(world_hardcore) || player.getWorld().equals(world_end)) {

                if (Hardcore24.map.containsKey(player.getUniqueId())) {
                    e.setCancelled(true);
                    player.sendMessage(ChatColor.BLUE + "409 Conflict");

                } else if (!Hardcore24.map.containsKey(player.getUniqueId())) {

                    Hardcore24.map.put(player.getUniqueId(), true);

                    for (ItemStack itemStack : player.getEnderChest().getContents()) {
                        e.getDrops().add(itemStack);

                    }
                    player.getEnderChest().clear();
                    double d2 = Hardcore24.plugin.getConfig().getDouble("hardcore-config.death-ban-time");
                    int i = (int) Math.round(d2);
                    String iString = i + "";

                    player.setPlayerListName(ChatColor.BLUE + iString + "hrs " + "§9⌚§9" + net.md_5.bungee.api.ChatColor.RESET + ": " + player.getName());

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                    if (player.getKiller() != null) {
                        string = ChatColor.BLUE + "#####################################\n#Player Death Date & Time: " + ChatColor.GREEN + LocalDateTime.now().format(formatter) + ChatColor.BLUE + "\n#Player Death Message: " + ChatColor.LIGHT_PURPLE + e.getDeathMessage() + ChatColor.BLUE + "\n#Player 'Killer': " + ChatColor.GREEN + player.getKiller() + ChatColor.BLUE + "\n#X:" + ChatColor.LIGHT_PURPLE + player.getX() + ChatColor.BLUE + " Y: " + ChatColor.GREEN  + player.getY()  + ChatColor.BLUE + " Z: " + ChatColor.LIGHT_PURPLE + player.getZ() + ChatColor.BLUE + "\n#####################################";
                        PlayerLog.put(player.getUniqueId(), string);
                    } else {
                        string = ChatColor.BLUE + "#####################################\n#Player Death Date & Time: " + ChatColor.GREEN + LocalDateTime.now().format(formatter) + ChatColor.BLUE + "\n#Player Death Message: " + ChatColor.LIGHT_PURPLE + e.getDeathMessage() + ChatColor.BLUE + "\n#X:" + ChatColor.LIGHT_PURPLE + player.getX() + ChatColor.BLUE + " Y: " + ChatColor.GREEN  + player.getY()  + ChatColor.BLUE + " Z: " + ChatColor.LIGHT_PURPLE + player.getZ() + ChatColor.BLUE + "\n#####################################";
                        PlayerLog.put(player.getUniqueId(), string);
                    }




                    Bukkit.getScheduler().runTaskLater(Hardcore24.plugin, () -> {
                        if (Hardcore24.map.containsKey(player.getUniqueId())) {
                            Hardcore24.map.remove(player.getUniqueId(), true);
                        }

                    }, time);


                    try {
                        if (!player.getBedSpawnLocation().getWorld().equals(world)) {
                            player.setBedSpawnLocation(new Location(world, x, y, z));
                        }
                    } catch (NullPointerException exception) {
                        Hardcore24.plugin.getLogger().info(player.getName() + "'s bed spawn location is set to null");
                    }

                }
            }



        }
    } }





