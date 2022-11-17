package prr.app.terminals;

import prr.core.Network;
import prr.app.exception.DuplicateTerminalKeyException;
import prr.app.exception.InvalidTerminalKeyException;
import prr.app.exception.UnknownClientKeyException;
import prr.core.exception.CoreDuplicateTerminalException;
import prr.core.exception.CoreInvalidTerminalKeyException;
import prr.core.exception.CoreUnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Register terminal.
 */
class DoRegisterTerminal extends Command<Network> {

  DoRegisterTerminal(Network receiver) {
    super(Label.REGISTER_TERMINAL, receiver);
    //FIXME add command fields
    addStringField("id", Message.terminalKey());
    addOptionField("type", Message.terminalType(), "BASIC", "FANCY");
    addStringField("idclient", Message.clientKey());
  }

  @Override
  protected final void execute() throws CommandException {
    //FIXME implement command
    String id = stringField("id");
    String type = stringField("type");
    String idClient = stringField("idclient");
    try{
      _receiver.registerTerminal(type, id, idClient);
    }
    catch(CoreDuplicateTerminalException c){
      throw new DuplicateTerminalKeyException(id);
    }
    catch(CoreInvalidTerminalKeyException e){
      throw new InvalidTerminalKeyException(id);
    }
    catch(CoreUnknownClientKeyException u){
      throw new UnknownClientKeyException(idClient);
    }
  }
}
