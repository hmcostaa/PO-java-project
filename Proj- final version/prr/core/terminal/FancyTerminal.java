package prr.core.terminal;

import prr.core.client.Client;

public class FancyTerminal extends Terminal {

    public FancyTerminal(String id, Client owner){
        super(id, owner);
    }

    public String getType(){
        return "FANCY";
    }
}
