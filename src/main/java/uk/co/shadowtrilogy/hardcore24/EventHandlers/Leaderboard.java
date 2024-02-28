package uk.co.shadowtrilogy.hardcore24.EventHandlers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;
import uk.co.shadowtrilogy.hardcore24.Hardcore24;

import java.io.IOException;

public class Leaderboard implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("§cWarning only players can use this command§c");
            return true;
        }


        Player player = (((Player) commandSender).getPlayer());


        if (command.getName().equalsIgnoreCase("leaderboard")) {


            if (player.isOp()) {
                try {
                if (args.length >= 3) {


                        World world = Bukkit.getWorld(args[0].toString());
                        Location location = new Location(world, Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]));
                        TextDisplay display = world.spawn(location, TextDisplay.class);
                        try {
                            Hardcore24.config.load(Hardcore24.Leaderboard);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (InvalidConfigurationException e) {
                            throw new RuntimeException(e);
                        }
                        Hardcore24.config.set("leaderboards." + args[0] + args[1] + args[2] + args[3] + ".entityID", display.getEntityId());
                        try {
                            Hardcore24.config.save(Hardcore24.Leaderboard);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        display.setText("HI");
                        display.setViewRange(900);
                        display.setGlowing(true);
                        Transformation transformation = display.getTransformation();
                        transformation.getScale().set(2);
                        display.setTransformation(transformation);
                        player.sendMessage("GENERATED LEADERBOARD AT: world: " + world + ", x: " + args[1] + args[2] + args[3]);
                    }
                } catch (NullPointerException e){}
            }
        }


        return true;
    }
}
