package uk.co.shadowtrilogy.hardcore24.EventHandlers;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;
import uk.co.shadowtrilogy.hardcore24.Hardcore24;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;


public class MoreMobs implements Listener {




    public static String title = Hardcore24.plugin.getConfig().getString("hardcore-message-config.blood-moon-title-message");
    public static String subtitle = Hardcore24.plugin.getConfig().getString("hardcore-message-config.blood-moon-subtitle-message");
    World world_nether = Bukkit.getWorld(Hardcore24.plugin.getConfig().getString("hardcore-world.hardcore-nether"));
    World world_hardcore = Bukkit.getWorld(Hardcore24.plugin.getConfig().getString("hardcore-world.hardcore-normal"));
    World world_end = Bukkit.getWorld(Hardcore24.plugin.getConfig().getString("hardcore-world.hardcore-end"));



    @EventHandler
    public void events(EntitySpawnEvent event) {
        Boolean bool = Hardcore24.plugin.getConfig().getBoolean("hardcore-config.harder-mobs");
        Boolean Moon = Hardcore24.plugin.getConfig().getBoolean("hardcore-config.do-blood-moon");
        //World world_hardcore = Bukkit.getWorld(Hardcore24.plugin.getConfig().getString("hardcore-world.hardcore-normal").toString());


        if (world_hardcore.getFullTime() < 23000 && world_hardcore.getFullTime() > 13000) {

            if(Hardcore24.phaseCounter == 3){
                Hardcore24.removeArmour = true;
                Hardcore24.phaseCounter = 0;
            }

            long phase = world_hardcore.getFullTime() / 24000;
            if (phase == 0) {



                if (Hardcore24.playOnce == true) {
                    int i = ThreadLocalRandom.current().nextInt(0, 4);

                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (Moon == true) {
                            if (i == 2) {

                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

                                int hour = LocalDateTime.now().getHour() + 2;

                                    String string = ChatColor.RED + "#####################################\n#Blood Moon Start Date & Time: " + ChatColor.GREEN + LocalDateTime.now().format(formatter) + ChatColor.RED + "\n#Blood Moon Predicted End: " + ChatColor.GREEN + hour + ":" + LocalDateTime.now().getMinute() + ChatColor.RED + "\n#####################################";
                                    Hardcore24.LastBloodMoon = string;



                                    if(player.getWorld().equals(world_hardcore) || player.getWorld().equals(world_end) || player.getWorld().equals(world_nether)) {
                                        player.sendMessage(Hardcore24.Blood_Moon_Message);
                                        player.sendTitle(title, subtitle);
                                        world_hardcore.playSound(player.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1.2f, 1.0f);
                                        Hardcore24.bloodmoon = true;
                                    }


                                Bukkit.getScheduler().runTaskLater(Hardcore24.plugin, () -> {

                                    for (LivingEntity entity : world_hardcore.getLivingEntities()) {
                                        try {
                                            if (entity.getEquipment().getHelmet().isSimilar(ArmourInit.bloodMoon1)) {
                                                entity.getEquipment().setHelmet(ArmourInit.none);
                                            }
                                        } catch (NullPointerException e) {

                                        }
                                        try {
                                            if (entity.getEquipment().getChestplate().isSimilar(ArmourInit.bloodMoon2)) {
                                                entity.getEquipment().setChestplate(ArmourInit.none);
                                            }
                                        } catch (NullPointerException e) {

                                        }
                                        try {
                                            if (entity.getEquipment().getLeggings().isSimilar(ArmourInit.bloodMoon3)) {
                                                entity.getEquipment().setLeggings(ArmourInit.none);
                                            }
                                        } catch (NullPointerException e) {

                                        }
                                        try {
                                            if (entity.getEquipment().getBoots().isSimilar(ArmourInit.bloodMoon4)) {
                                                entity.getEquipment().setBoots(ArmourInit.none);
                                            }
                                        } catch (NullPointerException e) {

                                        }

                                    }
                                    Hardcore24.bloodmoon = false;
                                    Hardcore24.day = true;


                            }, Hardcore24.d);
                            }
                        }
                        if (bool) {
                            if(Hardcore24.bloodmoon == false) {
                                if(player.getWorld().equals(world_hardcore) || player.getWorld().equals(world_end) || player.getWorld().equals(world_nether)) {
                                    player.sendMessage(ChatColor.DARK_RED + "As the full moon rises the countless monsters band\ntogether, you encounter mobs with stronger gear...");
                                    world_hardcore.playSound(player.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1.2f, 1.0f);
                                    Hardcore24.day = false;
                                }
                            }
                        }
                    }



                    Hardcore24.playOnce = false;
                }


                for (LivingEntity entity : world_hardcore.getLivingEntities()) {
                    if (entity.getType().equals(EntityType.SKELETON) && !Bukkit.getOnlinePlayers().contains(entity) || entity.getType().equals(EntityType.ZOMBIE) && !Bukkit.getOnlinePlayers().contains(entity)) {

                        if (Hardcore24.bloodmoon == true) {


                            EquipHelmet(entity);
                            EquipChestplate(entity);

                            //Checks if entity already has leggings and if not sets the leggings to Netherite
                            if (entity.getEquipment().getLeggings().getType().equals(Material.AIR) || entity.getEquipment().getLeggings().isSimilar(ArmourInit.iron2)) {
                                entity.getEquipment().setLeggings(ArmourInit.bloodMoon3);
                                entity.getEquipment().getLeggings().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                                entity.getEquipment().setLeggingsDropChance(0.0f);
                            }
                            //Checks if entity already has boots and if not sets the boots to Netherite
                            if (entity.getEquipment().getBoots().getType().equals(Material.AIR)) {
                                entity.getEquipment().setBoots(ArmourInit.bloodMoon4);
                                entity.getEquipment().getBoots().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                                entity.getEquipment().setBootsDropChance(0.0f);
                            }
                        } else if (bool == true && Hardcore24.day == false) {
                            //Checks if entity already has a helmet and if not sets the helmet to diamond
                            if (entity.getEquipment().getHelmet().getType().equals(Material.AIR)) {
                                entity.getEquipment().setHelmet(ArmourInit.diamond);
                                entity.getEquipment().setHelmetDropChance(0.0f);
                            }
                            //Checks if entity already has a chestplate and if not sets the chestplate to iron
                            if (entity.getEquipment().getChestplate().getType().equals(Material.AIR)) {
                                entity.getEquipment().setChestplate(ArmourInit.iron1);
                                entity.getEquipment().setChestplateDropChance(0.0f);
                            }
                            //Checks if entity already has leggings and if not sets the leggings to iron
                            if (entity.getEquipment().getLeggings().getType().equals(Material.AIR)) {
                                entity.getEquipment().setLeggings(ArmourInit.iron2);
                                entity.getEquipment().setLeggingsDropChance(0.0f);
                            }
                            //Checks if entity already has boots and if not sets the boots to iron
                            if (entity.getEquipment().getBoots().getType().equals(Material.AIR)) {
                                entity.getEquipment().setBoots(ArmourInit.iron3);
                                entity.getEquipment().setBootsDropChance(0.0f);
                            }
                        }


                    }
                    Hardcore24.removeArmour = false;
                }
            }


        } else if (world_hardcore.getFullTime() < 12500) {

            Hardcore24.playOnce = true;
            Hardcore24.bloodmoon = false;

            //Remove HarderMobs armour
            for (LivingEntity entity : world_hardcore.getLivingEntities()) {
                //Remove helmet if helmet exists & is the correct helmet
                if (entity.getEquipment()!=null) {
                    try {
                        if (entity.getEquipment().getHelmet().isSimilar(ArmourInit.diamond)) {
                            entity.getEquipment().setHelmet(ArmourInit.none);
                        }
                    } catch (NullPointerException e){

                    }
                }
                //Remove Chestplate
                if (entity.getEquipment()!=null) {
                    try {
                        if (entity.getEquipment().getChestplate().isSimilar(ArmourInit.iron1)) {
                            entity.getEquipment().setChestplate(ArmourInit.none);
                        }
                    } catch (NullPointerException e){

                    }
                }

                //Remove Leggings
                if (entity.getEquipment()!=null) {
                    try {
                        if (entity.getEquipment().getLeggings().isSimilar(ArmourInit.iron2)) {
                            entity.getEquipment().setLeggings(ArmourInit.none);
                        }
                    } catch (NullPointerException e){

                    }
                }

                    //Remove Boots
                    if (entity.getEquipment()!=null) {
                        try {
                            if (entity.getEquipment().getBoots().isSimilar(ArmourInit.iron3)) {
                                entity.getEquipment().setBoots(ArmourInit.none);
                            }
                        } catch (NullPointerException e){

                        }
                    }
            }
        }


    }

    public static void EquipHelmet(LivingEntity entity){
        if (entity.getEquipment().getHelmet().getType().equals(Material.AIR) || entity.getEquipment().getLeggings().isSimilar(ArmourInit.diamond)) {
            entity.getEquipment().setHelmet(ArmourInit.bloodMoon1);
            entity.getEquipment().getHelmet().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            entity.getEquipment().setHelmetDropChance(0.0f);
        }

    }

    public static void EquipChestplate(LivingEntity entity){
        if (entity.getEquipment().getChestplate().getType().equals(Material.AIR) || entity.getEquipment().getLeggings().isSimilar(ArmourInit.iron1)) {
            entity.getEquipment().setChestplate(ArmourInit.bloodMoon2);
            entity.getEquipment().getChestplate().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            entity.getEquipment().setChestplateDropChance(0.0f);
        }

    }

}
