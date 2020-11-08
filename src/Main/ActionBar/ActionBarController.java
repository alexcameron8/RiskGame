package Main.ActionBar;

import Main.*;
import Map.Territory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionBarController implements ActionListener, MapViewListener {
    private ActionBarView abv;
    private ActionBarModel abm;
    private Territory territory;
    private int numOfTroops;

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
                abm.nextTurn(abm.getRiskModel());
                abv.removeDeployTroopsBar();

                System.out.println("next turn");
            } else if(e.getActionCommand().equals("numTroops")) {
                if((Integer) abv.getNumberOfTroops().getSelectedItem()!=null) {
                    numOfTroops = (Integer) abv.getNumberOfTroops().getSelectedItem();
                    System.out.println("Number of troops to deploy : " + numOfTroops);
                }else{
                    System.out.println("error");
                }
            } else if (e.getActionCommand().equals("deploy")) {
                abm.deployTroops(territory, numOfTroops);
                abv.removeDeployTroopsBar();
                abv.updateUI();
                System.out.println("place troops");
         }
    }




    @Override
    public void handleMapUpdate(MapEvent e){
        if(e instanceof MapTerritoryEvent){
            territory = ((MapTerritoryEvent) e).getMapTerritory();
            System.out.println("This is a test for mapevent handler:" + territory.getName());
            setTerritory(territory);
        }
    }
    public Territory getTerritory() {
        return territory;
    }
}