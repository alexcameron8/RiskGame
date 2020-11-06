package Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapController implements ActionListener {
    private MapModel mapModel;

    MapController(MapModel mapModel){
        this.mapModel = mapModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mapModel.setActiveTerritoryByID(e.getActionCommand());
    }
}
