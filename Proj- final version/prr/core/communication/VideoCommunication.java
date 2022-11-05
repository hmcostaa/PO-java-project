package prr.core.communication;

import prr.core.terminal.Terminal;
import prr.core.tariff.TariffPlan;

public class VideoCommunication extends InteractiveCommunication implements Visitable{

    public VideoCommunication(int id, Terminal from, Terminal to){
        super(id, from, to);
    }

    @Override
    public String getType() {
        return "VIDEO";
    }

    @Override
    public int getSize() {
        return getDuration();
    }

    @Override
    public double accept(TariffPlan tariff) {
        return tariff.computeCost(getFromClient(), this);
    }
}
