package uk.co.shadowtrilogy.hardcore24.commands.worlds;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.co.shadowtrilogy.hardcore24.Hardcore24;
import uk.co.shadowtrilogy.hardcore24.json.groups.groupdata;

import java.util.ArrayList;
import java.util.List;

public class worldGroupsTabCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("group")) {
            if (args.length >= 0) {
                switch (args.length) {

                    case 1: {
                        List<String> list = new ArrayList<>();
                        if (commandSender.isOp() || commandSender.hasPermission("hardcore.manage.groups")) {
                            list.add("SET_GROUP");
                            list.add("CREATE_GROUP");
                            list.add("LIST_GROUPS");
                            list.add("SET_RESPAWN");
                            list.add("REMOVE_GROUP");




                            return list;

                        }

                    }

                    case 2: {
                        List<String> list = new ArrayList<>();
                        if (commandSender.isOp() || commandSender.hasPermission("hardcore.manage.groups")) {

                           if(args[0].toUpperCase().contains("SET") && args[0].toUpperCase().contains("G")) {
                               for (World world : Hardcore24.plugin.getServer().getWorlds()) {
                                   list.add(world.getName());
                               }
                           }
                            if(args[0].toUpperCase().contains("REMOVE") || args[0].toUpperCase().contains("RESPAWN")) {
                                ArrayList<groupdata> groups = new ArrayList<>(Hardcore24.groups);
                                for(groupdata g : groups){
                                    list.add(g.group_name);
                                }
                            }

                            if(args[0].toUpperCase().contains("CREATE")) {

                            }



                            return list;


                        }


                    }

                    case 3: {
                        List<String> list = new ArrayList<>();
                        if (commandSender.isOp() || commandSender.hasPermission("hardcore.manage.groups")) {

                            if(args[0].toUpperCase().contains("SET")) {
                                for (groupdata group : Hardcore24.groups) {
                                    list.add(group.group_name);
                                }
                            }

                            if(args[0].toUpperCase().contains("CREATE")) {

                            }



                            return list;


                        }


                    }
            }

            }
        }


            return null;
    }
}
