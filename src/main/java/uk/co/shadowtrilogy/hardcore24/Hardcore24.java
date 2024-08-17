package uk.co.shadowtrilogy.hardcore24;

import org.bukkit.*;
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
import java.time.LocalDateTime;
import java.util.*;

public final class Hardcore24 extends JavaPlugin {
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

    public static boolean ShulkerPlacement = false;

    public static Hardcore24 plugin;
    public static HashMap<UUID, LocalDateTime> map2 = new HashMap<>();
    public static Map map = map2;

   public static World NETHER_WORLD;
    public static World OVERWORLD;
    public static World END_WORLD;
    public static double DEATH_BAN_TIME;
    public static boolean DEATHBAN_EXCLUDE_SETTING;
    public static World RESPAWN_WORLD;
    public static double RESPAWN_X;
    public static double RESPAWN_Y;
    public static double RESPAWN_Z;

    public static Map<UUID, String> DamagedPlayers = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.saveDefaultConfig();

        plugin = this;

        try{


        NETHER_WORLD = Bukkit.getWorld(Hardcore24.plugin.getConfig().getString("hardcore-world.hardcore-nether"));
        OVERWORLD = Bukkit.getWorld(Hardcore24.plugin.getConfig().getString("hardcore-world.hardcore-normal"));
        END_WORLD = Bukkit.getWorld(Hardcore24.plugin.getConfig().getString("hardcore-world.hardcore-end"));

        DEATH_BAN_TIME = Hardcore24.plugin.getConfig().getDouble("hardcore-config.death-ban-time");

        DEATHBAN_EXCLUDE_SETTING = Hardcore24.plugin.getConfig().getBoolean("hardcore-config.death-ban-exclude-ops");


            if (Bukkit.getWorld(Hardcore24.plugin.getConfig().get("respawn-location.world").toString()) == null) {
                getLogger().info(ChatColor.RED + "FATAL ERROR! WORLD \"" + RESPAWN_WORLD.getName() + "\" DOES NOT EXIST...");
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage(ChatColor.RED + "FATAL ERROR! WORLD \"" + RESPAWN_WORLD.getName() + "\" DOES NOT EXIST...");
                }
                onDisable();



                RESPAWN_WORLD = Bukkit.getWorld(Hardcore24.plugin.getConfig().get("respawn-location.world").toString());

            }  } catch(NullPointerException e){
            getLogger().info("FATAL ERROR! RESPAWN WORLD IS NULL");
            onDisable();
        }
        RESPAWN_X = Hardcore24.plugin.getConfig().getDouble("respawn-location.x");
        RESPAWN_Y = Hardcore24.plugin.getConfig().getDouble("respawn-location.y");
        RESPAWN_Z = Hardcore24.plugin.getConfig().getDouble("respawn-location.z");

        boolean FORCE_PERMISSION_OVERRIDE = Hardcore24.plugin.getConfig().getBoolean("hardcore-config.allow-instant-respawn");


        //Instantiates the whole plugin
        Boolean bool = Hardcore24.plugin.getConfig().getBoolean("hardcore-config.harder-mobs");
        Boolean Moon = Hardcore24.plugin.getConfig().getBoolean("hardcore-config.do-blood-moon");
        d = (long) plugin.getConfig().getDouble("hardcore-config.blood-moon-duration") * 60 * 60 * 20;
        Blood_Moon_Message = plugin.getConfig().getString("hardcore-message-config.blood-moon-message");
        ShulkerPlacement = plugin.getConfig().getBoolean("hardcore-config.can't-place-shulker-during-damage");




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

        getCommand("hardcore24-version").setExecutor(new hardcore24Version());

        //Generates a .players file to store the "banned" players uuids


        ArmourInit.init();

        if(ShulkerPlacement == true){
            Bukkit.getPluginManager().registerEvents(new PlayerDamage(), this);
            Bukkit.getPluginManager().registerEvents(new PlayerPlace(), this);
            Bukkit.getPluginManager().registerEvents(new InventoryOpenEvent(), this);
        }

        //initiates the mob armour
        getLogger().info("Starting Hardcore24 by BlueNightFury46");

        //
        getLogger().info("Hardcore24 is active and should be working as usual");
try {

    if(RESPAWN_WORLD==null){
        onDisable();
    }

    if (!Bukkit.getWorlds().contains(RESPAWN_WORLD)) {
        getLogger().info(ChatColor.RED + "FATAL ERROR! WORLD \"" + RESPAWN_WORLD.getName() + "\" DOES NOT EXIST...");
        onDisable();
    }
} catch(NullPointerException e){
    this.getLogger().info("FATAL ERROR! THE RESPAWN WORLD IN THE CONFIG DOES NOT EXIST! TRY CHANGING IT AND RESTARTING THE SERVER!\n\n NOTE: IT MAY BE CASE SENSITIVE");
}

        if(FORCE_PERMISSION_OVERRIDE){
            if(!OVERWORLD.getGameRuleValue(GameRule.DO_IMMEDIATE_RESPAWN)){
                getLogger().info(ChatColor.RED + "Set immediate respawn to true for world \"" + OVERWORLD.getName() + "\"\n!");
                for(Player player : Bukkit.getOnlinePlayers()){
                    player.sendMessage(ChatColor.RED + "Set immediate respawn to true for world \"" + OVERWORLD.getName() + "\"\n!");

                }
                OVERWORLD.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
            }

            if(!NETHER_WORLD.getGameRuleValue(GameRule.DO_IMMEDIATE_RESPAWN)){
                getLogger().info(ChatColor.RED + "Set immediate respawn to true for world \"" + NETHER_WORLD.getName() + "\"\n!");
                for(Player player : Bukkit.getOnlinePlayers()){
                    player.sendMessage(ChatColor.RED + "Set immediate respawn to true for world \"" + NETHER_WORLD.getName() + "\"\n!");

                }
                NETHER_WORLD.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
            }

            if(!END_WORLD.getGameRuleValue(GameRule.DO_IMMEDIATE_RESPAWN)){
                getLogger().info(ChatColor.RED + "Set immediate respawn to true for world \"" + END_WORLD.getName() + "\"\n!");
                for(Player player : Bukkit.getOnlinePlayers()){
                    player.sendMessage(ChatColor.RED + "Set immediate respawn to true for world \"" + END_WORLD.getName() + "\"\n!");

                }
                END_WORLD.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
            }
        }


        if(Moon == true) {

            getLogger().info("Preparing for Blood Moon Mobs Armour Removal ");
            Bukkit.getScheduler().runTaskLater(Hardcore24.plugin, () -> {

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
            }, 250L);
            getLogger().info("Cleared all blood moon armour from mobs!");
        }


    }





    @Override
    public void onDisable() {
        // Plugin shutdown logic

        getLogger().info("Disabling Hardcore24!");

        }
    }
