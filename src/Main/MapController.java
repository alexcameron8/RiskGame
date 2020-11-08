package Main;

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
            System.out.println("triggered");
            mapModel.setActiveTerritoryByID(e.getActionCommand());
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getItem() instanceof JCheckBox){
            if(((JCheckBox) e.getItem()).getActionCommand().equals("territoryName")){
                mapModel.setTerritoryNamesVisible(e.getStateChange()==1?true:false);
            }
            if(((JCheckBox) e.getItem()).getActionCommand().equals("troopDots")){
                mapModel.setTroopDotsVisible(e.getStateChange()==1?true:false);
            }
            if(((JCheckBox) e.getItem()).getActionCommand().equals("troopCount")){
                mapModel.setTroopCountVisible(e.getStateChange()==1?true:false);
            }
            if(((JCheckBox) e.getItem()).getActionCommand().equals("playerTerritoryColor")){
                mapModel.setPlayerTerritoryColorVisible(e.getStateChange()==1?true:false);
            }
        }
    }
}
