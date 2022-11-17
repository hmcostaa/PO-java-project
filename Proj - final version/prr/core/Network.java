package prr.core;

import java.io.Serializable;
import java.io.IOException;

import prr.core.client.Client;
import prr.core.communication.Communication;
import prr.core.communication.InteractiveCommunication;
import prr.core.exception.*;
import prr.core.notification.Notification;
import prr.core.terminal.BasicTerminal;
import prr.core.terminal.FancyTerminal;
import prr.core.terminal.Terminal;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

import java.util.*;

/**
 * Class Network implements a network.
 */
public class Network implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;
  
  // FIXME define attributes
  // FIXME define contructor(s)
  // FIXME define methods
  /**
   * Stores the next id of each Communication.
   */
  private int _nextCommunicationId = 1;
  /**
   * Stores the Network's clients by their id.
   */
  private Map<String, Client> _clients = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
  /**
   * Stores the Network's terminals by their id.
   */
  private Map<String, Terminal> _terminals = new TreeMap<>();
  /**
   * Stores the Network's communications by their id.
   */
  private Map<Integer, Communication> _communications = new TreeMap<>();

  /**
   * Register a client in the Network.
   *
   * @param key  The unique key that represents the client.
   * @param name The name of the client.
   * @param taxNumber  The tax number of the client.
   * @throws CoreDuplicateKeyException if a client already stored in the Network has that same key.
   */
  public void registerClient(String key, String name, int taxNumber) throws CoreDuplicateKeyException {
    if(_clients.containsKey(key)){
      throw new CoreDuplicateKeyException();
    }
    Client novo = new Client(key, name, taxNumber);
    _clients.put(key, novo);
  }

  /**
   * Get all clients stored in the Network.
   *
   * @return a Collection of the clients.
   */
  public Collection<Client> getAllClients(){
    return _clients.values();
  }

  /**
   * Gets the client associated with the given key.
   *
   * @param key  The unique key that represents the client.
   * @return  the client associated with the given key.
   * @throws CoreUnknownClientKeyException if there's no client associated with the given key.
   */
  public Client getClient(String key) throws CoreUnknownClientKeyException {
    if(!_clients.containsKey(key)){
      throw new CoreUnknownClientKeyException();
    }
    return _clients.get(key);
  }

  /**
   * Register a terminal in the Network.
   *
   * @param type can be either BASIC or FANCY.
   * @param key  The unique key that represents the terminal.
   * @param keyClient  The unique key of the client that we want to associate our terminal with.
   * @return The terminal created.
   * @throws CoreDuplicateTerminalException if a terminal already stored in the Network has that same key.
   * @throws CoreInvalidTerminalKeyException if the given key doesn't have 6 digits.
   * @throws NumberFormatException if the given key isn't an Integer.
   * @throws CoreUnknownClientKeyException if there's no client associated with the given key.
   */
  public Terminal registerTerminal(String type, String key, String keyClient) throws CoreDuplicateTerminalException,
          CoreInvalidTerminalKeyException, CoreUnknownClientKeyException{
    if(_terminals.containsKey(key)){
      throw new CoreDuplicateTerminalException();
    }
    try{
      Integer.parseInt(key);
    }
    catch (NumberFormatException c){
      throw new CoreInvalidTerminalKeyException();
    }
    if(key.length() != 6){
      throw new CoreInvalidTerminalKeyException();
    }
    Client client = getClient(keyClient);
    Terminal terminal = null;
    if("BASIC".equals(type)){
      terminal = new BasicTerminal(key, client);
      client.addTerminal(terminal);
      _terminals.put(key, terminal);
    }
    if("FANCY".equals(type)){
      terminal = new FancyTerminal(key, client);
      client.addTerminal(terminal);
      _terminals.put(key, terminal);
    }
    return terminal;
  }

  /**
   * Add a friend to the terminal asscociated with the given key.
   *
   * @param key  The unique key that represents the terminal.
   * @param friend The unique key that represents the friend terminal.
   * @throws CoreUnknownTerminalKeyException if either the terminal's or the friend's terminal key isn't associated
   * with any terminal.
   */
  public void addFriend(String key, String friend) throws CoreUnknownTerminalKeyException,
          CoreUnknownTerminalFriendKeyException{
    if(!_terminals.containsKey(key)){
      throw new CoreUnknownTerminalKeyException();
    }
    if(!_terminals.containsKey(friend)){
      throw new CoreUnknownTerminalFriendKeyException();
    }
    if(!key.equals(friend)){
      _terminals.get(key).addFriend(_terminals.get(friend));
    }
  }

  public void removeFriend(String key, String friend) throws CoreUnknownTerminalKeyException,
          CoreUnknownTerminalFriendKeyException{
    if(!_terminals.containsKey(key)){
      throw new CoreUnknownTerminalKeyException();
    }
    if(!_terminals.containsKey(friend)){
      throw new CoreUnknownTerminalFriendKeyException();
    }
    _terminals.get(key).removeFriend(_terminals.get(friend));
  }

  /**
   * get the terminal associated with the given key.
   *
   * @param key  The unique key that represents the terminal.
   * @return the terminal associated with the given key.
   * @throws CoreUnknownTerminalKeyException if the key isn't associated with any terminal in the Network.
   */
  public Terminal getTerminal(String key) throws CoreUnknownTerminalKeyException{
    if(!_terminals.containsKey(key)){
      throw new CoreUnknownTerminalKeyException();
    }
    return _terminals.get(key);
  }

  /**
   * Get all the terminals stored in the Network.
   *
   * @return a Collection of the terminals.
   */
  public Collection<Terminal> getAllTerminals(){
    return _terminals.values();
  }

  /**
   * Increases the Communication id each time.
   *
   * @return the increased Communication id.
   */
  public int getNextCommunicationId(){
    return _nextCommunicationId++;
  }

  public void addCommunication(Communication com){
    _communications.put(com.getId(), com);
  }

  /**
   * Gets all the terminals in the Network that haven't made nor received any communications.
   *
   * @return the unused terminals.
   */
  public List<Terminal> getUnusedTerminals(){
    List<Terminal> terminals = new ArrayList<>(_terminals.values());
    List<Terminal> unused = new ArrayList<>();
    for(Terminal term : terminals){
      if(!term.isUsed()){
        unused.add(term);
      }
    }
    return unused;
  }

  public long getGlobalPayment(){
    long payments = 0;
    for(Client client : getAllClients()){
      payments += client.getPayments();
    }
    return payments;
  }

  public long getGlobalDebts(){
    long debts = 0;
    for(Client client: getAllClients()){
      debts += client.getDebts();
    }
    return debts;
  }

  public Collection<Communication> getAllCommunications(){
    return _communications.values();
  }

  public void sendTextCommunication(Terminal from, String toKey, String message) throws CoreUnknownTerminalKeyException,
          CoreDestinationOffException{
    Terminal to = getTerminal(toKey);
    Client owner = from.getOwner();
    if(to.isOff()){
      if(!to.alreadyHasNotification(owner) && owner.receiveNotifications()){
        new Notification(to, owner);
      }
      throw new CoreDestinationOffException();
    }
    owner.incrementConsecutiveTexts();
    owner.noConsecutiveVideoCalls();
    Communication com = from.makeSms(getNextCommunicationId(), to, message);
    to.accept(com);
    addCommunication(com);
    double cost = com.accept(owner.getTariff());
    from.addDebt(cost);
    com.setFinished(cost);
  }

  public void startInteractiveCommunication(Terminal from, String toKey, String type) throws CoreUnknownTerminalKeyException,
          CoreDestinationOffException, CoreDestinationSilentException, CoreDestinationBusyException,
          CoreUnsupportedAtOriginException, CoreUnsupportedAtDestinationException{
    Terminal to = getTerminal(toKey);
    Client owner = from.getOwner();
    if(toKey.equals(from.getId())){
      throw new CoreDestinationBusyException();
    }
    if(to.isOff()){
      if(!to.alreadyHasNotification(owner) && owner.receiveNotifications()){
        new Notification(to, owner);
      }
      throw new CoreDestinationOffException();
    }
    if(to.isSilent()){
      if(!to.alreadyHasNotification(owner) && owner.receiveNotifications()){
        new Notification(to, owner);
      }
      throw new CoreDestinationSilentException();
    }
    if(to.isBusy()){
      if(!to.alreadyHasNotification(owner) && owner.receiveNotifications()){
        new Notification(to, owner);
      }
      throw new CoreDestinationBusyException();
    }
    if("VOICE".equals(type)){
      owner.noConsecutiveVideoCalls();
      owner.noConsecutiveTexts();
      InteractiveCommunication com = from.makeVoiceCall(getNextCommunicationId(), to);
      to.acceptCall(com);
      addCommunication(com);
    }
    if("VIDEO".equals(type)){
      if(from.isBasic()){
        throw new CoreUnsupportedAtOriginException();
      }
      if(to.isBasic()){
        throw new CoreUnsupportedAtDestinationException();
      }
      owner.incrementConsecutiveVideoCalls();
      owner.noConsecutiveTexts();
      InteractiveCommunication com = from.makeVideoCall(getNextCommunicationId(), to);
      to.acceptCall(com);
      addCommunication(com);
    }
  }

  public double endInteractiveCommunication(Terminal from, int size){
    Client owner = from.getOwner();
    InteractiveCommunication com = from.getOnGoingCommunication();
    Terminal to = com.getToTerminal();
    com.setDuration(size);
    double cost = com.accept(owner.getTariff());
    if(from.isFriend(to)){
      cost = cost * 0.5;
    }
    from.addDebt(cost);
    com.setFinished(cost);
    from.endOnGoingCommunication();
    to.acceptEndCall();
    return cost;
  }
  
  /**
   * Read text input file and create corresponding domain entities.
   * 
   * @param filename name of the text input file
   * @throws UnrecognizedEntryException if some entry is not correct
   * @throws IOException if there is an IO erro while processing the text file
   **/
  void importFile(String filename) throws UnrecognizedEntryException, IOException /* FIXME maybe other exceptions */  {
    //FIXME implement method
    Parser parser = new Parser(this);
    parser.parseFile(filename);
  }
}

