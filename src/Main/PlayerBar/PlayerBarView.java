package Main.PlayerBar;

import Main.PlayerBar.*;
import Main.RiskEvent;
import Main.RiskModel;
import Main.RiskViewListener;
import Main.Turn;
import Player.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.Position;
import java.awt.*;
import java.util.ArrayList;

public class PlayerBarView extends JPanel implements RiskViewListener {

    private Color darkBlue = new Color(102,178,255);
    private DefaultListModel<Player> model;
    private JList<Player> playersList;
    private RiskModel rm;
    private PlayerBarController pbc;
    private PlayerBarModel pbm;

    public PlayerBarView(RiskModel rm){
        // JPanel Config
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBackground(darkBlue);

        pbm = new PlayerBarModel(rm);
        pbm.addPlayerBarModelViews(this);
        pbc = new PlayerBarController(this, pbm);
        this.rm = rm;

        initPlayerPanel();
        initPlayersList();
    }

    public void initPlayerPanel(){
        JLabel playersPanel = new JLabel("Players:");
        playersPanel.setBackground(darkBlue);
        this.add(playersPanel);
    }
    public void initPlayersList(){
        model = new DefaultListModel<Player>();
        for(Player player: pbm.getPlayers()){
            model.addElement(player);
        }
        playersList = new JList<>(model);
        DefaultListCellRenderer renderer= (DefaultListCellRenderer) playersList.getCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);

        playersList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        playersList.setVisibleRowCount(1);
        playersList.setOpaque(false);
        playersList.setCellRenderer(new TransparentListCellRenderer());
        this.add(playersList);
    }

    @Override
    public void handleTurnUpdate(RiskEvent e){
        Turn currentTurn = e.getCurrentTurn();
        ArrayList<Player> players = e.getPlayers();
        int activePlayerID = e.getActivePlayerID();
        for(Player player : players){
            //not done.
            if(model.contains(player)){
                //set the current players box to a different colour
            }
        }
    }

    public class TransparentListCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            setOpaque(isSelected);
            return this;
        }

    }
}
