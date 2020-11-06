package Main;

import java.util.EventObject;

public class MapEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public MapEvent(Object source) {
        super(source);
    }
}
