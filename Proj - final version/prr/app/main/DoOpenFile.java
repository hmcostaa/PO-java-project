package prr.app.main;

import prr.app.exception.FileOpenFailedException;
import prr.core.NetworkManager;
import prr.core.exception.UnavailableFileException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//Add more imports if needed

/**
 * Command to open a file.
 */
class DoOpenFile extends Command<NetworkManager> {

  DoOpenFile(NetworkManager receiver) {
    super(Label.OPEN_FILE, receiver);
    //FIXME add command fields
    addStringField("file", Message.openFile());
  }
  
  @Override
  protected final void execute() throws CommandException {

      try {
      //FIXME implement command
        String filename = stringField("file");
        _receiver.load(filename);
      } catch (UnavailableFileException e) {
        throw new FileOpenFailedException(e);
      }
  }
}
