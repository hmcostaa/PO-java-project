package prr.app.lookup;

import prr.core.Network;
import prr.core.client.Client;
import prr.core.comparator.DebtsComparator;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//FIXME more imports if needed

/**
 * Show clients with negative balance.
 */
class DoShowClientsWithDebts extends Command<Network> {

  DoShowClientsWithDebts(Network receiver) {
    super(Label.SHOW_CLIENTS_WITH_DEBTS, receiver);
  }

  @Override
  protected final void execute() throws CommandException {
    //FIXME implement command
    List<Client> clients = new ArrayList<>(_receiver.getAllClients());
    List<Client> clientsDebts = new ArrayList<>();
    for(Client client : clients){
      if(client.getDebts() > 0){
        clientsDebts.add(client);
      }
    }
    Collections.sort(clientsDebts, new DebtsComparator());
    for(Client client : clientsDebts){
      _display.addLine("CLIENT|" + client.getIdentifier() + "|" + client.getName() + "|"
              + client.getTaxNumber() + "|" + client.getType() + "|" + client.getNotifications() + "|"
              + client.numberTerminals() + "|" + client.getPayments() + "|" + client.getDebts());
    }
    _display.display();
  }
}
