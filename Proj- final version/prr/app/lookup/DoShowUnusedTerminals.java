package prr.app.lookup;

import prr.core.Network;
import prr.core.terminal.Terminal;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

import java.util.List;
import java.util.ArrayList;

/**
 * Show unused terminals (without communications).
 */
class DoShowUnusedTerminals extends Command<Network> {

  DoShowUnusedTerminals(Network receiver) {
    super(Label.SHOW_UNUSED_TERMINALS, receiver);
  }

  @Override
  protected final void execute() throws CommandException {
    //FIXME implement command
    List<Terminal> terminals = new ArrayList<>(_receiver.getUnusedTerminals());
    for(Terminal terminal : terminals){
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
