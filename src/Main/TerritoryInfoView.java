package Main;

import Map.Territory;

import javax.swing.*;
import java.awt.*;

public class TerritoryInfoView extends JPanel implements MapViewListener{

    private JTextArea info1 = new JTextArea();

    TerritoryInfoView(RiskModel rm){
        // JPanel Config
        this.setLayout(new BorderLayout());
        Color background = new Color(174,187,239,255);
        info1.setEditable(false);
        info1.setTabSize(4);
        info1.setBackground(background);
        setInfo(rm.getMap().getTerritory("Ontario"));
        this.add(info1, BorderLayout.CENTER);
    }

    public void setInfo(Territory territory){
        String info = territory.getName();
        info += "\n\tOwner: "+(territory.getOwner()==null? "none":territory.getOwner().getName());
        info += "\n\tTroops: "+territory.getSoldiers();
        info += "\n\tNeighbours:";
        for(Territory neighbour : territory.getNeighbours()){
            info += "\n\t\t- " + neighbour.getName() + " (" + (neighbour.getOwner()==null? "none":neighbour.getOwner().getName()) +")";
        }
        info1.setText(info);
    }

    @Override
    public void handleMapUpdate(MapEvent e) {
        setInfo(e.getMapTerritory());

    }
}
