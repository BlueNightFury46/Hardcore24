package uk.co.shadowtrilogy.hardcore24.commands.reload;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import uk.co.shadowtrilogy.hardcore24.Hardcore24;

public class reload implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!commandSender.hasPermission("hardcore.manage.reload")){
            commandSender.sendMessage(ChatColor.RED + "You don't have permission to use this command...");
            return true;
        }

        Hardcore24.reloadPlugin();
        commandSender.sendMessage(ChatColor.ITALIC + "" +ChatColor.BLUE + "Successfully reloaded" + ChatColor.RED + " Hardcore24 " + ChatColor.BLUE + "version " + ChatColor.GOLD + Hardcore24.plugin.getPluginMeta().getVersion() + ChatColor.BLUE + " by " + ChatColor.LIGHT_PURPLE + "BlueNightFury46");



        return true;
    }
}
