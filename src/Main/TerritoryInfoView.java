package Main;

import Map.Territory;

import javax.swing.*;
import java.awt.*;

public class TerritoryInfoView extends JPanel implements MapViewListener{

    private JTextArea info = new JTextArea("Territory Info");
    TerritoryInfoView(){
        // JPanel Config
        this.setLayout(new BorderLayout());
        Color background = new Color(0,50,255,50);
        info.setEditable(false);
        info.setTabSize(4);
        info.setBackground(background);
        this.add(info, BorderLayout.CENTER);
    }

    public void setInfo(Territory territory){
        String info = territory.getName();
        info += "\n\tOwner: "+(territory.getOwner()==null? "none":territory.getOwner().getName());
        info += "\n\tTroops: "+territory.getSoldiers();
        info += "\n\tNeighbours:";
        for(Territory neighbour : territory.getNeighbours()){
            info += "\n\t\t- " + neighbour.getName() + " (" + (territory.getOwner()==null? "none":territory.getOwner().getName()) +")";
        }
        this.info.setText(info);
    }

    @Override
    public void handleMapUpdate(MapEvent e) {
        setInfo(e.getMapTerritory());
    }
}
