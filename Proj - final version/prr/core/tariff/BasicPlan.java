package prr.core.tariff;

import prr.core.client.Client;
import prr.core.communication.TextCommunication;
import prr.core.communication.VideoCommunication;
import prr.core.communication.VoiceCommunication;

public class BasicPlan extends TariffPlan{

    public BasicPlan(String name){
        super(name);
    }

    @Override
    public double computeCost(Client client, TextCommunication com) {
        int size = com.getSize();
        String type = client.getType();
        if(size < 50){
            if("NORMAL".equals(type)){
                return 10;
            }
            if("GOLD".equals(type)){
                return 10;
            }
            if("PLATINUM".equals(type)){
                return 0;
            }
        }
        if(50 <= size && size < 100){
            if("NORMAL".equals(type)){
                return 16;
            }
            if("GOLD".equals(type)){
                return 10;
            }
            if("PLATINUM".equals(type)){
                return 4;
            }
        }
        if(size >= 100){
            if("NORMAL".equals(type)){
                return 2 * size;
            }
            if("GOLD".equals(type)){
                return 2 * size;
            }
            if("PLATINUM".equals(type)){
                return 4;
            }
        }
        return 0;
    }

    @Override
    public double computeCost(Client client, VoiceCommunication com) {
        int size = com.getSize();
        String type = client.getType();
        if("NORMAL".equals(type)){
            return 20 * size;
        }
        if("GOLD".equals(type)){
            return 10 * size;
        }
        if("PLATINUM".equals(type)){
            return 10 * size;
        }
        return 0;
    }

    @Override
    public double computeCost(Client client, VideoCommunication com) {
        int size = com.getSize();
        String type = client.getType();
        if("NORMAL".equals(type)){
            return 30 * size;
        }
        if("GOLD".equals(type)){
            return 20 * size;
        }
        if("PLATINUM".equals(type)){
            return 10 * size;
        }
        return 0;
    }
}
