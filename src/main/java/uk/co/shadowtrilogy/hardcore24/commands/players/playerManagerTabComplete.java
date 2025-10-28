package uk.co.shadowtrilogy.hardcore24.commands.players;

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

public class playerManagerTabComplete implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("player")) {
            if (args.length >= 0) {
                switch (args.length) {

                    case 1: {
                        List<String> list = new ArrayList<>();
                        if (commandSender.isOp() || commandSender.hasPermission("hardcore.manage.players")) {
                            list.add("ban");
                            list.add("unban");
                            list.add("list");




                            return list;

                        }

                    }

                    case 2: {
                        //List<String> list = new ArrayList<>();
                        if (commandSender.isOp() || commandSender.hasPermission("hardcore.manage.groups")) {

                          return null;


                        }


                    }

                    case 3: {
                        List<String> list = new ArrayList<>();
                        if (commandSender.isOp() || commandSender.hasPermission("hardcore.manage.players")) {

                            if(args[0].toUpperCase().contains("ban")) {
                                for (World w : Hardcore24.plugin.getServer().getWorlds()) {
                                    list.add(w.getName());
                                }
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
