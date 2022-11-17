package prr.core.communication;

import java.io.Serializable;
import prr.core.terminal.Terminal;

public abstract class Communication implements Serializable {

    private static final long serialVersionUID = 202208091753L;

    private int _id;
    private boolean _isPaid;
    private double _cost;
    private boolean _isGoing;
    private Terminal _from;
    private Terminal _to;
}
