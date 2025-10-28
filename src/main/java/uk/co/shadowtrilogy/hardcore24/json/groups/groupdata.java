package uk.co.shadowtrilogy.hardcore24.json.groups;

import java.util.Set;

public class groupdata {
   public String group_name;
   public json_location respawn_location;
   public Set<String> worlds;
   public groupdata(String name, json_location respawn, Set<String> worlds_){
      worlds = worlds_;
      group_name = name;
      respawn_location = respawn;

   }
}
