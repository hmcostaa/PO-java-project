package prr.core.communication;

import prr.core.tariff.TariffPlan;
import prr.core.terminal.Terminal;

public class TextCommunication extends Communication implements Visitable{

    private String _message;

    public TextCommunication(int id, Terminal from, Terminal to, String message){
        super(id, from, to);
        _message = message;
    }

    @Override
    public String getType() {
        return "TEXT";
    }

    @Override
    public int getSize() {
        return _message.length();
    }

    @Override
    public double accept(TariffPlan tariff){
        return tariff.computeCost(getFromClient(), this);
    }
}
