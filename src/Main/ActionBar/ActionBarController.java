package Main.ActionBar;

import Main.MapEvent;
import Main.MapModel;
import Main.MapView;
import Main.MapViewListener;
import Map.Territory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionBarController implements ActionListener, MapViewListener {
    private ActionBarView abv;
    private ActionBarModel abm;
    private Territory territory;
    private int numOfTroops = 0;

    public ActionBarController(ActionBarView abv, ActionBarModel abm) {
        this.abv = abv;
        this.abm = abm;
    }
    public Territory setTerritory(Territory territory){
        this.territory = territory;
        return territory;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
            if (e.getActionCommand().equals("place")) {
                //Open troop deployment info
                abv.deployTroopsInfo();
                System.out.println("Test to see if place troops button works.");
            } else if (e.getActionCommand().equals("attack")) {
                //attack
                abm.attack();
                System.out.println("attack");
            } else if (e.getActionCommand().equals("next")) {
                //advances turn
                abm.getRiskModel().advanceTurn();
                System.out.println("next turn");

                //delete below
            } else if (e.getActionCommand().equals("setup")) {
                abm.getRiskModel().play();
                System.out.println("Test: play()");
            } else if(e.getActionCommand().equals("numTroops")) {
                //if(numOfTroops!=0 && abv.getNumberOfTroops().getSelectedItem()!=null) {
                numOfTroops = (Integer) abv.getNumberOfTroops().getSelectedItem();
                System.out.println("Number of troops to deploy : " + numOfTroops);
              //  }
            } else if (e.getActionCommand().equals("deploy")) {
                abm.deployTroops(territory, numOfTroops);
                 System.out.println("place troops");
         }
    }




    @Override
    public void handleMapUpdate(MapEvent e){
        territory = e.getMapTerritory();
        System.out.println("This is a tes for " + territory.getName());3
        setTerritory(territory);
    }
    public Territory getTerritory() {
        return territory;
    }
}