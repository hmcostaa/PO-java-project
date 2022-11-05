package prr.app.lookup;

import prr.core.Network;
import prr.core.client.Client;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.ArrayList;
import java.util.List;
//FIXME more imports if needed

/**
 * Show clients with positive balance.
 */
class DoShowClientsWithoutDebts extends Command<Network> {

  DoShowClientsWithoutDebts(Network receiver) {
    super(Label.SHOW_CLIENTS_WITHOUT_DEBTS, receiver);
  }

  @Override
  protected final void execute() throws CommandException {
    //FIXME implement command
    List<Client> clients = new ArrayList<>(_receiver.getAllClients());
    for(Client client : clients){
      if(client.getDebts() == 0){
        _display.addLine("CLIENT|" + client.getIdentifier() + "|" + client.getName() + "|"
                + client.getTaxNumber() + "|" + client.getType() + "|" + client.getNotifications() + "|"
                + client.numberTerminals() + "|" + client.getPayments() + "|" + client.getDebts());
      }
    }
    _display.display();
  }
}
