package uk.co.shadowtrilogy.hardcore24.EventHandlers;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.LivingEntity;
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

    @EventHandler
    public void event(EntitySpawnEvent event){
       Boolean bool = Hardcore24.plugin.getConfig().getBoolean("hardcore-config.harder-mobs");
        World world_hardcore = Bukkit.getWorld(Hardcore24.plugin.getConfig().getString("hardcore-world.hardcore-normal").toString());
       if(bool == true) {
           if (world_hardcore.getFullTime() < 23000 && world_hardcore.getFullTime() > 13000) {
               long phase = world_hardcore.getFullTime() / 24000;
               if (phase == 8) {

                       world_hardcore.playSound(event.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1.2f, 1.0f);

                   for (LivingEntity entity : world_hardcore.getLivingEntities()) {
                       if (entity.getType().equals(EntityType.SKELETON) || entity.getType().equals(EntityType.ZOMBIE))
                           entity.getEquipment().getHelmet().setType(Material.DIAMOND_HELMET);
                       entity.getEquipment().getHelmet().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                       entity.getEquipment().setHelmetDropChance(0.1f);

                       entity.getEquipment().getChestplate().setType(Material.IRON_CHESTPLATE);
                       entity.getEquipment().getChestplate().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);

                       entity.getEquipment().getLeggings().setType(Material.IRON_LEGGINGS);
                       entity.getEquipment().getLeggings().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);

                       entity.getEquipment().getBoots().setType(Material.IRON_BOOTS);
                       entity.getEquipment().getBoots().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                   }
               }

           }

       }
    }


}
