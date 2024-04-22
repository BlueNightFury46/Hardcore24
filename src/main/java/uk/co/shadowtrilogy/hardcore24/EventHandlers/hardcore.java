package uk.co.shadowtrilogy.hardcore24.EventHandlers;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.LeashHitch;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;
import uk.co.shadowtrilogy.hardcore24.Hardcore24;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class hardcore implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("§cWarning only players can use this command§c");
            return true;
        }


        Player player = (((Player) commandSender).getPlayer());


        if (command.getName().equalsIgnoreCase("hardcore")) {


            if (player.hasPermission("hardcore.commands") || player.isOp() == true) {
                try {
                    if (args.length > 0) {

                        if (args.length > 1) {
                            //Remove tree
                            if (args[0].toLowerCase().equals("remove")) {

                                if (!args[1].isEmpty()) {
                                    if(args.length > 2) {
                                        long time = (long) (Double.valueOf(args[2]) * 60 * 60 * 20);
                                        OfflinePlayer p;
                                        commandSender.sendMessage(ChatColor.BLUE + "Scheduled player \"" + ChatColor.LIGHT_PURPLE + args[1] + ChatColor.BLUE + "\" to be unbanned from hardcore in " + time / 60 / 60 / 20 + "hrs");
                                        for (@NotNull OfflinePlayer pl : Bukkit.getOfflinePlayers()) {
                                            if (args[1].equalsIgnoreCase(pl.getName())) {
                                               p = pl;
                                                Bukkit.getScheduler().runTaskLater(Hardcore24.plugin, () -> {
                                                    if (Hardcore24.map.containsKey(p.getUniqueId())) {
                                                        LocalDateTime DateTime = (LocalDateTime) Hardcore24.map.get(p.getUniqueId());
                                                        Hardcore24.map.remove(p.getUniqueId(), DateTime);
                                                        if(Bukkit.getOnlinePlayers().contains(p)) {
                                                            Player player1 = Bukkit.getPlayer(p.getUniqueId());
                                                            player1.setPlayerListName(player1.getName());
                                                        }
                                                    }
                                                }, time);
                                                return true;
                                            }
                                        }

                                    } else {

                                        for (@NotNull OfflinePlayer p : Bukkit.getOfflinePlayers()) {
                                            if (args[1].equalsIgnoreCase(p.getName())) {
                                                Hardcore24.map.remove(p.getUniqueId());
                                                player.sendMessage("Removed " + p.getName());
                                                if(Bukkit.getOnlinePlayers().contains(p)){
                                                    Player player1 = Bukkit.getPlayer(p.getUniqueId());
                                                    player1.setPlayerListName(player1.getName());
                                                }
                                                return true;
                                            }
                                        }
                                    }
                                }
                              return false;
                            } else if (args[0].toLowerCase().equals("add")) {

                                if (!args[1].isEmpty()) {
                                    double d = Hardcore24.plugin.getConfig().getDouble("hardcore-config.death-ban-time");

                                    long time = (long) d * 60 * 60 * 20;
                                    LocalDateTime dateTime = LocalDateTime.now();
                                    for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
                                        if (args[1].contains(p.getName())) {
                                            Hardcore24.map.put(p.getUniqueId(), dateTime);
                                            player.sendMessage("Added " + p.getName() + " to the temporarily banned players list");
                                            Bukkit.getScheduler().runTaskLater(Hardcore24.plugin, () -> {
                                                if (Hardcore24.map.containsKey(p.getUniqueId())) {
                                                    Hardcore24.map.remove(p.getUniqueId(), dateTime);
                                                }

                                            }, time);
                                            return true;
                                        }
                                    }

                                } else {
                                    return false;
                                }

                            } else if (args[0].toLowerCase().contains("log")) {
                                for (@NotNull OfflinePlayer p : Bukkit.getOfflinePlayers()) {
                                    if (args[1].contains(p.getName())) {
                                        if (PlayerDeath.PlayerLog.containsKey(p.getUniqueId())) {

                                            player.sendMessage(PlayerDeath.PlayerLog.get(p.getUniqueId()).toString());
                                            return true;

                                        } else {
                                            player.sendMessage(ChatColor.RED + "No recent logs for player '" + p.getName() + "'");
                                            return true;
                                        }
                                    } else if (args[1].toLowerCase().contains("blood") && args[1].toLowerCase().contains("moon")) {
                                        if (Hardcore24.LastBloodMoon != null) {
                                            player.sendMessage(Hardcore24.LastBloodMoon);
                                            return true;
                                        } else {
                                            player.sendMessage(ChatColor.RED + "There have been no recent Blood Moons...");
                                            return true;
                                        }
                                    }

                                }
                                player.sendMessage(ChatColor.RED + "Player '" + args[1] + "' not found...");
                                return true;


                            } else if (args[0].toLowerCase().contains("ping")) {
                                for (Player p : Bukkit.getOnlinePlayers()) {
                                    if (args[1].contains(p.getName())) {

                                        player.sendMessage(ChatColor.BLUE + "The current ping of Player '" + p.getName() + "' is " + p.getPing() + "ms");
                                        return true;
                                    }

                                }
                                player.sendMessage(ChatColor.RED + "Player '" + args[1] + "' not found...");
                                return true;


                            }else {
                                return false;
                            }
                        }
                        return false;

                    } else {
                        return false;
                    }

                } catch (NullPointerException exception) {


                }

                return true;
            }
            player.sendMessage(ChatColor.RED + "You don't have permission to use that command");
            return true;
        }
        return true;
    }
}

