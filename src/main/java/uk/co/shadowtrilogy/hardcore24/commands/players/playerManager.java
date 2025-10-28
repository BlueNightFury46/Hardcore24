package uk.co.shadowtrilogy.hardcore24.commands.players;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import uk.co.shadowtrilogy.hardcore24.Hardcore24;
import uk.co.shadowtrilogy.hardcore24.PlayerDeathData;

import java.time.LocalDateTime;
import java.util.UUID;

public class playerManager implements CommandExecutor {


    final int OPERATION = 0;
    final int PLAYER = 1;
    final int WORLD = 2;

    final String REMOVE = "UNBAN";
    final String ADD = "BAN";
    final String LIST = "LIST";



    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if(!commandSender.hasPermission("hardcore.manage.players")){
            commandSender.sendMessage(ChatColor.RED + "You don't have permission to use this command...");
            return true;
        }

        if(args.length>0){

            if(args[OPERATION].toUpperCase().contains(REMOVE)){
                if(args.length<3){
                    commandSender.sendMessage(ChatColor.RED + "Argument error! More arguments are needed");
                    return true;
                }

                if(!playerExists(args[PLAYER])){
                    commandSender.sendMessage(ChatColor.RED + "Error! Player \"" + args[PLAYER] + "\" not found...");
                    return true;
                }

              /*  if(!worldExists(args[WORLD])){
                    commandSender.sendMessage(ChatColor.RED + "Error! World \"" + args[WORLD] + "\" not found...");
                    return true;
                }*/


                try{
                    UUID player = (getPlayer(args[PLAYER]));
                    if(player==null){
                        throw new NullPointerException();
                    }
                    if(Hardcore24.deadPlayers.containsKey(player)){
                        Hardcore24.deadPlayers.remove(player);
                        commandSender.sendMessage(ChatColor.BLUE + "Successfully unbanned player \"" + ChatColor.LIGHT_PURPLE + args[PLAYER] +  ChatColor.BLUE + "\"  from hardcore...");


                    }
                } catch (NullPointerException ex){
                    commandSender.sendMessage(ChatColor.RED + "Error! Player \"" + args[PLAYER] + "\" was never banned from hardcore...");

                }


               return true;

            }
            if(args[OPERATION].toUpperCase().contains(ADD)){
                if(args.length<3){
                    commandSender.sendMessage(ChatColor.RED + "Argument error! More arguments are needed");
                    return true;
                }

                if(!playerExists(args[PLAYER])){
                    commandSender.sendMessage(ChatColor.RED + "Error! Player \"" + args[PLAYER] + "\" not found...");
                    return true;
                }

                if(!worldExists(args[WORLD])){
                    commandSender.sendMessage(ChatColor.RED + "Error! World \"" + args[WORLD] + "\" not found...");
                    return true;
                }


                try{
                    UUID player = (getPlayer(args[PLAYER]));

                    if(player==null){
                        throw new NullPointerException();
                    }
                        Hardcore24.deadPlayers.put(player, new PlayerDeathData(args[WORLD], LocalDateTime.now()));
                    commandSender.sendMessage(ChatColor.BLUE + "Successfully banned player \"" + ChatColor.LIGHT_PURPLE + args[PLAYER] +  ChatColor.BLUE + "\"  from hardcore world \"" + ChatColor.GREEN + args[WORLD] + ChatColor.BLUE + "\"");


                } catch (NullPointerException ex){
                    commandSender.sendMessage(ChatColor.RED + "Error! Player \"" + args[PLAYER] + "\" not found...");

                }

                return true;



            }
            if(args[OPERATION].toUpperCase().contains(LIST)){
                System.out.println(Hardcore24.deadPlayers);
                //TODO fix

            }



        }

        return true;




    }


    boolean playerExists(String p){

        for(OfflinePlayer player : Hardcore24.plugin.getServer().getOfflinePlayers()){
            if(p.toLowerCase().equalsIgnoreCase((player.getName().toLowerCase()))){
                return true;
            }
        }

        return false;

    }

    UUID getPlayer(String p){

        for(OfflinePlayer player : Hardcore24.plugin.getServer().getOfflinePlayers()){
            if(p.toLowerCase().equalsIgnoreCase((player.getName().toLowerCase()))){
                return player.getUniqueId();
            }
        }

        return null;

    }



    boolean worldExists(String p){

        for(World w : Hardcore24.plugin.getServer().getWorlds()){
            if(p.toLowerCase().equalsIgnoreCase((w.getName().toLowerCase()))){
                return true;
            }



    }
        return false;

    }
}
