package prr.core.terminal;

import java.io.Serializable;
import java.util.*;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

import prr.core.client.Client;
import prr.core.communication.Communication;
import prr.core.communication.InteractiveCommunication;
import prr.core.notification.Notification;

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable /* FIXME maybe addd more interfaces */{

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;
  
  // FIXME define attributes
  // FIXME define contructor(s)
  // FIXME define methods

  private String _id;
  private TerminalMode _mode;
  private double _payments = 0;
  private double _debt = 0;
  private Client _owner;
  private InteractiveCommunication _onGoingCommunication;

  private Map<String, Terminal> _friends = new TreeMap<>();

  private List<Notification> _toNotify = new ArrayList<>();

  private Map<Integer, Communication> _madeCommunications = new TreeMap<>();

  private Map<Integer, Communication> _receivedCommunications = new TreeMap<>();

  /** Terminal Constructor. */
  public Terminal(String id, Client owner){
    _id = id;
    _owner = owner;
    _mode = TerminalMode.IDLE;
  }

  public TerminalMode getMode(){
    return _mode;
  }

  public String getId(){
    return _id;
  }

  public Client getOwner(){
    return _owner;
  }

  public double getPayments(){

    return _payments;
  }

  public double getDebt(){

    return _debt;
  }

  /** return True if the Terminal has at least one friend. */
  public boolean hasFriends(){
    return _friends.size() != 0;
  }

  /** returns a String containing all Terminal friends separated by a comma. */
  public StringBuffer getFriends(){
    StringBuffer friends = new StringBuffer();
    List<Terminal> list = new ArrayList<>(_friends.values());
    Iterator<Terminal> iter = list.iterator();
    while(iter.hasNext()){
      Terminal t = iter.next();
      friends.append(t.getId() + ",");
    }
    friends.deleteCharAt(friends.length() - 1);
    return friends;
  }

  public void addFriend(Terminal friend){
    _friends.put(friend.getId(), friend);
  }

  /** The classes BasicTerminal and FancyTerminal implement this method. */
  public abstract String getType();

  public boolean setOnSilent(){
    if(_mode == TerminalMode.SILENCE || _mode == TerminalMode.BUSY){
      _mode = TerminalMode.SILENCE;
      return true;
    }
    return false;
  }

  public boolean turnOff(){
    if(_mode == TerminalMode.SILENCE || _mode == TerminalMode.IDLE){
      _mode = TerminalMode.OFF;
      return true;
    }
    return false;
  }

  public boolean setOnIdle(){
    if(_mode == TerminalMode.OFF || _mode == TerminalMode.SILENCE || _mode == TerminalMode.BUSY){
      _mode = TerminalMode.IDLE;
      return true;
    }
    return false;
  }

  public List<Notification> getNotifiers(){
    return _toNotify;
  }

  public boolean isUsed(){
    if(_receivedCommunications.size() == 0 && _madeCommunications.size() == 0){
      return false;
    }
    return true;
  }

  public boolean onGoingCommunication(){
    return _onGoingCommunication != null;
  }
  
  /**
   * Checks if this terminal can end the current interactive communication.
   *
   * @return true if this terminal is busy (i.e., it has an active interactive communication) and
   *          it was the originator of this communication.
   **/
  public boolean canEndCurrentCommunication() {
    // FIXME add implementation code
    return false;
  }
  
  /**
   * Checks if this terminal can start a new communication.
   *
   * @return true if this terminal is neither off neither busy, false otherwise.
   **/
  public boolean canStartCommunication() {
    // FIXME add implementation code
    if(_mode == TerminalMode.OFF || _mode == TerminalMode.BUSY){
      return false;
    }
    return true;
  }
}
