package prr.app.client;

import prr.core.Network;
import prr.app.exception.UnknownClientKeyException;
import prr.core.client.Client;
import prr.core.exception.CoreUnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Show the payments and debts of a client.
 */
class DoShowClientPaymentsAndDebts extends Command<Network> {

  DoShowClientPaymentsAndDebts(Network receiver) {
    super(Label.SHOW_CLIENT_BALANCE, receiver);
    //FIXME add command fields
    addStringField("key", Message.key());
  }
  
  @Override
  protected final void execute() throws CommandException {
    //FIXME implement command
    String key = stringField("key");
    try{
      Client client = _receiver.getClient(key);
      _display.popup(Message.clientPaymentsAndDebts(key, client.getPayments(), client.getDebts()));
    }
    catch(CoreUnknownClientKeyException c){
      throw new UnknownClientKeyException(key);
    }
  }
}
