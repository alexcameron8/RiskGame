package Main;

import Map.Territory;

import java.util.EventObject;

public class MapEvent extends EventObject {
    private Territory territory;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public MapEvent(Object source, Territory territory) {
        super(source);
        this.territory = territory;
    }

    /**
     * Returns the Territory which the click action that generated the event was on.
     * @return Territory that was selected.
     */
    public Territory getMapTerritory(){
        return this.territory;
    }
}

