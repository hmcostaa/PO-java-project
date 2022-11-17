package prr.core.client;

import java.io.Serializable;

public class IsGold implements ClientLevel, Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;

    Client _client;

    public IsGold(Client client){
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
    public void turnGold() {}

    @Override
    public void turnPlatinum() {
        if(_client.getConsecutiveVideoCalls() == 5 && _client.getBalance() >= 0){
            _client.noConsecutiveVideoCalls();
            _client.noConsecutiveTexts();
            _client.setClientState(_client.getPlatinumState());
        }
    }

    @Override
    public String toString() {
        return "GOLD";
    }
}
