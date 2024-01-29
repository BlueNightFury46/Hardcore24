package uk.co.shadowtrilogy.hardcore24.EventHandlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.jetbrains.annotations.NotNull;
import uk.co.shadowtrilogy.hardcore24.Hardcore24;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class hardcore implements CommandExecutor {

    public static HashMap<UUID, PermissionAttachment> map4 = new HashMap<>();

    public static Map permissionAttachmentHashMap = map4;


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("§cWarning only players can use this command§c");
            return true;
        }


        Player player = (((Player) commandSender).getPlayer());


        if (command.getName().equalsIgnoreCase("hardcore")) {


            if (player.hasPermission("hardcore.commands")) {
                try {
                    if (args.length > 0) {

                        if(args.length > 1) {
                            //Remove tree
                            if (args[0].toLowerCase().equals("remove")) {

                                if (!args[1].isEmpty()) {
                                    for (Player p : Bukkit.getOnlinePlayers()) {
                                        if (args[1].contains(p.getName())) {
                                            Hardcore24.map.remove(p.getUniqueId());
                                            player.sendMessage("Removed " + p.getName());
                                            return true;
                                        }
                                    }

                                }

                            } else if (args[0].toLowerCase().equals("add")) {

                                if (!args[1].isEmpty()) {
                                    for (Player p : Bukkit.getOnlinePlayers()) {
                                        if (args[1].contains(p.getName())) {
                                            Hardcore24.map.put(p.getUniqueId(), true);
                                            player.sendMessage("Added " + p.getName());
                                            return true;
                                        }
                                    }

                                } else {
                                    return false;
                                }

                            } else if (args[0].toLowerCase().contains("perm")) {


                                //permission one


                                for (Player p2 : Bukkit.getOnlinePlayers()) {
                                    if (args[1].contains(p2.getName())) {

                                        if (args[0].toLowerCase().contains("remove")) {
                                            PermissionAttachment permissionAttachment = p2.addAttachment(Hardcore24.plugin);

                                            permissionAttachment.unsetPermission("hardcore.commands");

                                            map4.remove(p2.getUniqueId(), permissionAttachment);

                                            String string = "permissions.players." + p2.getPlayer().getUniqueId();

                                            Hardcore24.configuration.load(Hardcore24.file);
                                            Hardcore24.configuration.set(string, "hardcore.disabled.commands");
                                            Hardcore24.configuration.save(Hardcore24.file);



                                            p2.removeAttachment(permissionAttachment);

                                            player.sendMessage(ChatColor.BLUE + "removed permission hardcore.commands from " + p2.getName());

                                            p2.kickPlayer(ChatColor.LIGHT_PURPLE + "Permissions reset by " + player.getName() + "\nfor assistance show this screen to a moderator or owner\n" + ChatColor.BLUE + "423 Locked");

                                            return true;
                                        } else if (args[0].toLowerCase().contains("add")) {
                                            PermissionAttachment permissionAttachment = p2.addAttachment(Hardcore24.plugin);

                                            String string = "permissions.players." + p2.getPlayer().getUniqueId();

                                            permissionAttachment.setPermission("hardcore.commands", true);

                                            map4.put(p2.getUniqueId(), permissionAttachment);


                                            Hardcore24.configuration.load(Hardcore24.file);
                                            Hardcore24.configuration.set(string, "hardcore.commands");
                                            Hardcore24.configuration.save(Hardcore24.file);


                                            player.sendMessage(ChatColor.BLUE + "added permission hardcore.commands from " + p2.getName());
                                            return true;
                                        }


                                    }
                                }




                            }


                        }else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } catch (NullPointerException exception) {

                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InvalidConfigurationException e) {
                    throw new RuntimeException(e);
                }
            }
            player.sendMessage(ChatColor.RED + "You don't have permission to use that command");
        }


        return true;
    }
}
