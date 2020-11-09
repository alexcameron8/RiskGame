package Main.Map;

public class MapRedrawEvent extends MapEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public MapRedrawEvent(Object source) {
        super(source);
    }
}
