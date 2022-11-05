package prr.core.notification;

import prr.core.Observer;
import prr.core.Subject;
import prr.core.client.Client;

import java.io.Serializable;

public class Notification implements Serializable, Observer {

    private static final long serialVersionUID = 202208091753L;
    private NotificationType _type;

    private Client _notifyingClient;

    private boolean _updated;

    private Subject _terminal;

    public Notification(Subject terminal, Client owner){

        _updated = false;

        _terminal = terminal;

        _terminal.register(this);

        _notifyingClient = owner;

    }

    public boolean isForClient(Client client){
        return _notifyingClient.equals(client);
    }

    public boolean isUpdated(){
        return _updated;
    }

    public void update(NotificationType type){
        if(!isUpdated()){
            _type = type;
            _updated = true;
        }
    }

    @Override
    public String toString() {
        return _type + "|" + _terminal;
    }
}
