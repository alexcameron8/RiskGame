package Main;

import Main.ActionBar.ActionBarView;
import Main.PlayerBar.PlayerBarView;

import javax.swing.*;
import java.awt.*;

public class RiskView extends JFrame {
    private RiskModel riskModel;
    private RiskController riskController;

    RiskView(){
        this.riskModel = new RiskModel();
        this.riskController = new RiskController(riskModel);

        this.setSize(new Dimension(800, 600));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setJMenuBar(new MenuBarView());

        MapView mapView = new MapView();

        this.add(new ActionBarView(), BorderLayout.PAGE_START);
        this.add(mapView, BorderLayout.CENTER);
        this.add(new TerritoryInfoView(), BorderLayout.LINE_END);
        this.add(new PlayerBarView(), BorderLayout.PAGE_END);

        this.setVisible(true);
    }

    public static void main(String[] args) {
        new RiskGame();
    }
}
