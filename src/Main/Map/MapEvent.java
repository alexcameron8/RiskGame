package Main.Map;

import Map.Territory;

import java.util.EventObject;

/**
 * Object representing an event triggered by the MapView
 *
 * @author Benjamin Munro
 */
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

