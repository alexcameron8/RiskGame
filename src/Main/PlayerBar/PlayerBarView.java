package Main.PlayerBar;

import Player.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class PlayerBarView extends JPanel {

    private Color darkBlue = new Color(102,178,255);
    private JList<Player> playersList;
    private Player[] players = {new Player("Alex"), new Player("Ben"), new Player("Thomas")};

    public PlayerBarView(){
        // JPanel Config
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBackground(darkBlue);
        initPlayerPanel();
        initPlayersList();
    }

    public void initPlayerPanel(){
        JLabel playersPanel = new JLabel("Players:");
        playersPanel.setBackground(darkBlue);
        this.add(playersPanel);
    }
    public void initPlayersList(){
        playersList = new JList<>(players);
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

}
