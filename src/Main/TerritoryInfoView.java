package Main;

import Main.Map.MapEvent;
import Main.Map.MapTerritoryEvent;
import Main.Map.MapViewListener;
import Map.Territory;

import javax.swing.*;
import java.awt.*;

/**
 * this class displays the info of a territory, the name of the territory
 * who ownes the territory, the number of troops on the territory and the neighbours
 *
 * @author Thomas
 */
public class TerritoryInfoView extends JPanel implements MapViewListener {
    /**
     * constructor for TerritoryInfoView which creates the display with a default territory of Ontario
     *
     * @param rm
     */
    TerritoryInfoView(RiskModel rm){
        // JPanel Config
        super();
        this.setPreferredSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()/6,(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
        this.setLayout(new BorderLayout());
        this.setBackground(RiskModel.BACKGROUND);
        setInfo(rm.getMap().getTerritories().get(0));
    }

    /**
     * Updates the display to show the current territories info
     *
     * @param territory
     */
    public void setInfo(Territory territory){
        this.removeAll();
        this.repaint();

        JLabel territoryName = new JLabel(territory.getName());
        territoryName.setAlignmentX(Component.CENTER_ALIGNMENT);
        territoryName.setFont(new Font(territoryName.getName(), Font.BOLD, 30));
        this.add(territoryName, BorderLayout.PAGE_START);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(RiskModel.BACKGROUND);

        JLabel owner = new JLabel(("\nOwner: "+(territory.getOwner()==null? "none":territory.getOwner().getName())));
        owner.setAlignmentX(Component.LEFT_ALIGNMENT);
        owner.setFont(new Font(territoryName.getName(), Font.PLAIN, 25));
        infoPanel.add(owner);

        JLabel troops = new JLabel(("\nTroops: "+territory.getSoldiers()));
        troops.setAlignmentX(Component.LEFT_ALIGNMENT);
        troops.setFont(new Font(territoryName.getName(), Font.PLAIN, 25));
        infoPanel.add(troops);

        JTextArea neighboursInfo = new JTextArea();
        neighboursInfo.setEditable(false);
        neighboursInfo.setTabSize(2);
        neighboursInfo.setBackground(RiskModel.BACKGROUND);

        String info = "\nNeighbours:";
        for(Territory neighbour : territory.getNeighbours()){
            info += "\n\t" + neighbour.getName() + " (" + (neighbour.getOwner()==null? "none":neighbour.getOwner().getName()) +")";
        }
        neighboursInfo.setText(info);
        neighboursInfo.setFont(new Font(territoryName.getName(), Font.PLAIN, 20));
        neighboursInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(neighboursInfo);
        this.add(infoPanel, BorderLayout.CENTER);
    }

    /**
     * changes the display when a new territory is clicked
     *
     * @param e Event to handle.
     */
    @Override
    public void handleMapUpdate(MapEvent e) {
        if(e instanceof MapTerritoryEvent){
            setInfo(((MapTerritoryEvent)e).getMapTerritory());
        }
    }
}
