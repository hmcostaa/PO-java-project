package prr.core.tariff;

import prr.core.communication.TextCommunication;
import prr.core.client.Client;
import prr.core.communication.VideoCommunication;
import prr.core.communication.VoiceCommunication;

import java.io.Serializable;

public abstract class TariffPlan implements Serializable {

    private static final long serialVersionUID = 202208091753L;

    private String _name;

    public TariffPlan(String name){
        _name = name;
    }

    public abstract double computeCost(Client client, TextCommunication com);

    public abstract double computeCost(Client client, VoiceCommunication com);

    public abstract double computeCost(Client client, VideoCommunication com);
}
