package prr.core.terminal;

import java.io.Serializable;
import java.util.*;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

import prr.core.Observer;
import prr.core.Subject;
import prr.core.client.Client;
import prr.core.communication.*;
import prr.core.notification.NotificationType;

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable, Subject /* FIXME maybe addd more interfaces */{

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;
  
  // FIXME define attributes
  // FIXME define contructor(s)
  // FIXME define methods

  private String _id;
  private TerminalMode _mode;

  private TerminalMode _wasMode;

  private NotificationType _stateChange;
  private double _payments;
  private double _debt;
  private Client _owner;
  private InteractiveCommunication _onGoingCommunication;

  private Map<String, Terminal> _friends = new TreeMap<>();

  private List<Client> _toNotify = new ArrayList<>();

  private List<Observer> _observers = new ArrayList<Observer>();

  private Map<Integer, Communication> _madeCommunications = new TreeMap<>();

  private Map<Integer, Communication> _receivedCommunications = new TreeMap<>();

  /** Terminal Constructor. */
  public Terminal(String id, Client owner){
    _id = id;
    _owner = owner;
    _mode = TerminalMode.IDLE;
    _payments = 0;
    _debt = 0;
  }

  public TerminalMode getMode(){
    return _mode;
  }

  public void setWasMode(TerminalMode mode){
    _wasMode = mode;
  }

  public TerminalMode getWasMode(){
    return _wasMode;
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

  public double getBalance(){
    return _payments - _debt;
  }

  /** return True if the Terminal has at least one friend. */
  public boolean hasFriends(){
    return !_friends.isEmpty();
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

  public void removeFriend(Terminal friend){
    _friends.remove(friend.getId());
  }

  public boolean isFriend(Terminal friend){
    return _friends.containsKey(friend.getId());
  }

  /** The classes BasicTerminal and FancyTerminal implement this method. */
  public abstract String getType();

  public boolean setOnSilent(){
    if(_mode == TerminalMode.OFF){
      _mode = TerminalMode.SILENCE;
      _stateChange = NotificationType.O2S;
      return true;
    }
    if(_mode == TerminalMode.IDLE || (_mode == TerminalMode.BUSY && !onGoingCommunication())){
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
    if(_mode == TerminalMode.OFF){
      _mode = TerminalMode.IDLE;
      _stateChange = NotificationType.O2I;
      return true;
    }
    if(_mode == TerminalMode.SILENCE){
      _mode = TerminalMode.IDLE;
      _stateChange = NotificationType.S2I;
      return true;
    }
    if(_mode == TerminalMode.BUSY && !onGoingCommunication()){
      _mode = TerminalMode.IDLE;
      _stateChange = NotificationType.B2I;
      return true;
    }
    return false;
  }

  public void setBusy(){
    _mode = TerminalMode.BUSY;
  }

  public Collection<Communication> getMadeCommunications(){
    return _madeCommunications.values();
  }

  public Collection<Communication> getReceivedCommunications(){
    return _receivedCommunications.values();
  }

  public boolean isUsed(){
    return !(_receivedCommunications.isEmpty() && _madeCommunications.isEmpty());
  }

  public boolean onGoingCommunication(){
    if(_onGoingCommunication == null){
      return false;
    }
    return _onGoingCommunication.isGoing();
  }

  public InteractiveCommunication getOnGoingCommunication(){
    return _onGoingCommunication;
  }

  public boolean hasMadeCommunication(int id){
    return _madeCommunications.containsKey(id);
  }

  public Communication getCommunication(int id){
    return _madeCommunications.get(id);
  }

  public void addPayment(double pay){
    _payments += pay;
  }

  public void addDebt(double debt){
    _debt += debt;
  }

  public void removeDebt(double pay){
    _debt -= pay;
  }

  public Communication makeSms(int id, Terminal to, String message){
    Communication sms = new TextCommunication(id, this, to, message);
    _madeCommunications.put(sms.getId(), sms);
    return sms;
  }

  public InteractiveCommunication makeVoiceCall(int id, Terminal to){
    setWasMode(getMode());
    setBusy();
    InteractiveCommunication voice = new VoiceCommunication(id, this, to);
    voice.setOnGoing();
    _onGoingCommunication = voice;
    _madeCommunications.put(voice.getId(), voice);
    return voice;
  }

  public InteractiveCommunication makeVideoCall(int id, Terminal to){
    setWasMode(getMode());
    setBusy();
    InteractiveCommunication video = new VideoCommunication(id, this, to);
    video.setOnGoing();
    _onGoingCommunication = video;
    _madeCommunications.put(video.getId(), video);
    return video;
  }

  public void accept(Communication com){
    _receivedCommunications.put(com.getId(), com);
  }

  public void acceptCall(InteractiveCommunication com){
    setWasMode(getMode());
    setBusy();
    _onGoingCommunication = com;
    _receivedCommunications.put(com.getId(), com);
  }

  public void endOnGoingCommunication(){
    if(getWasMode().equals(TerminalMode.IDLE)){
      setOnIdle();
    }
    if(getWasMode().equals(TerminalMode.SILENCE)){
      setOnSilent();
    }
  }

  public void acceptEndCall(){
    if(getWasMode().equals(TerminalMode.IDLE)){
      setOnIdle();
    }
    if(getWasMode().equals(TerminalMode.SILENCE)){
      setOnSilent();
    }
  }

  public boolean isBasic(){
    return "BASIC".equals(getType());
  }

  public boolean isOff(){
    return getMode().equals(TerminalMode.OFF);
  }

  public boolean isSilent(){
    return getMode().equals(TerminalMode.SILENCE);
  }

  public boolean isBusy(){
    return getMode().equals(TerminalMode.BUSY);
  }

  public void register(Observer observer){
    _observers.add(observer);
  }

  public void unregister(Observer o){
    _observers.removeIf(o::equals);
  }

  public void notifyObserver(){
    for(Observer observer : _observers){
      observer.update(_stateChange);
    }
  }

  public List<Observer> getObservers(){
    return _observers;
  }

  @Override
  public String toString() {
    return getId();
  }

  public boolean alreadyHasNotification(Client client){
    for(Observer notification : getObservers()){
      if(notification.isForClient(client)){
        return true;
      }
    }
    return false;
  }

  /**
   * Checks if this terminal can end the current interactive communication.
   *
   * @return true if this terminal is busy (i.e., it has an active interactive communication) and
   *          it was the originator of this communication.
   **/
  public boolean canEndCurrentCommunication() {
    // FIXME add implementation code
    if(_mode.equals(TerminalMode.BUSY)){
      return _onGoingCommunication.getFromTerminal() == this;
    }
    return false;
  }
  
  /**
   * Checks if this terminal can start a new communication.
   *
   * @return true if this terminal is neither off neither busy, false otherwise.
   **/
  public boolean canStartCommunication() {
    // FIXME add implementation code
    return !(_mode.equals(TerminalMode.OFF) || _mode.equals(TerminalMode.BUSY));
  }
}
