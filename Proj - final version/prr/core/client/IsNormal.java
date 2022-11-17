package prr.core.client;

import java.io.Serializable;

public class IsNormal implements ClientLevel, Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;

    Client _client;

    public IsNormal(Client client){
        _client = client;
    }

    @Override
    public void turnNormal() {}

    @Override
    public void turnGold() {
        if(_client.getBalance() > 500){
            _client.noConsecutiveTexts();
            _client.noConsecutiveVideoCalls();
            _client.setClientState(_client.getGoldState());
        }
    }

    @Override
    public void turnPlatinum() {}

    @Override
    public String toString() {
        return "NORMAL";
    }
}
