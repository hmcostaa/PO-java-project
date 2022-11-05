package prr.core.client;

import java.io.Serializable;

public class IsPlatinum implements ClientLevel, Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;

    Client _client;

    public IsPlatinum(Client client){
        _client = client;
    }

    @Override
    public void turnNormal() {
        if(_client.getBalance() < 0){
            _client.noConsecutiveTexts();
            _client.noConsecutiveVideoCalls();
            _client.setClientState(_client.getNormalState());
        }
    }

    @Override
    public void turnGold() {
        if(_client.getConsecutiveTexts() == 2 && _client.getBalance() >= 0){
            _client.noConsecutiveTexts();
            _client.noConsecutiveVideoCalls();
            _client.setClientState(_client.getGoldState());
        }
    }

    @Override
    public void turnPlatinum() {}

    @Override
    public String toString() {
        return "PLATINUM";
    }
}
