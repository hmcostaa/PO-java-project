package prr.core;

import prr.core.notification.NotificationType;
import prr.core.client.Client;

public interface Observer {

    public void update(NotificationType stateChange);

    public boolean isUpdated();

    public boolean isForClient(Client client);
}
