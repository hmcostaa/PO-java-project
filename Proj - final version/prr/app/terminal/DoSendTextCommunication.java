package prr.app.terminal;

import prr.app.exception.UnknownTerminalKeyException;
import prr.core.Network;
import prr.core.exception.CoreDestinationOffException;
import prr.core.exception.CoreUnknownTerminalKeyException;
import prr.core.terminal.Terminal;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

import prr.core.client.Client;


/**
 * Command for sending a text communication.
 */
class DoSendTextCommunication extends TerminalCommand {

  DoSendTextCommunication(Network context, Terminal terminal) {
    super(Label.SEND_TEXT_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
    addStringField("key", Message.terminalKey());
    addStringField("message", Message.textMessage());
  }
  
  @Override
  protected final void execute() throws CommandException {
    //FIXME implement command
    String key = stringField("key");
    String message = stringField("message");
    Client owner = _receiver.getOwner();
    try{
      _network.sendTextCommunication(_receiver, key, message);
      owner.turnNormal();
      owner.turnGold();
      owner.turnPlatinum();
    }
    catch(CoreUnknownTerminalKeyException c){
      throw new UnknownTerminalKeyException(key);
    }
    catch(CoreDestinationOffException i){
      _display.popup(Message.destinationIsOff(key));
    }
  }
} 
