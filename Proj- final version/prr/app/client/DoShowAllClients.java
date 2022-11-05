package prr.app.client;

import prr.core.Network;
import prr.core.client.Client;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.ArrayList;
//FIXME add more imports if needed

import java.util.List;

/**
 * Show all clients.
 */
class DoShowAllClients extends Command<Network> {

  DoShowAllClients(Network receiver) {
    super(Label.SHOW_ALL_CLIENTS, receiver);
  }
  
  @Override
  protected final void execute() throws CommandException {
    //FIXME implement command
    List<Client> clients = new ArrayList<>(_receiver.getAllClients());
    for(Client client : clients){
      _display.addLine("CLIENT|" + client.getIdentifier() + "|" + client.getName() + "|"
      + client.getTaxNumber() + "|" + client.getType() + "|" + client.getNotifications() + "|"
      + client.numberTerminals() + "|" + client.getPayments() + "|" + client.getDebts());
    }
    _display.display();
  }
}
