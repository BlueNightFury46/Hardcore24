package uk.co.shadowtrilogy.hardcore24.commands.worlds;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import uk.co.shadowtrilogy.hardcore24.Hardcore24;
import uk.co.shadowtrilogy.hardcore24.json.groups.groupdata;
import uk.co.shadowtrilogy.hardcore24.json.groups.json_location;

import java.util.*;

public class worldGroups implements CommandExecutor {


    final int SELECT_OPERATION = 0;
    final int SET_WORLD = 1;
    final int SET_GROUP = 2;
    final String SET = "SET_GR";

    final String CREATE_GROUP = "CREATE";
    final String SPAWN = "RESPAWN";
    final String REMOVE_GROUP = "REMOVE";


    final int CREATE_GROUP_NAME = 1;
    final String LIST_GROUPS = "LIST";


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if(!commandSender.hasPermission("hardcore.manage.groups")){
            commandSender.sendMessage(ChatColor.RED + "You don't have permission to use this command...");
            return true;
        }


        if(args.length>0) {

            if(args[SELECT_OPERATION].toUpperCase().contains(SET)){

                if(args.length<3){
                    commandSender.sendMessage(ChatColor.RED + "Argument error! More arguments are needed");
                    return true;
                }

                if(!containsWorld(args[SET_WORLD])){
                    commandSender.sendMessage(ChatColor.RED + "Error! World \"" + args[SET_WORLD] + "\" not found...");
                    return true;
                }

                if(!containsGroup(args[SET_GROUP])){
                    commandSender.sendMessage(ChatColor.RED + "Error! Group \"" + args[SET_GROUP] + "\" not found...");
                    return true;
                }




                Hardcore24.worlds.put(args[SET_WORLD], args[SET_GROUP]);
                for(groupdata group : Hardcore24.groups){
                    if(group.group_name.equalsIgnoreCase(args[SET_GROUP])){

                        try {
                            if(group.worlds==null){
                                group.worlds = new HashSet<>();
                                group.worlds.add(args[SET_WORLD]);
                            }
                            group.worlds.add(args[SET_WORLD]);
                        }catch(NullPointerException ex){
                            group.worlds = new HashSet<>();
                            group.worlds.add(args[SET_WORLD]);
                        }
                    }
                    else {
                        try {

                            if(group.worlds.contains((args[SET_WORLD]))){
                                group.worlds.remove(args[SET_WORLD]);

                            }
                        }catch(NullPointerException ex){

                        }

                    }
                }

                commandSender.sendMessage(ChatColor.BLUE + "Set group for world \"" + args[SET_WORLD] + "\" to " + args[SET_GROUP]);

                return true;






            }


            if(args[SELECT_OPERATION].toUpperCase().contains(CREATE_GROUP)){

                if(!(commandSender instanceof Player)){
                    commandSender.sendMessage(ChatColor.RED + "Only players can use this command...");
                    return true;
                }


                if(args.length<2){
                    commandSender.sendMessage(ChatColor.RED + "Syntax error");
                    return true;
                }

                if(args[CREATE_GROUP_NAME].equalsIgnoreCase("") || args[CREATE_GROUP_NAME].equalsIgnoreCase(" ")){
                    commandSender.sendMessage(ChatColor.RED + "Error! Group name can not be blank...");
                    return true;
                }

                if(groupAlreadyExists(args[CREATE_GROUP_NAME])){
                    commandSender.sendMessage(ChatColor.RED + "Error! Group \"" + args[CREATE_GROUP_NAME] + "\" already exists...");
                    return true;

                }

                Player player = ((Player) commandSender).getPlayer();

                groupdata NEW_GROUP = new groupdata(args[CREATE_GROUP_NAME], new json_location(0, 0, 0, player.getWorld().getName(), false), null);

                Hardcore24.groups.add(NEW_GROUP);
                commandSender.sendMessage(ChatColor.GOLD + "Created new group \"" + args[CREATE_GROUP_NAME] + "\"!");
                return true;

            }

            else if(args[SELECT_OPERATION].toUpperCase().contains(LIST_GROUPS)){

                String res = ChatColor.GREEN+"World groups:\n";
                for(groupdata group : Hardcore24.groups){
                   res += ChatColor.BLUE + group.group_name + "\n";

                   try {

                       Set<String> world_set = group.worlds;

                       for (String str : group.worlds){
                           res+=ChatColor.BLUE+"  - ";
                           res+=ChatColor.LIGHT_PURPLE+str + "\n";
                       }
                   }catch(NullPointerException ex){

                   }
                   res+="\n";

                }

                commandSender.sendMessage(res);


            } if(args[SELECT_OPERATION].toUpperCase().contains(REMOVE_GROUP)){




                if(args.length<2){
                    commandSender.sendMessage(ChatColor.RED + "Syntax error! More arguments required");
                    return true;
                }

                if(!groupAlreadyExists(args[CREATE_GROUP_NAME])){
                    commandSender.sendMessage(ChatColor.RED + "Error! Group \"" + args[CREATE_GROUP_NAME] + "\" does not exist...");
                    return true;

                }


                List<groupdata> toRemove = new ArrayList<>();

                for(groupdata group : Hardcore24.groups){
                    if(group.group_name.equalsIgnoreCase(args[CREATE_GROUP_NAME])){
                        toRemove.add(group);
                    }
                }

                Hardcore24.groups.removeAll(toRemove);


                //TODO Remove world links
                commandSender.sendMessage(ChatColor.RED + "Deleted group \"" + args[CREATE_GROUP_NAME] + "\"! Warning, all worlds in the deleted group will still try to use that group, which may lead to unintended behavior... Please change, or delete their group...");
                return true;

            }
            if(args[SELECT_OPERATION].toUpperCase().contains(SPAWN)){

                if(!(commandSender instanceof Player)){
                    commandSender.sendMessage(ChatColor.RED + "Only players can use this command...");
                    return true;
                }


                if(args.length<1){
                    commandSender.sendMessage(ChatColor.RED + "Syntax error! More arguments required");
                    return true;
                }

                if(!groupAlreadyExists(args[CREATE_GROUP_NAME])){
                    commandSender.sendMessage(ChatColor.RED + "Error! Group \"" + args[CREATE_GROUP_NAME] + "\" does not exist...");
                    return true;

                }

                Player player = ((Player) commandSender).getPlayer();



                for(groupdata group : Hardcore24.groups){
                    if(group.group_name.equalsIgnoreCase(args[CREATE_GROUP_NAME])){
                        group.respawn_location = new json_location(player.getX(), player.getY(), player.getZ(), player.getWorld().getName(), true);

                    }
                }

                commandSender.sendMessage(ChatColor.AQUA + "Set respawn location for group " + ChatColor.LIGHT_PURPLE + "\"" + args[CREATE_GROUP_NAME] + "\"" + ChatColor.BLUE+" to " + ChatColor.GREEN + player.getLocation());
                return true;



            }




        }




        return true;


    }


    boolean containsWorld(String world_to_be_searched_for){
        for(World world : Hardcore24.plugin.getServer().getWorlds()){
            if(world.getName().equalsIgnoreCase(world_to_be_searched_for)){
                return true;
            }
        }
        return false;
    }

    boolean containsGroup(String group_to_be_searched_for){
        for(groupdata group : Hardcore24.plugin.groups){
            if(group.group_name.equalsIgnoreCase(group_to_be_searched_for)){
                return true;
            }
        }
        return false;
    }


    boolean groupAlreadyExists(String group){
        for(groupdata data : Hardcore24.groups){
            if(data.group_name.equalsIgnoreCase(group)){
                return true;
            }
        }
        return false;
    }
}
