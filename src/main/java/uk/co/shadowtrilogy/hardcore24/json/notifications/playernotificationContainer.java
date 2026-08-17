package uk.co.shadowtrilogy.hardcore24.json.notifications;

import java.util.HashMap;
import java.util.UUID;

public class playernotificationContainer {
    public HashMap<UUID, String> notifications;

    public playernotificationContainer(HashMap<UUID, String> notificationMap){
        notifications = notificationMap;
    }
}
