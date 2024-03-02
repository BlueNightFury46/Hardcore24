package uk.co.shadowtrilogy.hardcore24;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;
import uk.co.shadowtrilogy.hardcore24.EventHandlers.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class Hardcore24 extends JavaPlugin {

    public static FileConfiguration configuration;

    public static File file;
    public static String Blood_Moon_Message;
    public static Boolean playOnce = true;
    public static Boolean day = false;
    public static boolean MESSAGE_ONCE = true;
    public static double bDuration;
    public static long d;
    public static int phaseCounter = 0;
    public static String LastBloodMoon = null;

    public static boolean bloodmoon = false;
    public static boolean removeArmour = false;

    public static Hardcore24 plugin;
    public static HashMap<UUID, Boolean> map2 = new HashMap<>();
    public static Map map = map2;

    @Override
    public void onEnable() {
        // Plugin startup logic
        //this.saveDefaultConfig();








        //Instantiates the whole plugin
        plugin = this;
        Boolean bool = Hardcore24.plugin.getConfig().getBoolean("hardcore-config.harder-mobs");
        Boolean Moon = Hardcore24.plugin.getConfig().getBoolean("hardcore-config.do-blood-moon");
        d = (long) plugin.getConfig().getDouble("hardcore-config.blood-moon-duration") * 60 * 60 * 20;
        Blood_Moon_Message = plugin.getConfig().getString("hardcore-message-config.blood-moon-message");

        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new ServerLoad(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeath(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerRespawn(), this);

        if(bool == true || Moon == true) {
            Bukkit.getPluginManager().registerEvents(new MoreMobs(), this);
        }


        //Registers commands

        getCommand("hardcore").setExecutor(new hardcore());
        getCommand("hardcore").setTabCompleter(new hardcoreAutoComplete());

        //Generates a .players file to store the "banned" players uuids

        plugin.saveDefaultConfig();
        //Saves the config

        ArmourInit.init();
        //initiates the mob armour
        getLogger().info("Starting Hardcore24 by BlueNightFury46");


        try {
            fileGeneration();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }


        //
        getLogger().info("Hardcore24 is active and should be working as usual");



        if(Moon == true) {
            World world_hardcore = Bukkit.getWorld(plugin.getConfig().getString("hardcore-world.hardcore-normal"));

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
                if (MESSAGE_ONCE == true) {
                    MESSAGE_ONCE = false;
                    getLogger().info("Removing Blood Moon armour from mobs...");
                }

            }
        }


    }



    public void fileGeneration() throws IOException, InvalidConfigurationException, FileNotFoundException {
        file = new File(getDataFolder(), "permissions.yml");
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                saveResource("permissions.yml", false);
            }
        }catch (NullPointerException exception){
            file.getParentFile().mkdirs();
            saveResource("permissions.yml", false);
        }
        configuration = new YamlConfiguration();
        configuration.load(file);
    }




    @Override
    public void onDisable() {
        // Plugin shutdown logic

        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                saveResource("permissions.yml", false);
            }
        } catch (NullPointerException e) {
            saveResource("permissions.yml", false);
        }


        if (Bukkit.getOfflinePlayers().length > 0) {
            try {
                configuration.load(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InvalidConfigurationException e) {
                throw new RuntimeException(e);
            }


            for (OfflinePlayer p2 : Bukkit.getOfflinePlayers()) {
                String s = "permissions.players." + p2.getUniqueId();
                if (hardcore.permissionAttachmentHashMap.containsKey(p2.getUniqueId())) {
                    configuration.set(s, "hardcore.commands");
                }
            }


            try {
                try {
                    configuration.save(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (NullPointerException e){

            }

        }
    }
}
