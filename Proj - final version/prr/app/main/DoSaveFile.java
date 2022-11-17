package prr.app.main;

import prr.core.NetworkManager;
import prr.core.exception.MissingFileAssociationException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.io.IOException;
//FIXME add more imports if needed

/**
 * Command to save a file.
 */
class DoSaveFile extends Command<NetworkManager> {

  DoSaveFile(NetworkManager receiver) {
    super(Label.SAVE_FILE, receiver);
  }
  
  @Override
  protected final void execute() throws CommandException {
    //FIXME implement command and create a local Form
    if (!_receiver.hasFilename()) {
      Form form = new Form();
      form.addStringField("filename", Message.newSaveAs());
      form.parse();
      try{
        _receiver.saveAs(form.stringField("filename"));
      }
      catch (MissingFileAssociationException | IOException e){
        e.printStackTrace();
      }
      return;
    }
    try{
      _receiver.save();
    }
    catch(MissingFileAssociationException | IOException c){
      c.printStackTrace();
    }
  }
}
