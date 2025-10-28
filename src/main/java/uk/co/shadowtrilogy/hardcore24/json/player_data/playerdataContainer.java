package uk.co.shadowtrilogy.hardcore24.json.player_data;

import uk.co.shadowtrilogy.hardcore24.PlayerDeathData;

import java.util.HashMap;
import java.util.UUID;

public class playerdataContainer {
    public HashMap<UUID, PlayerDeathData> players;

    public playerdataContainer(HashMap<UUID, PlayerDeathData> p){
        players = p;
    }
}
