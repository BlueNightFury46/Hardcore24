package uk.co.shadowtrilogy.hardcore24;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
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
    public static FileConfiguration config;

    public static File file;
    public static File Leaderboard;
    public static Boolean playOnce = true;
    public static Boolean day = false;
    public static double bDuration;
    public static long d;
    public static int phaseCounter = 0;

    public static boolean bloodmoon = false;
    public static boolean removeArmour = false;
    public static boolean LeaderboardEnabled;

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
        LeaderboardEnabled = Hardcore24.plugin.getConfig().getBoolean("hardcore-config.leaderboard");
        d = (long) plugin.getConfig().getDouble("hardcore-config.blood-moon-duration") * 60 * 60 * 20;

        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new ServerLoad(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeath(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerRespawn(), this);

        if(bool == true || Moon == true) {
            Bukkit.getPluginManager().registerEvents(new MoreMobs(), this);
        }
     //   if(LeaderboardEnabled == true){
            Bukkit.getPluginManager().registerEvents(new LeaderboardUpdate(), this);

      //  }


        //Registers commands

        getCommand("hardcore").setExecutor(new hardcore());
        getCommand("hardcore").setTabCompleter(new hardcoreAutoComplete());

        getCommand("leaderboard").setExecutor(new Leaderboard());
        getCommand("leaderboard").setTabCompleter(new leaderboardAU());

        //Generates a .players file to store the "banned" players uuids

        plugin.saveDefaultConfig();
        //Saves the config

        ArmourInit.init();
        //initiates the mob armour
        getLogger().info("Starting Hardcore24 by BlueNightFury46");


        try {
            fileGeneration();
            LeaderBoardfileGeneration();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }


        //
        getLogger().info("Hardcore24 is active and should be working as usual");
        


    }



    public FileConfiguration getConfiguration(){
        return this.config;
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



    public void LeaderBoardfileGeneration() throws IOException, InvalidConfigurationException, FileNotFoundException {
        Leaderboard = new File(getDataFolder(), "leaderboard.yml");
        try {
            if (!Leaderboard.exists()) {
                Leaderboard.getParentFile().mkdirs();
                saveResource("leaderboard.yml", false);
            }
        }catch (NullPointerException exception){
            Leaderboard.getParentFile().mkdirs();
            saveResource("leaderboard.yml", false);
        }
        config = new YamlConfiguration();
        config.load(Leaderboard);
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
        try {
            if (!Leaderboard.exists()) {
                Leaderboard.getParentFile().mkdirs();
                saveResource("leaderboard.yml", false);
            }
        } catch (NullPointerException e) {
            saveResource("leaderboard.yml", false);
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
                    config.save(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (NullPointerException e){

            }

        }
    }
}
