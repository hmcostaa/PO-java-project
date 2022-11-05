package prr.app.terminal;

import prr.app.exception.UnknownTerminalKeyException;
import prr.core.Network;
import prr.core.exception.CoreUnknownTerminalFriendKeyException;
import prr.core.exception.CoreUnknownTerminalKeyException;
import prr.core.terminal.Terminal;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Remove friend.
 */
class DoRemoveFriend extends TerminalCommand {

  DoRemoveFriend(Network context, Terminal terminal) {
    super(Label.REMOVE_FRIEND, context, terminal);
    //FIXME add command fields
    addStringField("id", Message.terminalKey());
  }
  
  @Override
  protected final void execute() throws CommandException {
    //FIXME implement command
    String id = stringField("id");
    try{
      _network.removeFriend(_receiver.getId(), id);
    }
    catch(CoreUnknownTerminalKeyException c){
      throw new UnknownTerminalKeyException(_receiver.getId());
    }
    catch(CoreUnknownTerminalFriendKeyException u){
      throw new UnknownTerminalKeyException(id);
    }
  }
}
