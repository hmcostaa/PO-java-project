package prr.app.terminal;

import prr.core.Network;
import prr.core.terminal.Terminal;
import prr.core.terminal.TerminalMode;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Turn on the terminal.
 */
class DoTurnOnTerminal extends TerminalCommand {

  DoTurnOnTerminal(Network context, Terminal terminal) {
    super(Label.POWER_ON, context, terminal);
  }
  
  @Override
  protected final void execute() throws CommandException {
    //FIXME implement command
    if(_receiver.getMode().equals(TerminalMode.IDLE)){
      _display.popup(Message.alreadyOn());
    }
    else{
      if(_receiver.setOnIdle()){
        _receiver.notifyObserver();
      }
    }
  }
}
