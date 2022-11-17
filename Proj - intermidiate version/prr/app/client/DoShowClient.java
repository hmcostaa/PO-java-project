package prr.app.client;

import prr.core.Network;
import prr.app.exception.UnknownClientKeyException;
import prr.core.exception.CoreUnknownClientKeyException;
import prr.core.notification.Notification;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed
import prr.core.client.Client;
import prr.core.terminal.Terminal;

/**
 * Show specific client: also show previous notifications.
 */
class DoShowClient extends Command<Network> {

  DoShowClient(Network receiver) {
    super(Label.SHOW_CLIENT, receiver);
    //FIXME add command fields
    addStringField("key", Message.key());
  }
  
  @Override
  protected final void execute() throws CommandException {
    //FIXME implement command
    String key = stringField("key");
    try{
      Client client = _receiver.getClient(key);
      _display.addLine("CLIENT|" + client.getIdentifier() + "|" + client.getName() + "|"
      + client.getTaxNumber() + "|" + client.getType() + "|" + client.getNotifications() + "|"
      + client.activeTerminals() + "|" + client.getPayments() + "|" + client.getDebts());
      for(Terminal term : client.getTerminals()){
        for(Notification notification : term.getNotifiers()){
          _display.addLine(notification.getNotificationType() + "|" + notification.getNotifyingTerminal());
        }
        term.getNotifiers().clear();
      }
    }
    catch(CoreUnknownClientKeyException c){
      throw new UnknownClientKeyException(key);
    }
    _display.display();
  }
}
