package prr.app.lookup;

import prr.core.Network;
import prr.core.communication.Communication;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.util.ArrayList;
import java.util.List;
//FIXME more imports if needed

/**
 * Command for showing all communications.
 */
class DoShowAllCommunications extends Command<Network> {

  DoShowAllCommunications(Network receiver) {
    super(Label.SHOW_ALL_COMMUNICATIONS, receiver);
  }

  @Override
  protected final void execute() throws CommandException {
    //FIXME implement command
    List<Communication> communications = new ArrayList<>(_receiver.getAllCommunications());
    for(Communication com : communications){
      _display.addLine(com.toString());
    }
    _display.display();
  }
}
