package Main;

import Map.Territory;

import javax.swing.*;
import java.awt.*;

public class TerritoryInfoView extends JPanel implements MapViewListener{

    private Color background = new Color(174,187,239,255);

    TerritoryInfoView(RiskModel rm){
        // JPanel Config
        super();
        this.setPreferredSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()/6,(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
        this.setLayout(new BorderLayout());
        this.setBackground(background);
        setInfo(rm.getMap().getTerritory("Ontario"));
    }

    public void setInfo(Territory territory){
        this.removeAll();
        this.repaint();

        JLabel territoryName = new JLabel(territory.getName());
        territoryName.setAlignmentX(Component.CENTER_ALIGNMENT);
        territoryName.setFont(new Font(territoryName.getName(), Font.BOLD, 30));
        this.add(territoryName, BorderLayout.PAGE_START);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(background);

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
        neighboursInfo.setBackground(background);

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

    @Override
    public void handleMapUpdate(MapEvent e) {
        setInfo(e.getMapTerritory());

    }
}
