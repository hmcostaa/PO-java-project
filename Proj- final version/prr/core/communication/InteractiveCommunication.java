package prr.core.communication;

import prr.core.terminal.Terminal;

public abstract class InteractiveCommunication extends Communication {

    private int _duration;

    public InteractiveCommunication(int id, Terminal from, Terminal to){
        super(id, from, to);
    }

    @Override
    public void setFinished(double cost) {
        super.setFinished(cost);
    }

    public void setDuration(int duration){
        _duration = duration;
    }

    protected int getDuration(){
        return _duration;
    }
}
