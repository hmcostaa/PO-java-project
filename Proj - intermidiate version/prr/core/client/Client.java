package prr.core.client;

import prr.core.terminal.Terminal;
import prr.core.terminal.TerminalMode;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Client implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;

    /** Client unique key. */
    private String _key;
    /** Client name. */
    private String _name;
    /** Client tax number. */
    private int _taxNumber;
    /** true if Client can receive notifications. */
    private boolean _receiveNotifications;
    /** Client type: NORMAL, GOLD or PLATINUM. */
    private ClientLevel _type;
    /** Terminals owned by this Client. */
    private List<Terminal> _terminals = new ArrayList<>();

    /** Client Constructor. */
    public Client(String key, String name, int taxNumber){
        _key = key;
        _name = name;
        _taxNumber = taxNumber;
        _receiveNotifications = true;
        _type = ClientLevel.NORMAL;
    }
    public String getIdentifier(){
        return _key;
    }

    public String getName(){
        return _name;
    }

    public int getTaxNumber(){
        return _taxNumber;
    }

    public ClientLevel getType(){
        return _type;
    }

    public String getNotifications(){
        if(_receiveNotifications){
            return "YES";
        }
        return "NO";
    }
    /** return the number of Terminals owned by the client that are not OFF. */
    public int activeTerminals(){
        int num = 0;
        for(Terminal term : _terminals){
            if(!term.getMode().equals(TerminalMode.OFF)){
                num +=1;
            }
        }
        return num;
    }

    public List<Terminal> getTerminals(){
        return _terminals;
    }

    public int getPayments(){
        int num = 0;
        for(Terminal term : _terminals){
            num += term.getPayments();
        }
        return num;
    }

    public int getDebts(){
        int num = 0;
        for(Terminal term : _terminals){
            num += term.getDebt();
        }
        return num;
    }

    public void activateNotifications(){
        _receiveNotifications = true;
    }

    public void disactivateNotifications(){
        _receiveNotifications = false;
    }

    public void setNormal(){
        _type = ClientLevel.NORMAL;
    }


    public void addTerminal(Terminal terminal){
        _terminals.add(terminal);
    }
}
