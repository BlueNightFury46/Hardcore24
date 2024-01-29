package uk.co.shadowtrilogy.hardcore24.EventHandlers;

import org.bukkit.Bukkit;
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

        if(command.getName().equalsIgnoreCase("hardcore")){
            if(args.length >= 0){
                if(args.length == 1){
                    List<String> list = new ArrayList<>();
                    list.add("remove");
                    list.add("add");
                    list.add("permissions-add");
                    list.add("permissions-remove");
                    
                    return list;
                } else if (args.length == 2) {

                   return null;

                    
                }

            }
        }
   return null;
    }
}