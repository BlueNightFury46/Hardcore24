package uk.co.shadowtrilogy.hardcore24;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import uk.co.shadowtrilogy.hardcore24.commands.players.playerManager;
import uk.co.shadowtrilogy.hardcore24.commands.players.playerManagerTabComplete;
import uk.co.shadowtrilogy.hardcore24.commands.reload.reload;
import uk.co.shadowtrilogy.hardcore24.commands.worlds.worldGroups;
import uk.co.shadowtrilogy.hardcore24.commands.worlds.worldGroupsTabCompleter;
import uk.co.shadowtrilogy.hardcore24.events.PlayerDeathManager;
import uk.co.shadowtrilogy.hardcore24.events.PlayerTransportManager;
import uk.co.shadowtrilogy.hardcore24.json.groups.Group_dataJSON;
import uk.co.shadowtrilogy.hardcore24.json.groups.groupdata;
import uk.co.shadowtrilogy.hardcore24.json.groups.groupdataContainer;
import uk.co.shadowtrilogy.hardcore24.json.player_data.Player_dataJSON;
import uk.co.shadowtrilogy.hardcore24.json.player_data.playerdataContainer;
import uk.co.shadowtrilogy.hardcore24.json.world_group.World_groupJSON;
import uk.co.shadowtrilogy.hardcore24.json.world_group.worldgroupdataContainer;


import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class Hardcore24 extends JavaPlugin {

    @NotNull public static HashMap<UUID, PlayerDeathData> deadPlayers = new HashMap<>();

    @NotNull public static HashMap<String, String> worlds = new HashMap<>();
    @NotNull public static Set<groupdata> groups = new HashSet<groupdata>();


    final String deadPlayers_filename = "players.json";
    final String worlds_filename = "worlds.json";
    final String groups_filename = "groups.json";



    public static Hardcore24 plugin;

    @NotNull public static boolean DROP_ENDERCHEST = true;
    @NotNull public static boolean WARNINGS = true;
    //Hours
    @NotNull public static long DEATHBAN_TIME_HRS = 24;
    @NotNull public static long DEATHBAN_TIME_DAYS = 0;

    @NotNull public static long DEATHBAN_TIME_MINS = 0;




    @Override
    public void onEnable() {
        // Plugin startup logic
        this.saveDefaultConfig();


        plugin = this;

        loadConfig();



        getServer().getPluginManager().registerEvents(new PlayerDeathManager(), this);
        getServer().getPluginManager().registerEvents(new PlayerTransportManager(), this);


        getCommand("group").setExecutor(new worldGroups());
        getCommand("group").setTabCompleter(new worldGroupsTabCompleter());

        getCommand("player").setExecutor(new playerManager());
        getCommand("player").setTabCompleter(new playerManagerTabComplete());

        getCommand("reload").setExecutor(new reload());




    }


    public void loadConfig(){



        File death_data  = new File(getDataFolder(), deadPlayers_filename);
       deadPlayers = Player_dataJSON.jsonInit(death_data, deadPlayers).players;

        File world_data  = new File(getDataFolder(), worlds_filename);
        worlds = World_groupJSON.jsonInit(world_data, worlds).world_groups;

        File group_data  = new File(getDataFolder(), groups_filename);
        groups = Group_dataJSON.jsonInit(group_data, groups).groups;


        DROP_ENDERCHEST = getConfig().getBoolean("drop-enderchest-on-death");
        DEATHBAN_TIME_DAYS = getConfig().getInt("deathban-time.days");
        DEATHBAN_TIME_HRS = getConfig().getInt("deathban-time.hours");
        DEATHBAN_TIME_MINS = getConfig().getInt("deathban-time.minutes");

    }

    public static void reloadPlugin(){


        final String deadPlayers_filename = "players.json";
        final String worlds_filename = "worlds.json";
        final String groups_filename = "groups.json";


        File death_data  = new File(Hardcore24.plugin.getDataFolder(), deadPlayers_filename);
        playerdataContainer players_container = new playerdataContainer(deadPlayers);
        Player_dataJSON.jsonSave(players_container, death_data);

        //worlds.json shutdown logic
        File world_data  = new File(Hardcore24.plugin.getDataFolder(), worlds_filename);
        worldgroupdataContainer worlds_container = new worldgroupdataContainer(worlds);
        World_groupJSON.jsonSave(worlds_container, world_data);

        //groups.json shutdown logic
        File group_data  = new File(Hardcore24.plugin.getDataFolder(), groups_filename);
        groupdataContainer groups_container = new groupdataContainer(groups);
        Group_dataJSON.jsonSave(groups_container, group_data);

        Hardcore24.plugin.saveDefaultConfig();


        Hardcore24.plugin.reloadConfig();

        deadPlayers = Player_dataJSON.jsonInit(death_data, deadPlayers).players;

        worlds = World_groupJSON.jsonInit(world_data, worlds).world_groups;

        groups = Group_dataJSON.jsonInit(group_data, groups).groups;


        DROP_ENDERCHEST = Hardcore24.plugin.getConfig().getBoolean("drop-enderchest-on-death");
        DEATHBAN_TIME_DAYS = Hardcore24.plugin.getConfig().getInt("deathban-time.days");
        DEATHBAN_TIME_HRS = Hardcore24.plugin.getConfig().getInt("deathban-time.hours");
        DEATHBAN_TIME_MINS = Hardcore24.plugin.getConfig().getInt("deathban-time.minutes");
    }





    @Override
    public void onDisable() {
        // Plugin shutdown logic

        //players.json shutdown logic
        File death_data  = new File(getDataFolder(), deadPlayers_filename);
        playerdataContainer players_container = new playerdataContainer(deadPlayers);
        Player_dataJSON.jsonSave(players_container, death_data);

        //worlds.json shutdown logic
        File world_data  = new File(getDataFolder(), worlds_filename);
        worldgroupdataContainer worlds_container = new worldgroupdataContainer(worlds);
        World_groupJSON.jsonSave(worlds_container, world_data);

        //groups.json shutdown logic
        File group_data  = new File(getDataFolder(), groups_filename);
        groupdataContainer groups_container = new groupdataContainer(groups);
        Group_dataJSON.jsonSave(groups_container, group_data);


        getLogger().info("Disabling Hardcore24!");

        }
    }
