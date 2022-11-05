package prr.app.terminals;

import java.util.ArrayList;

import prr.core.Network;
import prr.core.terminal.Terminal;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

import java.util.List;

/**
 * Show all terminals.
 */
class DoShowAllTerminals extends Command<Network> {

  DoShowAllTerminals(Network receiver) {
    super(Label.SHOW_ALL_TERMINALS, receiver);
  }

  @Override
  protected final void execute() throws CommandException {
    //FIXME implement command
    List<Terminal> terminais = new ArrayList<>(_receiver.getAllTerminals());
    for(Terminal terminal : terminais){
      if(!terminal.hasFriends()){
        _display.addLine(terminal.getType() + "|" + terminal.getId() + "|" + terminal.getOwner().getIdentifier()
                + "|" + terminal.getMode() + "|" + Math.round(terminal.getPayments()) + "|" + Math.round(terminal.getDebt()));
      }
      else{
        _display.addLine(terminal.getType() + "|" + terminal.getId() + "|" + terminal.getOwner().getIdentifier()
                + "|" + terminal.getMode() + "|" + Math.round(terminal.getPayments()) + "|" + Math.round(terminal.getDebt()) + "|" + terminal.getFriends());
      }
    }
    _display.display();
  }
}
