package uk.co.shadowtrilogy.hardcore24.EventHandlers;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class hardcoreAutoComplete implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase("hardcore")) {
            if (args.length >= 0) {
                if (args.length == 1) {
                    List<String> list = new ArrayList<>();
                    if (commandSender.isOp() || commandSender.hasPermission("hardcore.commands")) {
                        list.add("remove");
                        list.add("add");
                        list.add("log");
                        list.add("ping");

                        return list;
                    }
                } else if(args.length == 2){
                    List<String> list = new ArrayList<>();
                    for(Player player : Bukkit.getOnlinePlayers()) {

                        list.add(player.getName());

                    }
                    list.add("Blood-Moon");
                    return list;

                } else if(args.length == 3){
                    List<String> list = new ArrayList<>();
                    list.add("1.0");
                    list.add("2.0");
                    list.add("3.0");
                    list.add("4.0");
                    list.add("5.0");
                    list.add("6.0");
                    list.add("7.0");
                    list.add("8.0");
                    list.add("9.0");
                    list.add("10.0");
                    list.add("11.0");
                    list.add("12.0");
                    list.add("13.0");
                    list.add("14.0");
                    list.add("15.0");
                    list.add("16.0");
                    list.add("17.0");
                    list.add("18.0");
                    list.add("19.0");
                    list.add("20.0");
                    list.add("21.0");
                    list.add("22.0");
                    list.add("23.0");
                    list.add("24.0");




                    return list;

                }


            }
            return null;
        }
        return null;
    }
}
