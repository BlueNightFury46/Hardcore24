package uk.co.shadowtrilogy.hardcore24.EventHandlers;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ArmourInit {

    public static ItemStack diamond = new ItemStack(Material.DIAMOND_HELMET);
    public static ItemStack iron1 = new ItemStack(Material.IRON_CHESTPLATE);
    public static ItemStack iron2 = new ItemStack(Material.IRON_LEGGINGS);
    public static ItemStack iron3 = new ItemStack(Material.IRON_BOOTS);


    public static ItemStack bloodMoon1;
    public static ItemStack bloodMoon2;
    public static ItemStack bloodMoon3;
    public static ItemStack bloodMoon4;

    public static final ItemStack none = new ItemStack(Material.AIR);

    public static void init(){
           HarderInit();

    }

    public static void HarderInit(){

        bloodMoon1 = new ItemStack(Material.NETHERITE_HELMET);
        bloodMoon2 = new ItemStack(Material.NETHERITE_CHESTPLATE);
        bloodMoon3 = new ItemStack(Material.NETHERITE_LEGGINGS);
        bloodMoon4 = new ItemStack(Material.NETHERITE_BOOTS);




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




        ItemMeta b1 = bloodMoon1.getItemMeta();
        ItemMeta b2 = bloodMoon2.getItemMeta();
        ItemMeta b3 = bloodMoon3.getItemMeta();
        ItemMeta b4 = bloodMoon4.getItemMeta();

        b1.setDisplayName("{Hardcore24:Exclusive}DoNotObtainOrModify?.27");
        b2.setDisplayName("{Hardcore24:Exclusive}DoNotObtainOrModify?.28");
        b3.setDisplayName("{Hardcore24:Exclusive}DoNotObtainOrModify?.29");
        b4.setDisplayName("{Hardcore24:Exclusive}DoNotObtainOrModify?.30");

        b1.setUnbreakable(true);
        b2.setUnbreakable(true);
        b3.setUnbreakable(true);
        b4.setUnbreakable(true);

        bloodMoon1.setItemMeta(b1);
        bloodMoon2.setItemMeta(b2);
        bloodMoon3.setItemMeta(b3);
        bloodMoon4.setItemMeta(b4);



    }


}
