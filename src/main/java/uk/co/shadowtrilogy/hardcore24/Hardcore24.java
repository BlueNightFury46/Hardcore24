package uk.co.shadowtrilogy.hardcore24;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
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
    public static Boolean playOnce = true;

    public static Hardcore24 plugin;
    public static HashMap<UUID, Boolean> map2 = new HashMap<>();
    public static Map map = map2;
    Boolean bool = Hardcore24.plugin.getConfig().getBoolean("hardcore-config.harder-mobs");
    Boolean Moon = Hardcore24.plugin.getConfig().getBoolean("hardcore-config.do-blood-moon");

    @Override
    public void onEnable() {
        // Plugin startup logic
        //this.saveDefaultConfig();




        //Instantiates the whole plugin
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new ServerLoad(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeath(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerRespawn(), this);
        if(bool == true || Moon == true) {
            Bukkit.getPluginManager().registerEvents(new MoreMobs(), this);
        }

        //initiates the mob armour
        ArmourInit.init();

        plugin = this;
        getCommand("hardcore").setExecutor(new hardcore());
        getCommand("hardcore").setTabCompleter(new hardcoreAutoComplete());

        //Generates a .players file to store the "banned" players uuids

        plugin.saveDefaultConfig();
        //Saves the config




        try {
            fileGeneration();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }


        //
        getLogger().info("Hardcore24 is active and should be working as usual");
        


    }



    public FileConfiguration getConfiguration(){
        return this.configuration;
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

        if (!file.exists()) {
            file.getParentFile().mkdirs();
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
                configuration.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
    }
}
