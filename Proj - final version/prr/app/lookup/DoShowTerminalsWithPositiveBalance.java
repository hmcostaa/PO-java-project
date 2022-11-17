package prr.app.lookup;

import prr.core.Network;
import prr.core.terminal.Terminal;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.List;
import java.util.ArrayList;
//FIXME add more imports if needed

/**
 * Show terminals with positive balance.
 */
class DoShowTerminalsWithPositiveBalance extends Command<Network> {

  DoShowTerminalsWithPositiveBalance(Network receiver) {
    super(Label.SHOW_TERMINALS_WITH_POSITIVE_BALANCE, receiver);
  }

  @Override
  protected final void execute() throws CommandException {
    //FIXME implement command
    List<Terminal> terminals = new ArrayList<>(_receiver.getAllTerminals());
    for(Terminal terminal : terminals){
      if(terminal.getBalance() > 0){
        if(!terminal.hasFriends()){
          _display.addLine(terminal.getType() + "|" + terminal.getId() + "|" + terminal.getOwner().getIdentifier()
                  + "|" + terminal.getMode() + "|" + Math.round(terminal.getPayments()) + "|" + Math.round(terminal.getDebt()));
        }
        else{
          _display.addLine(terminal.getType() + "|" + terminal.getId() + "|" + terminal.getOwner().getIdentifier()
                  + "|" + terminal.getMode() + "|" + Math.round(terminal.getPayments()) + "|" + Math.round(terminal.getDebt()) + "|"
                  + terminal.getFriends());
        }
      }
    }
    _display.display();
  }
}
