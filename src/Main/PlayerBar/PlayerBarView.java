package Main.PlayerBar;

import Main.RiskEvent;
import Main.RiskModel;
import Main.RiskViewListener;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * the GUI for th Player Bar which shows the players and which player's turn it is.
 * @author Thomas
 */
public class PlayerBarView extends JPanel implements RiskViewListener {

    private ArrayList<JLabel> playersList;
    private RiskModel rm;
    private PlayerBarModel pbm;
    private JPanel playerNamesPanel;

    /**
     * constructor for PlayerBarView
     *
     * @param rm The current RiskModel
     */
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

    /**
     * create a panel the displays "Players: "
     */
    public void initPlayerPanel(){
        JLabel playersPanel = new JLabel("Players:");
        playersPanel.setFont(new Font(playersPanel.getText(),Font.PLAIN, 20));
        this.add(playersPanel);
    }

    /**
     * Creates a panel that displays all the Names of the players and highlights which player starts
     */
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

    /**
     * update the player Bar for when a player is eliminated or the current turn of a player
     *
     * @param e the RiskEvent to handle turn updates
     */
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
