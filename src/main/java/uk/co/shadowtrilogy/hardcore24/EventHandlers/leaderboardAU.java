package uk.co.shadowtrilogy.hardcore24.EventHandlers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class leaderboardAU implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("leaderboard")) {
            if (args.length >= 0) {
                Player player = (((Player) commandSender).getPlayer());

                if(args.length == 1 && player.isOp()){
                    List<String> list = new ArrayList<>();
                    list.add(player.getWorld().getName());
                    return list;
                } else if (args.length == 2 && player.isOp()) {
                    List<String> list = new ArrayList<>();
                    list.add(String.valueOf(player.getX()));
                    return list;
                }else if (args.length == 3 && player.isOp()) {
                    List<String> list = new ArrayList<>();
                    list.add(String.valueOf(player.getY()));
                    return list;
                }else if (args.length == 4 && player.isOp()) {
                    List<String> list = new ArrayList<>();
                    list.add(String.valueOf(player.getZ()));
                    return list;
                } else if (args.length == 5 && player.isOp()) {
                    List<String> list = new ArrayList<>();
                    list.add("name");
                    return list;
                } else {
                    List<String> list = new ArrayList<>();
                    list.add("");
                    return list;
                }

            }
            }
        return null;
    }
}
