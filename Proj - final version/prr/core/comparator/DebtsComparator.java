package prr.core.comparator;

import prr.core.client.Client;

import java.util.Comparator;

public class DebtsComparator implements Comparator<Client> {

    @Override
    public int compare(Client o1, Client o2) {
        if(o1.getDebts() == o2.getDebts()){
            return o1.getIdentifier().compareTo(o2.getIdentifier());
        }
        return Math.round(o2.getDebts()) - Math.round(o1.getDebts());
    }
}
