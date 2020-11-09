package Main.PlayerBar;

import Main.RiskEvent;
import Main.RiskModel;
import Main.RiskViewListener;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

public class PlayerBarView extends JPanel implements RiskViewListener {

    private ArrayList<JLabel> playersList;
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
        playersPanel.setFont(new Font(playersPanel.getText(),Font.PLAIN, 20));
        this.add(playersPanel);
    }
    public void initPlayersList(){
        playerNamesPanel = new JPanel();
        playerNamesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        playerNamesPanel.setBackground(RiskModel.BACKGROUND);

        playersList = new ArrayList<>();
        for (int i = 0; i < pbm.getPlayers().size(); i++) {
            playersList.add(new JLabel(pbm.getPlayers().get(i).getName()));
            playersList.get(i).setBackground(pbm.getPlayers().get(i).getPlayerColor());
            playersList.get(i).setFont(new Font(playersList.get(i).getText(),Font.PLAIN, 20));
            playersList.get(i).setBorder(new LineBorder(Color.BLACK,2));
            if(playersList.get(i).getText().equals(pbm.getCurrentTurn().getName())){
                playersList.get(i).setOpaque(true);
            }
            playerNamesPanel.add(playersList.get(i));
        }

        this.add(playerNamesPanel);
    }

    @Override
    public void handleTurnUpdate(RiskEvent e){
        if(e.getEliminatedPlayer()!=null){
            for(JLabel playerLabel: playersList){
                if(playerLabel.getText().equals(e.getEliminatedPlayer().getName())){
                    playersList.remove(playerLabel);
                    break;
                }
            }
        }
        playerNamesPanel.removeAll();
        for (int i = 0; i < pbm.getPlayers().size(); i++) {
            playersList.add(new JLabel(pbm.getPlayers().get(i).getName()));
            playersList.get(i).setBackground(pbm.getPlayers().get(i).getPlayerColor());
            playersList.get(i).setFont(new Font(playersList.get(i).getText(),Font.PLAIN, 20));
            playersList.get(i).setBorder(new LineBorder(Color.BLACK,2));
            if(playersList.get(i).getText().equals(pbm.getCurrentTurn().getName())){
                playersList.get(i).setOpaque(true);
            }
            else{
                playersList.get(i).setOpaque(false);
            }
            playerNamesPanel.add(playersList.get(i));
        }
        updateUI();
    }
}
