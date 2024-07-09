package uk.co.shadowtrilogy.hardcore24.EventHandlers;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import uk.co.shadowtrilogy.hardcore24.Hardcore24;

public class hardcore24Version implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(commandSender instanceof Player)) {
            if (command.getName().equalsIgnoreCase("hardcore24-version")) {
                if((commandSender instanceof ConsoleCommandSender)){

                    commandSender.sendMessage(ChatColor.BLUE + "Hardcore24 is currently running on version " + Hardcore24.plugin.getDescription().getVersion());
                    return true;


                }
                commandSender.sendMessage("§cCommand Failed! Only Players or Console can access this command§c");
                return true;


            }


            commandSender.sendMessage("§cWarning only players can use this command§c");
            return true;





        } else if((commandSender instanceof Player)){

            Player player = (((Player) commandSender).getPlayer());


            if (command.getName().equalsIgnoreCase("hardcore24-version")) {


                if (player.isOp() == true || player.hasPermission("hardcore.commands.version")) {

                    player.sendMessage(ChatColor.BLUE + "Hardcore24 is currently running on version " + Hardcore24.plugin.getDescription().getVersion());
                    Hardcore24.plugin.getLogger().info(ChatColor.RED + "Player " + player.getName() + " attempted to run command /" + command.getName().toLowerCase());
                    return true;

                }
                player.sendMessage("You don't have permission... Foolish player... Why try...");
                return true;
            }

        }

        return true;
    }
}
