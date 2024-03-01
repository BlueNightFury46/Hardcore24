package uk.co.shadowtrilogy.hardcore24.EventHandlers;

import io.papermc.paper.event.player.AsyncChatCommandDecorateEvent;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.util.Transformation;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;
import uk.co.shadowtrilogy.hardcore24.Hardcore24;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
                if (args.length >= 4) {


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
                        Hardcore24.config.set("leaderboards." + args[4] + ".entityID", display.getEntityId());
                    Hardcore24.config.set("leaderboards." + args[4] + ".world", display.getWorld().getName());
                        try {
                            Hardcore24.config.save(Hardcore24.Leaderboard);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        ArrayList<UUID> list = new ArrayList<>();
                        ArrayList<Integer> placement = new ArrayList<>();
                        ArrayList<String> placement2 = new ArrayList<>();
                        for(String string : Hardcore24.config.getConfigurationSection("leaderboards.players.deaths").getKeys(false)){

                            //Player player1 = Bukkit.getPlayer(string);
                            list.add(UUID.fromString(string));
                            placement.add(Hardcore24.config.getInt("leaderboards.players.deaths." + string + ".score"));
                            placement2.add(Hardcore24.config.getString("leaderboards.players.deaths." + string + ".name"));
                        }
                        int maximum = (int) placement.toArray()[0];
                         int i = 0;
                        while(i < placement.size()){
                            if(placement.get(i) > maximum){
                                maximum = placement.get(i);
                                //System.out.println(maximum);
                            }
                            i++;
                        }
                    System.out.println(placement2.get(i - 1) + placement.get(i - 1));
                        String text = ChatColor.RED + "Hardcore\n" + net.md_5.bungee.api.ChatColor.RESET + "\n" + placement2.get(i - 1) + ": " + placement.get(i - 1);
                    Hardcore24.config.set("leaderboards." + args[0] + args[1] + args[2] + args[3] + ".Text", text);
                   // display.setText(ChatColor.RED + "Hardcore\n" + net.md_5.bungee.api.ChatColor.RESET);


                    Hardcore24.config.load(Hardcore24.Leaderboard);
                    Hardcore24.config.set("leaderboards." + args[0] + args[1] + args[2] + args[3] + ".Text", text);
                    Hardcore24.config.save(Hardcore24.Leaderboard);

                        display.setText(text);
                        display.setViewRange(900);
                        display.setGlowing(true);
                        display.setBillboard(Display.Billboard.CENTER);
                        display.setInvulnerable(true);
                        display.setSeeThrough(true);
                        Transformation transformation = display.getTransformation();
                        transformation.getScale().set(1.5);
                        display.setTransformation(transformation);
                        player.sendMessage("GENERATED LEADERBOARD AT: world: " + world + ", x: " + args[1] + args[2] + args[3]);
                    }
                } catch (NullPointerException e){} catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InvalidConfigurationException e) {
                    throw new RuntimeException(e);
                }
            }
        }


        return true;
    }
}
