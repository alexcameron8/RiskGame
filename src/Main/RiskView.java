package Main;

import Main.ActionBar.*;
import Main.IntializeFrame.InitializeView;
import Main.PlayerBar.*;
import Player.Player;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

public class RiskView extends JFrame implements RiskViewListener{
    private RiskModel riskModel;
    private RiskController riskController;

    RiskView(){
        super("Risk");
        this.riskModel = new RiskModel();
        this.riskController = new RiskController(riskModel,this);
        welcomeScreen();


        InitializeView initializeGame = new InitializeView();
        JOptionPane.showConfirmDialog(this, initializeGame, "Initialize Game ", JOptionPane.OK_CANCEL_OPTION);
        ArrayList<String> nameOfPlayers = initializeGame.getNameOfPlayers();
        ArrayList<Color> coloursOfPlayers = initializeGame.getPlayersColour();

        for(int i = 0; i < initializeGame.getNumberOfPlayers(); i++){
            riskModel.addPlayer(new Player(nameOfPlayers.get(i),coloursOfPlayers.get(i)));
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
        ActionBarView actionBarView = new ActionBarView(this, riskModel);

        mapView.getMapModel().addMapListener(actionBarView.getAbc());

        this.add(actionBarView, BorderLayout.PAGE_START);
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

    @Override
    public void handleTurnUpdate(RiskEvent e) {

    }
}
