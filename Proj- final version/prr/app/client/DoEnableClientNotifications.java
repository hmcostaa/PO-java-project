package prr.app.client;

import prr.core.Network;
import prr.app.exception.UnknownClientKeyException;
import prr.core.exception.CoreUnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

import prr.core.client.Client;

/**
 * Enable client notifications.
 */
class DoEnableClientNotifications extends Command<Network> {

  DoEnableClientNotifications(Network receiver) {
    super(Label.ENABLE_CLIENT_NOTIFICATIONS, receiver);
    //FIXME add command fields
    addStringField("key", Message.key());
  }
  
  @Override
  protected final void execute() throws CommandException {
    //FIXME implement command
    String key = stringField("key");
    try{
      Client client = _receiver.getClient(key);
      if("YES".equals(client.getNotifications())){
        _display.popup(Message.clientNotificationsAlreadyEnabled());
      }
      else{
        client.activateNotifications();
      }
    }
    catch(CoreUnknownClientKeyException c){
      throw new UnknownClientKeyException(key);
    }
  }
}
