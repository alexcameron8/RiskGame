package Main;

import java.util.EventObject;

public class MapEvent extends EventObject {
    private MapTerritory mapTerritory;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public MapEvent(Object source, MapTerritory mapTerritory) {
        super(source);
        this.mapTerritory = mapTerritory;
    }

    /**
     * Returns the Territory which the click action that generated the event was on.
     * @return Territory that was selected.
     */
    public MapTerritory getMapTerritory(){
        return this.mapTerritory;
    }
}
