package prr.app.terminal;

import prr.core.Network;
import prr.core.terminal.Terminal;
import prr.core.terminal.TerminalMode;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

import prr.core.client.Client;

/**
 * Command for ending communication.
 */
class DoEndInteractiveCommunication extends TerminalCommand {

  DoEndInteractiveCommunication(Network context, Terminal terminal) {
    super(Label.END_INTERACTIVE_COMMUNICATION, context, terminal, receiver -> receiver.canEndCurrentCommunication());
    addIntegerField("duration", Message.duration());
  }
  
  @Override
  protected final void execute() throws CommandException {
    //FIXME implement command
    int duration = integerField("duration");
    Client owner = _receiver.getOwner();
    double cost = _network.endInteractiveCommunication(_receiver, duration);
    owner.turnNormal();
    owner.turnGold();
    owner.turnPlatinum();
    if(_receiver.getMode().equals(TerminalMode.IDLE)){
      _receiver.notifyObserver();
    }
    _display.popup(Message.communicationCost(Math.round(cost)));
  }
}
