package prr.core.communication;

import java.io.Serializable;

import prr.core.client.Client;
import prr.core.terminal.Terminal;

public abstract class Communication implements Serializable, Visitable{

    private static final long serialVersionUID = 202208091753L;

    private int _id;
    private boolean _isPaid;
    private double _cost;
    private boolean _isGoing;
    private Terminal _from;
    private Terminal _to;

    protected Communication(int id, Terminal from, Terminal to){
        _id = id;
        _from = from;
        _to = to;
        _isPaid = false;
    }

    public abstract String getType();

    public int getId(){
        return _id;
    }

    public boolean isPaid(){
        return _isPaid;
    }

    public void setPaid(){
        _isPaid = true;
    }

    public void setOnGoing(){
        _isGoing = true;
    }

    public void setFinished(double cost){
        _cost = cost;
        _isGoing = false;
    }

    public boolean isGoing(){
        return _isGoing;
    }

    public double getCost(){
        return _cost;
    }

    public Terminal getFromTerminal(){
        return _from;
    }

    public Terminal getToTerminal(){
        return _to;
    }

    public Client getFromClient(){
        return _from.getOwner();
    }

    public String toString(){
        String string = getType() + "|" + getId() + "|" + getFromTerminal().getId() + "|" + getToTerminal().getId()
                + "|" + getSize() + "|" + Math.round(getCost()) + "|";
        if(isGoing()){
            string += "ONGOING";
        }
        else{
            string += "FINISHED";
        }
        return string;
    }

    public abstract int getSize();
}
