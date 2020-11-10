package Main;

import Main.ActionBar.*;
import Main.IntializeFrame.InitializeView;
import Main.Map.*;
import Main.PlayerBar.*;
import Player.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * RiskView class which is the swing components creating a GUI representing the risk game from risk model
 */
public class RiskView extends JFrame implements RiskViewListener{
    private RiskModel riskModel;
    private TerritoryInfoView territoryInfoView;

    /**
     * Initializes the JFrame containing all the different components.
     */
    RiskView(){

        super("Risk");
        this.riskModel = new RiskModel();
        //displays welcome screen
        welcomeScreen();
        //displays initial setup
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
        //creates map listeners for territoryinfoview and actionbarview
        MapView mapView = new MapView(this.riskModel);
        territoryInfoView = new TerritoryInfoView(riskModel);
        mapView.getMapModel().addMapListener(territoryInfoView);
        ActionBarView actionBarView = new ActionBarView(this, riskModel);

        mapView.getMapModel().addMapListener(actionBarView.getAbc());

        PlayerBarView playerBarView = new PlayerBarView(riskModel);
        riskModel.addRiskViewListeners(playerBarView);
        riskModel.addRiskViewListeners(this);

        //adds the different views to this component
        this.add(actionBarView, BorderLayout.PAGE_START);
        this.add(mapView, BorderLayout.CENTER);
        this.add(territoryInfoView, BorderLayout.LINE_END);
        this.add(playerBarView, BorderLayout.PAGE_END);
        this.setVisible(true);

    }

    /**
     * Creates a new view of risk. This method is used when a new risk game is created.
     * @param riskModel The risk model
     */
    public void newRiskView(RiskModel riskModel){
        this.riskModel = riskModel;
        new RiskView();
    }

    /**
     * Initializes the welcome screen frame that opens upon new game start.
     */
    public void welcomeScreen(){
        ImageIcon icon = null;
        try {
            icon = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("resources/Risk.png")));
        } catch (Exception e){

        }

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

    /**
     * gets the current risk model
     * @return risk model
     */
    public RiskModel getRiskModel(){
        return riskModel;
    }

    @Override
    public void handleTurnUpdate(RiskEvent e) {
        //if there is a winner then start new game and display that there is a winner
        if(e.getWinner()!=null){
            JOptionPane.showMessageDialog(this,getRiskModel().getActivePlayer().getName() + " has Won. You will now be returned to the Main Menu.");
            this.dispose();
            RiskModel newGame = riskModel.newGame();
            this.newRiskView(newGame);
        }//If a player is eliminated then notify user there is an eliminated player
        if(e.getEliminatedPlayer()!=null){
            JOptionPane.showMessageDialog(this,e.getEliminatedPlayer().getName()+ " has been eliminated!");
        }
    }

    public static void main(String[] args) {
        new RiskView();
    }
}
