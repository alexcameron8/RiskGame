package Main.PlayerBar;

import Main.PlayerBar.*;
import Main.RiskModel;
import Player.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class PlayerBarView extends JPanel {

    private Color darkBlue = new Color(102,178,255);
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
        for(Player player: pbm.getPlayers()){
            System.out.println(player);
        }

        playersList = new JList(pbm.getPlayers().toArray());

        DefaultListCellRenderer renderer= (DefaultListCellRenderer) playersList.getCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);

        playersList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        playersList.setVisibleRowCount(1);
        playersList.setOpaque(false);
        playersList.setCellRenderer(new TransparentListCellRenderer());
        this.add(playersList);
    }

    public class TransparentListCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            setOpaque(isSelected);
            return this;
        }

    }
    class CellRenderer extends JLabel implements ListCellRenderer {
        public CellRenderer() {
            setOpaque(true);
            setHorizontalAlignment(CENTER);
            setVerticalAlignment(CENTER);
        }

        public Component getListCellRendererComponent(
                JList list,
                Object displayItem,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }


            setText((String)displayItem);
            return this;
        }
    }

}
