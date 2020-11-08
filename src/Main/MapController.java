package Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller for MapView
 * @author Benjamin Munro
 */
public class MapController implements ActionListener {
    private MapModel mapModel;

    /**
     * Creates a new map controller.
     * @param mapModel MapModel used by the map view.
     */
    MapController(MapModel mapModel){
        this.mapModel = mapModel;
    }

    /**
     * Handles actions performed.
     * @param e ActionEvent to handle.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        mapModel.setActiveTerritoryByID(e.getActionCommand());
    }
}
