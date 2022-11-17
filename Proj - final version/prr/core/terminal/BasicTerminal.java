package prr.core.terminal;

import prr.core.client.Client;

public class BasicTerminal extends Terminal {

    public BasicTerminal(String id, Client owner){
        super(id, owner);
    }

    public String getType(){
        return "BASIC";
    }
}
