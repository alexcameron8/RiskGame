package Main;

import javax.swing.*;
import java.awt.*;

public class RiskGame extends JFrame {
    RiskGame(){
        this.setSize(new Dimension(800, 600));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setJMenuBar(new MenuBarView());
        this.add(new ActionBarView(), BorderLayout.PAGE_START);
        this.add(new RiskGameMap(), BorderLayout.CENTER);
        this.add(new TerritoryInfoView(), BorderLayout.LINE_END);
        this.add(new PlayerBarView(), BorderLayout.PAGE_END);

        this.setVisible(true);
    }

    public static void main(String[] args) {
        new RiskGame();
    }
}
