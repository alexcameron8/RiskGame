package Main.PlayerBar;

import Main.RiskEvent;
import Main.RiskModel;
import Main.RiskViewListener;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class PlayerBarView extends JPanel implements RiskViewListener {

    private JLabel[] playersList;
    private RiskModel rm;
    private PlayerBarModel pbm;
    private JPanel playerNamesPanel;

    public PlayerBarView(RiskModel rm){
        // JPanel Config
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBackground(RiskModel.BACKGROUND);

        pbm = new PlayerBarModel(rm);
        pbm.addPlayerBarModelViews(this);
        this.rm = rm;

        initPlayerPanel();
        initPlayersList();
    }

    public void initPlayerPanel(){
        JLabel playersPanel = new JLabel("Players:");
        playersPanel.setFont(new Font(playersPanel.getText(),Font.BOLD, 25));
        this.add(playersPanel);
    }
    public void initPlayersList(){
        playerNamesPanel = new JPanel();
        playerNamesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        playerNamesPanel.setBackground(RiskModel.BACKGROUND);

        playersList = new JLabel[pbm.getPlayers().size()];
        for (int i = 0; i < pbm.getPlayers().size(); i++) {
            playersList[i]=new JLabel(pbm.getPlayers().get(i).getName());
            playersList[i].setBackground(pbm.getPlayers().get(i).getPlayerColor());
            playersList[i].setBorder(null);
            playersList[i].setFont(new Font(playersList[i].getText(),Font.BOLD, 25));
            if(pbm.getPlayers().get(i).getName().equals(pbm.getCurrentTurn().getName())){
                playersList[i].setOpaque(true);
                playersList[i].setBorder(new LineBorder(Color.BLACK,2));
            }
            playerNamesPanel.add(playersList[i]);

        }

        this.add(playerNamesPanel);
    }

    @Override
    public void handleTurnUpdate(RiskEvent e){
        for(JLabel playerName: playersList){
            if(playerName.getText().equals(pbm.getCurrentTurn().getName())){
                playerName.setOpaque(true);
                playerName.setBorder(new LineBorder(Color.BLACK,2));
            }
            else{
                playerName.setBorder(null);
                playerName.setOpaque(false);
            }
        }
    }
}
