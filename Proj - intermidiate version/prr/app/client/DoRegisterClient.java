package prr.app.client;

import prr.core.Network;
import prr.app.exception.DuplicateClientKeyException;
import prr.core.exception.CoreDuplicateKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Register new client.
 */
class DoRegisterClient extends Command<Network> {

  DoRegisterClient(Network receiver) {
    super(Label.REGISTER_CLIENT, receiver);
    //FIXME add command fields
    addStringField("key", Message.key());
    addStringField("name", Message.name());
    addIntegerField("id", Message.taxId());
  }
  
  @Override
  protected final void execute() throws CommandException {
    //FIXME implement command
    String key = stringField("key");
    String name = stringField("name");
    Integer id = integerField("id");
    try{
      _receiver.registerClient(key, name, id);
    }
    catch(CoreDuplicateKeyException d){
      throw new DuplicateClientKeyException(key);
    }
  }
}
