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

        this.setMinimumSize(new Dimension(1250,800));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setJMenuBar(new MenuBarView(riskModel,this));

        MapView mapView = new MapView(this.riskModel);
        TerritoryInfoView territoryInfoView = new TerritoryInfoView(riskModel);
        mapView.getMapModel().addMapListener(territoryInfoView);
        ActionBarView actionBarView = new ActionBarView(this, riskModel);

        mapView.getMapModel().addMapListener(actionBarView.getAbc());

        PlayerBarView playerBarView = new PlayerBarView(riskModel);
        riskModel.addRiskViewListeners(playerBarView);


        this.add(actionBarView, BorderLayout.PAGE_START);
        this.add(mapView, BorderLayout.CENTER);
        this.add(territoryInfoView, BorderLayout.LINE_END);
        this.add(playerBarView, BorderLayout.PAGE_END);
        this.setVisible(true);

    }
    public void newRiskView(RiskModel riskModel){
        this.riskModel = riskModel;
        new RiskView();
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
        if(e.getWinner()!=null){
            JOptionPane.showMessageDialog(this,getRiskModel().getActivePlayer().getName() + " has Won. You will now be returned to the Main Menu.");
            getRiskModel().newGame();
        }
        if(e.getEliminatedPlayer()!=null){
            System.out.println("eliminated JOption");
            JOptionPane.showMessageDialog(this,e.getEliminatedPlayer().getName()+ " has been eliminated!");
        }
    }
}
