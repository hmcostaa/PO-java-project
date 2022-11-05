package prr.app.terminal;

import prr.app.exception.UnknownTerminalKeyException;
import prr.core.Network;
import prr.core.exception.CoreUnknownTerminalKeyException;
import prr.core.exception.CoreDestinationOffException;
import prr.core.exception.CoreDestinationSilentException;
import prr.core.exception.CoreDestinationBusyException;
import prr.core.exception.CoreUnsupportedAtOriginException;
import prr.core.exception.CoreUnsupportedAtDestinationException;
import prr.core.terminal.Terminal;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Command for starting communication.
 */
class DoStartInteractiveCommunication extends TerminalCommand{

  DoStartInteractiveCommunication(Network context, Terminal terminal) {
    super(Label.START_INTERACTIVE_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
    addStringField("key", Message.terminalKey());
    addOptionField("type", Message.commType(), "VIDEO", "VOICE");
  }
  
  @Override
  protected final void execute() throws CommandException {
    //FIXME implement command
    String key = stringField("key");
    String type = stringField("type");
    try{
      _network.startInteractiveCommunication(_receiver, key, type);
    }
    catch(CoreUnknownTerminalKeyException c){
      throw new UnknownTerminalKeyException(key);
    }
    catch(CoreDestinationOffException u){
      _display.popup(Message.destinationIsOff(key));
    }
    catch(CoreDestinationSilentException o){
      _display.popup(Message.destinationIsSilent(key));
    }
    catch(CoreDestinationBusyException e){
      _display.popup(Message.destinationIsBusy(key));
    }
    catch(CoreUnsupportedAtOriginException s){
      _display.popup(Message.unsupportedAtOrigin(key, type));
    }
    catch(CoreUnsupportedAtDestinationException d){
      _display.popup(Message.unsupportedAtDestination(key, type));
    }
  }
}
