package prr.core.notification;

import prr.core.terminal.Terminal;

import java.io.Serializable;

public class Notification implements Serializable {

    private static final long serialVersionUID = 202208091753L;
    private NotificationType _type;
    private Terminal _notifyingTerminal;

    public Notification(NotificationType type){
        _type = type;
    }

    public NotificationType getNotificationType(){
        return _type;
    }

    public Terminal getNotifyingTerminal(){
        return _notifyingTerminal;
    }
}
