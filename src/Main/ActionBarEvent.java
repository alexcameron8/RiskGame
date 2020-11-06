package Main;
import jdk.jfr.Event;

import java.util.EventObject;

public class ActionBarEvent extends EventObject {

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ActionBarEvent(Object source) {
        super(source);
    }
}

