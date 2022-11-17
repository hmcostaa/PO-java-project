package prr.app.client;

import prr.core.Network;
import prr.app.exception.UnknownClientKeyException;
import prr.core.Observer;
import prr.core.exception.CoreUnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed
import prr.core.client.Client;
import prr.core.terminal.Terminal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
      + client.numberTerminals() + "|" + client.getPayments() + "|" + client.getDebts());
      List<Terminal> terminals = new ArrayList<>(_receiver.getAllTerminals());
      for(Terminal term : terminals){
        List<Observer> notifications = new ArrayList<>(term.getObservers());
        Iterator<Observer> iter = notifications.iterator();
        while(iter.hasNext()){
          Observer notification = iter.next();
          if(notification.isUpdated() && notification.isForClient(client)){
            _display.addLine(notification);
            term.unregister(notification);
          }
        }
      }
    }
    catch(CoreUnknownClientKeyException c){
      throw new UnknownClientKeyException(key);
    }
    _display.display();
  }
}
