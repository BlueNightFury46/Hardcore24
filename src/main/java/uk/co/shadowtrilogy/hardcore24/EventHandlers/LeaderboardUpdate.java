package uk.co.shadowtrilogy.hardcore24.EventHandlers;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.TextDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import uk.co.shadowtrilogy.hardcore24.Hardcore24;

import java.util.ArrayList;
import java.util.UUID;

public class LeaderboardUpdate implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void event(PlayerPostRespawnEvent event) {



        for(String s : Hardcore24.config.getConfigurationSection("leaderboards").getKeys(false)){

            if(!s.equalsIgnoreCase("players")){

                int length = 0;
                for (String string : Hardcore24.config.getConfigurationSection("leaderboards.players.deaths").getKeys(false)) {

                    length++;

                }

                if (length >= 10) {

                    int l = 0;
                    ArrayList<UUID> list = new ArrayList<>();
                    ArrayList<Integer> placement = new ArrayList<>();
                    ArrayList<String> placement2 = new ArrayList<>();
                    while (l <= 10) {

                        for (String string : Hardcore24.config.getConfigurationSection("leaderboards.players.deaths").getKeys(false)) {

                            //Player player1 = Bukkit.getPlayer(string);
                            list.add(UUID.fromString(string));
                            placement.add(Hardcore24.config.getInt("leaderboards.players.deaths." + string + ".score"));
                            placement2.add(Hardcore24.config.getString("leaderboards.players.deaths." + string + ".name"));
                            System.out.println(placement);
                        }
                        try {
                            int maximum = (int) placement.toArray()[0];
                            int i = 0;
                            while (i < placement.size()) {
                                if (placement.get(i) > maximum) {
                                    maximum = placement.get(i);
                                    //System.out.println(maximum);
                                }
                                i++;
                            }
                            put(placement.get(i), placement2.get(i), Hardcore24.config.get("leaderboards." + s + ".entityID"), Hardcore24.config.getString("leaderboards.") + s + ".world");
                            placement2.remove(i);
                            placement.remove(i);
                        } catch (NullPointerException e){
                            System.out.println("ERROR");
                        }


                    }
                }
                else {

                    int o = 0;
                    while(o <= length) {

                        if(o == length){
                            break;
                        }
                        ArrayList<UUID> list = new ArrayList<>();
                        ArrayList<Integer> placement = new ArrayList<>();
                        ArrayList<String> placement2 = new ArrayList<>();
                        for (String string : Hardcore24.config.getConfigurationSection("leaderboards.players.deaths").getKeys(false)) {

                            //Player player1 = Bukkit.getPlayer(string);
                            list.add(UUID.fromString(string));
                            placement.add(Hardcore24.config.getInt("leaderboards.players.deaths." + string + ".score"));
                            placement2.add(Hardcore24.config.getString("leaderboards.players.deaths." + string + ".name"));
                        }
                        int maximum = (int) placement.toArray()[0];
                        int i = 0;
                        while (i < placement.size()) {
                            if (placement.get(i) > maximum) {
                                maximum = placement.get(i);
                                //System.out.println(maximum);
                            }
                            i++;
                        }
                        System.out.println(placement2.get(i - 1) + placement.get(i - 1));
                        o++;
                    }


            }

        }

        }
    }

    public void put(int i, String s, Object uuid, String world){


        System.out.println("WORKING");
        World world1 = Bukkit.getWorld(world);

        for(Entity entity : world1.getEntities()){

          if(entity.getType().equals(EntityType.TEXT_DISPLAY)){
              int entityId  = entity.getEntityId();
              int ID = (int) uuid;
              if(entityId == ID){
                  TextDisplay textDisplay = (TextDisplay) entity;
                  textDisplay.setText(textDisplay.getText() + "\n" + s + ": " + i);


              }


          }
        }

    }
}
