package uk.co.shadowtrilogy.hardcore24.EventHandlers;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ArmourInit {

    public static final ItemStack diamond = new ItemStack(Material.DIAMOND_HELMET);
    public static final ItemStack iron1 = new ItemStack(Material.IRON_CHESTPLATE);
    public static final ItemStack iron2 = new ItemStack(Material.IRON_LEGGINGS);
    public static final ItemStack iron3 = new ItemStack(Material.IRON_BOOTS);


    public static final ItemStack bloodMoon1 = new ItemStack(Material.NETHERITE_HELMET);
    public static final ItemStack bloodMoon2 = new ItemStack(Material.NETHERITE_HELMET);
    public static final ItemStack bloodMoon3 = new ItemStack(Material.NETHERITE_HELMET);
    public static final ItemStack bloodMoon4 = new ItemStack(Material.NETHERITE_HELMET);

    public static final ItemStack none = new ItemStack(Material.AIR);

    public static void init(){
           HarderInit();

    }

    public static void HarderInit(){
        ItemMeta d = diamond.getItemMeta();
        ItemMeta i1 = iron1.getItemMeta();
        ItemMeta i2 = iron2.getItemMeta();
        ItemMeta i3 = iron3.getItemMeta();

        d.setDisplayName("{Hardcore24:Exclusive}DoNotObtainOrModify?.23");
        i1.setDisplayName("{Hardcore24:Exclusive}DoNotObtainOrModify?.24");
        i2.setDisplayName("{Hardcore24:Exclusive}DoNotObtainOrModify?.25");
        i3.setDisplayName("{Hardcore24:Exclusive}DoNotObtainOrModify?.26");

        d.setUnbreakable(true);
        i1.setUnbreakable(true);
        i2.setUnbreakable(true);
        i3.setUnbreakable(true);

        diamond.setItemMeta(d);
        iron1.setItemMeta(i1);
        iron2.setItemMeta(i2);
        iron3.setItemMeta(i3);



    }


}
