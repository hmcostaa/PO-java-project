package prr.core.client;

import prr.core.tariff.BasicPlan;
import prr.core.tariff.TariffPlan;
import prr.core.terminal.Terminal;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Client implements Serializable{

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;

    ClientLevel isNormal;
    ClientLevel isGold;
    ClientLevel isPlatinum;

    /** Client unique key. */
    private String _key;
    /** Client name. */
    private String _name;
    /** Client tax number. */
    private int _taxNumber;
    /** true if Client can receive notifications, false otherwise. */
    private boolean _receiveNotifications;
    /** Client type: NORMAL, GOLD or PLATINUM. */
    private ClientLevel _type;
    /** Terminals owned by this Client. */
    private List<Terminal> _terminals = new ArrayList<>();

    private TariffPlan _tariff;

    private int _consecutiveVideoCalls;

    private int _consecutiveTexts;

    /** Client Constructor. */
    public Client(String key, String name, int taxNumber){
        _key = key;
        _name = name;
        _taxNumber = taxNumber;
        _receiveNotifications = true;
        _consecutiveVideoCalls = 0;
        _consecutiveTexts = 0;
        isNormal = new IsNormal(this);
        isGold = new IsGold(this);
        isPlatinum = new IsPlatinum(this);

        _type = isNormal;

        _tariff = new BasicPlan("BASIC");
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

    public String getType(){
        return _type.toString();
    }

    public String getNotifications(){
        if(_receiveNotifications){
            return "YES";
        }
        return "NO";
    }

    public boolean receiveNotifications(){
        return _receiveNotifications;
    }


    /** return the number of Terminals owned by the client. */
    public int numberTerminals(){
        return _terminals.size();
    }

    public List<Terminal> getTerminals(){
        return _terminals;
    }

    public long getPayments(){
        long num = 0;
        for(Terminal term : _terminals){
            num += term.getPayments();
        }
        return num;
    }

    public long getDebts(){
        long num = 0;
        for(Terminal term : _terminals){
            num += term.getDebt();
        }
        return num;
    }

    public TariffPlan getTariff(){
        return _tariff;
    }

    public long getBalance(){
        return getPayments() - getDebts();
    }

    public void activateNotifications(){
        _receiveNotifications = true;
    }

    public void diactivateNotifications(){
        _receiveNotifications = false;
    }

    public void noConsecutiveVideoCalls(){
        _consecutiveVideoCalls = 0;
    }

    public int getConsecutiveVideoCalls(){
        return _consecutiveVideoCalls;
    }

    public void noConsecutiveTexts(){
        _consecutiveTexts = 0;
    }

    public int getConsecutiveTexts(){
        return _consecutiveTexts;
    }

    public void incrementConsecutiveTexts(){
        _consecutiveTexts++;
    }

    public void incrementConsecutiveVideoCalls(){
        _consecutiveVideoCalls++;
    }

    public void setClientState(ClientLevel newClientLevel){
        _type = newClientLevel;
    }

    public void turnNormal(){
        _type.turnNormal();
    }

    public void turnGold(){
        _type.turnGold();
    }

    public void turnPlatinum(){
        _type.turnPlatinum();
    }

    public ClientLevel getNormalState(){
        return isNormal;
    }

    public ClientLevel getGoldState(){
        return isGold;
    }

    public ClientLevel getPlatinumState(){
        return isPlatinum;
    }

    public void addTerminal(Terminal terminal){
        _terminals.add(terminal);
    }
}
