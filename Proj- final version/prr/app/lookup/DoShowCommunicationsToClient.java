package prr.app.lookup;

import prr.app.exception.UnknownClientKeyException;
import prr.core.Network;
import prr.core.client.Client;
import prr.core.communication.Communication;
import prr.core.exception.CoreUnknownClientKeyException;
import prr.core.terminal.Terminal;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.ArrayList;
import java.util.List;
//FIXME add more imports if needed

/**
 * Show communications to a client.
 */
class DoShowCommunicationsToClient extends Command<Network> {

  DoShowCommunicationsToClient(Network receiver) {
    super(Label.SHOW_COMMUNICATIONS_TO_CLIENT, receiver);
    //FIXME add command fields
    addStringField("key", Message.clientKey());
  }

  @Override
  protected final void execute() throws CommandException {
    //FIXME implement command
    String key = stringField("key");
    try{
      Client client = _receiver.getClient(key);
      List<Terminal> terminals = new ArrayList<>(client.getTerminals());
      for(Terminal term : terminals){
        List<Communication> communications = new ArrayList<>(term.getReceivedCommunications());
        for(Communication com : communications){
          _display.addLine(com.toString());
        }
      }
    }
    catch(CoreUnknownClientKeyException c){
      throw new UnknownClientKeyException(key);
    }
    _display.display();
  }
}
