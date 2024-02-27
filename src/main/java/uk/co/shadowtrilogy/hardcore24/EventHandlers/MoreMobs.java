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

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;


public class MoreMobs implements Listener {


    public boolean bloodmoon = false;

    @EventHandler
    public void events(EntitySpawnEvent event){
       Boolean bool = Hardcore24.plugin.getConfig().getBoolean("hardcore-config.harder-mobs");
        Boolean Moon = Hardcore24.plugin.getConfig().getBoolean("hardcore-config.do-blood-moon");
        World world_hardcore = Bukkit.getWorld(Hardcore24.plugin.getConfig().getString("hardcore-world.hardcore-normal").toString());


           if (world_hardcore.getFullTime() < 23000 && world_hardcore.getFullTime() > 13000 && bool == true) {
               long phase = world_hardcore.getFullTime() / 24000;
               if (phase == 0) {


                   if(Hardcore24.playOnce == true) {

                       for(Player player : Bukkit.getOnlinePlayers()){
                           player.sendMessage(ChatColor.DARK_RED + "As the full moon rises the countless monsters band together,\nyou encounter mobs with stronger gear...");
                           world_hardcore.playSound(player.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1.2f, 1.0f);

                           if(Moon == true) {
                               int i = ThreadLocalRandom.current().nextInt(0, 4);
                               if (i == 3) {
                                   bloodmoon = true;
                               }
                           }
                       }
                       Hardcore24.playOnce = false;
                   }

                   for (LivingEntity entity : world_hardcore.getLivingEntities()) {
                       if (entity.getType().equals(EntityType.SKELETON) || entity.getType().equals(EntityType.ZOMBIE))

                           if(bloodmoon == true){
                               //Checks if entity already has a helmet and if not sets the helmet to diamond
                               if(entity.getEquipment().getHelmet()==null) {
                                   entity.getEquipment().setHelmet(ArmourInit.diamond);
                               }
                               //Checks if entity already has a chestplate and if not sets the chestplate to iron
                               if(entity.getEquipment().getChestplate()==null) {
                                   entity.getEquipment().setChestplate(ArmourInit.iron1);
                               }
                               //Checks if entity already has leggings and if not sets the leggings to iron
                               if(entity.getEquipment().getLeggings()==null) {
                                   entity.getEquipment().setLeggings(ArmourInit.iron2);
                               }
                               //Checks if entity already has boots and if not sets the boots to iron
                               if(entity.getEquipment().getBoots()==null) {
                                   entity.getEquipment().setBoots(ArmourInit.iron3);
                               }
                           } else if (bool){
                               //Checks if entity already has a helmet and if not sets the helmet to diamond
                               if(entity.getEquipment().getHelmet()==null) {
                                   entity.getEquipment().setHelmet(ArmourInit.diamond);
                               }
                               //Checks if entity already has a chestplate and if not sets the chestplate to iron
                               if(entity.getEquipment().getChestplate()==null) {
                                   entity.getEquipment().setChestplate(ArmourInit.iron1);
                               }
                               //Checks if entity already has leggings and if not sets the leggings to iron
                               if(entity.getEquipment().getLeggings()==null) {
                                   entity.getEquipment().setLeggings(ArmourInit.iron2);
                               }
                               //Checks if entity already has boots and if not sets the boots to iron
                               if(entity.getEquipment().getBoots()==null) {
                                   entity.getEquipment().setBoots(ArmourInit.iron3);
                               }
                           }


                   }
               }


       }
    }


}
