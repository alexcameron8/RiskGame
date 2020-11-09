package Main.Map;

import Map.Territory;

/**
 * Object representing an event triggered by the MapView
 *
 * @author Benjamin Munro
 */
public class MapTerritoryEvent extends MapEvent {
    private Territory territory;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public MapTerritoryEvent(Object source, Territory territory) {
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

