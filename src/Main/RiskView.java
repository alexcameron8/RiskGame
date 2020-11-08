package Main;

import Main.ActionBar.*;
import Main.IntializeFrame.InitializeView;
import Main.PlayerBar.*;
import Player.Player;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class RiskView extends JFrame {
    private RiskModel riskModel;
    private RiskController riskController;

    RiskView(){
        super("Risk");
        this.riskModel = new RiskModel();
        this.riskController = new RiskController(riskModel,this);
        riskModel.setState(GameState.MAIN_MENU);
        welcomeScreen();


        InitializeView initializeGame = new InitializeView();
        JOptionPane.showConfirmDialog(this, initializeGame, "Initialize Game ", JOptionPane.OK_CANCEL_OPTION);
        riskModel.setState(GameState.GENERATE_GAME);


        for(String playerName: initializeGame.getNameOfPlayers()){
            riskModel.addPlayer(new Player(playerName));
        }
        riskModel.play();

        this.setVisible(true);
        this.setSize(new Dimension(800,600));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        riskModel.addRiskView(this);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setJMenuBar(new MenuBarView());

        MapView mapView = new MapView(this.riskModel);
        TerritoryInfoView territoryInfoView = new TerritoryInfoView(riskModel);
        mapView.getMapModel().addMapListener(territoryInfoView);

        this.add(new ActionBarView(this, riskModel), BorderLayout.PAGE_START);
        this.add(mapView, BorderLayout.CENTER);
        this.add(territoryInfoView, BorderLayout.LINE_END);
        this.add(new PlayerBarView(riskModel), BorderLayout.PAGE_END);

    }

    public void welcomeScreen(){
        ImageIcon icon = new ImageIcon("src/Main/Resources/Risk.png");
        JLabel labelIcon = new JLabel(icon);
        JPanel panel = new JPanel(new GridBagLayout());
        panel.add(labelIcon);
        JLabel text = new JLabel("Welcome to Risk. Click OK to continue to game setup.");
        text.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        text.setFont(new Font("Ubuntu", Font.BOLD, 20));
        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.PAGE_AXIS));
        panel2.add(panel);
        panel2.add(text);
        JOptionPane startGame = new JOptionPane();
        startGame.showMessageDialog(this,panel2, "Welcome to Risk",JOptionPane.DEFAULT_OPTION);

    }

    public RiskModel getRiskModel(){
        return riskModel;
    }


    public static void main(String[] args) {
        new RiskView();
    }
}
