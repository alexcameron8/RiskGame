package Main;

import Map.Territory;

import javax.swing.*;
import java.awt.*;

public class TerritoryInfoView extends JPanel {

    private JTextArea info = new JTextArea("Territory Info");
    TerritoryInfoView(){
        // JPanel Config
        this.setLayout(new BorderLayout());
        Color backgroud = new Color(0,50,255,50);
        info.setEditable(false);
        info.setTabSize(4);
        info.setBackground(backgroud);
        this.add(info, BorderLayout.CENTER);
    }

    public void setInfo(Territory territory){
        String info = territory.getName();
        info += "\n\tOwner: "+territory.getOwner().getName();
        info += "\n\tTroops: "+territory.getSoldiers();
        info += "\n\tNeighbours:";
        for(Territory neighbour : territory.getNeighbours()){
            info += "\n\t\t- " + neighbour.getName() + " (" + neighbour.getOwner().getName() +")";
        }
        this.info.setText(info);
    }
}
