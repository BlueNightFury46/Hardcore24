package uk.co.shadowtrilogy.hardcore24.EventHandlers;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
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
    Boolean deathbanExclude = Hardcore24.DEATHBAN_EXCLUDE_SETTING;

    World world = Hardcore24.RESPAWN_WORLD;

    double KILL_PLAYER_AT = Hardcore24.KILL_PLAYER_AT;

    World world_nether = Hardcore24.NETHER_WORLD;
    World world_end = Hardcore24.END_WORLD;
    World world_hardcore = Hardcore24.OVERWORLD;

    double x = Hardcore24.RESPAWN_X;
    double y = Hardcore24.RESPAWN_Y;
    double z = Hardcore24.RESPAWN_Z;

    double d = Hardcore24.DEATH_BAN_TIME;

    String string;



    @EventHandler
    public void Event(PlayerDeathEvent e) throws IOException, InvalidConfigurationException {
        long time = (long) d * 60 * 60 * 20;
        if (deathbanExclude == true) {



            Player player = e.getEntity().getPlayer();




            if(player.isOp() == false) {
                if (player.getWorld().equals(world_nether) || player.getWorld().equals(world_hardcore) || player.getWorld().equals(world_end)) {

                    if (Hardcore24.map.containsKey(player.getUniqueId())) {
                        player.sendMessage(ChatColor.BLUE + "409 Conflict");
                        return;

                    } else if (!Hardcore24.map.containsKey(player.getUniqueId())) {
                        LocalDateTime DateTime = LocalDateTime.now();
                        Hardcore24.map.put(player.getUniqueId(), DateTime);

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
                            string = ChatColor.BLUE + "#####################################\n#Player Death Date & Time: " + ChatColor.GREEN + LocalDateTime.now().format(formatter) + ChatColor.BLUE + "\n#Player Death Message: " + ChatColor.LIGHT_PURPLE + e.getDeathMessage() + ChatColor.BLUE + "\n#Player 'Killer': " + ChatColor.GREEN + player.getKiller() + ChatColor.BLUE + "\n#X: " + ChatColor.LIGHT_PURPLE + player.getLocation().getX() + ChatColor.BLUE + " Y: " + ChatColor.GREEN  + player.getLocation().getY()  + ChatColor.BLUE + " Z: " + ChatColor.LIGHT_PURPLE + player.getLocation().getZ() + ChatColor.BLUE + "\n#####################################";
                            PlayerLog.put(player.getUniqueId(), string);
                        } else {
                            string = ChatColor.BLUE + "#####################################\n#Player Death Date & Time: " + ChatColor.GREEN + LocalDateTime.now().format(formatter) + ChatColor.BLUE + "\n#Player Death Message: " + ChatColor.LIGHT_PURPLE + e.getDeathMessage() + ChatColor.BLUE + ChatColor.BLUE + "\n#X: " + ChatColor.LIGHT_PURPLE + player.getLocation().getX() + ChatColor.BLUE + " Y: " + ChatColor.GREEN  + player.getLocation().getY() + ChatColor.BLUE + " Z: " + ChatColor.LIGHT_PURPLE + player.getLocation().getZ() + ChatColor.BLUE + "\n#####################################";
                            PlayerLog.put(player.getUniqueId(), string);
                        }




                        Bukkit.getScheduler().runTaskLater(Hardcore24.plugin, () -> {
                            if (Hardcore24.map.containsKey(player.getUniqueId())) {
                                Hardcore24.map.remove(player.getUniqueId(), DateTime);
                                player.setPlayerListName(player.getName());
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



            Player player = e.getEntity().getPlayer();
            if (player.getWorld().equals(world_nether) || player.getWorld().equals(world_hardcore) || player.getWorld().equals(world_end)) {

                if (Hardcore24.map.containsKey(player.getUniqueId())) {
                    player.sendMessage(ChatColor.BLUE + "409 Conflict");
                    return;

                } else if (!Hardcore24.map.containsKey(player.getUniqueId())) {
                    LocalDateTime DateTime = LocalDateTime.now();
                    Hardcore24.map.put(player.getUniqueId(), DateTime);

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
                        string = ChatColor.BLUE + "#####################################\n#Player Death Date & Time: " + ChatColor.GREEN + LocalDateTime.now().format(formatter) + ChatColor.BLUE + "\n#Player Death Message: " + ChatColor.LIGHT_PURPLE + e.getDeathMessage() + ChatColor.BLUE + "\n#Player 'Killer': " + ChatColor.GREEN + player.getKiller() + ChatColor.BLUE + "\n#X:" + ChatColor.LIGHT_PURPLE + player.getLocation().getX() + ChatColor.BLUE + " Y: " + ChatColor.GREEN  + player.getLocation().getY()  + ChatColor.BLUE + " Z: " + ChatColor.LIGHT_PURPLE + player.getLocation().getZ() + ChatColor.BLUE + "\n#####################################";
                        PlayerLog.put(player.getUniqueId(), string);
                    } else {
                        string = ChatColor.BLUE + "#####################################\n#Player Death Date & Time: " + ChatColor.GREEN + LocalDateTime.now().format(formatter) + ChatColor.BLUE + "\n#Player Death Message: " + ChatColor.LIGHT_PURPLE + e.getDeathMessage() + ChatColor.BLUE + "\n#X: " + ChatColor.LIGHT_PURPLE + player.getLocation().getX() + ChatColor.BLUE + " Y: " + ChatColor.GREEN  + player.getLocation().getY()  + ChatColor.BLUE + " Z: " + ChatColor.LIGHT_PURPLE + player.getLocation().getZ() + ChatColor.BLUE + "\n#####################################";
                        PlayerLog.put(player.getUniqueId(), string);
                    }




                    Bukkit.getScheduler().runTaskLater(Hardcore24.plugin, () -> {
                        if (Hardcore24.map.containsKey(player.getUniqueId())) {
                            Hardcore24.map.remove(player.getUniqueId(), DateTime);
                            player.setPlayerListName(player.getName());
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



     }




    }
