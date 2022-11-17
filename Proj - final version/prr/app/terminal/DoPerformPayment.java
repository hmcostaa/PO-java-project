package prr.app.terminal;

import prr.core.Network;
import prr.core.client.Client;
import prr.core.communication.Communication;
import prr.core.terminal.Terminal;
import pt.tecnico.uilib.menus.CommandException;
// Add more imports if needed

/**
 * Perform payment.
 */
class DoPerformPayment extends TerminalCommand {

  DoPerformPayment(Network context, Terminal terminal) {
    super(Label.PERFORM_PAYMENT, context, terminal);
    //FIXME add command fields
    addIntegerField("commKey", Message.commKey());
  }
  
  @Override
  protected final void execute() throws CommandException {
    //FIXME implement command
    int commKey = integerField("commKey");
    Client owner = _receiver.getOwner();
    Communication com = _receiver.getCommunication(commKey);
    if(_receiver.hasMadeCommunication(commKey) && !com.isGoing() && !com.isPaid()){
      _receiver.addPayment(com.getCost());
      _receiver.removeDebt(com.getCost());
      com.setPaid();
      owner.turnGold();
    }
    else{
      _display.popup(Message.invalidCommunication());
    }
  }
}
