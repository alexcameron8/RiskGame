package Main.Map;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Controller for MapView
 * @author Benjamin Munro
 */
public class MapController implements ActionListener, ItemListener {
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
        Object source = e.getSource();
        if(source instanceof MapView){
            mapModel.setActiveTerritoryByID(e.getActionCommand());
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getItem() instanceof JCheckBox){
            if(((JCheckBox) e.getItem()).getActionCommand().equals(MapView.TERRITORY_NAME_TOGGLE_ACTION)){
                mapModel.setTerritoryNamesVisible(e.getStateChange()==ItemEvent.SELECTED);
            }
            if(((JCheckBox) e.getItem()).getActionCommand().equals(MapView.TROOP_DOTS_TOGGLE_ACTION)){
                mapModel.setTroopDotsVisible(e.getStateChange()==ItemEvent.SELECTED);
            }
            if(((JCheckBox) e.getItem()).getActionCommand().equals(MapView.TROOP_COUNT_TOGGLE_ACTION)){
                mapModel.setTroopCountVisible(e.getStateChange()==ItemEvent.SELECTED);
            }
            if(((JCheckBox) e.getItem()).getActionCommand().equals(MapView.PLAYER_TERRITORY_COLOUR_TOGGLE_ACTION)){
                mapModel.setPlayerTerritoryColorVisible(e.getStateChange()==ItemEvent.SELECTED);
            }
        }
    }
}
