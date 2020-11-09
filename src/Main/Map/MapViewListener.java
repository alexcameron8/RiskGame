package Main.Map;

/**
 * Interface required to handle MapView events.
 *
 * @author Benjamin Munro
 */
public interface MapViewListener {
    /**
     * Method to handle events.
     * @param e Event to handle.
     */
    void handleMapUpdate(MapEvent e);
}
