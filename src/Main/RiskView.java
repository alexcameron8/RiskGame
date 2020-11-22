package Main;

import Main.ActionBar.*;
import Main.IntializeFrame.InitializeView;
import Main.Map.*;
import Main.NotificationView.NotificationEvent;
import Main.NotificationView.NotificationModel;
import Main.NotificationView.NotificationView;
import Main.PlayerBar.*;
import Player.AI.AIEasy;
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
    private NotificationView notificationView;

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
        ArrayList<Boolean> isPlayerAI = initializeGame.getIsPlayerAI();

        for(int i = 0; i < initializeGame.getNumberOfPlayers(); i++){
            if(!isPlayerAI.get(i)){
                riskModel.addPlayer(new Player(nameOfPlayers.get(i),coloursOfPlayers.get(i)));
            } else {
                riskModel.addPlayer(new AIEasy(nameOfPlayers.get(i),coloursOfPlayers.get(i)));
            }

        }
        riskModel.play();

        this.setLayout(new GridBagLayout());

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

        this.notificationView = new NotificationView();

        //adds the different views to this component
        // Action Bar View
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weighty = 0.1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(actionBarView, gbc);

        // Map View
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 2;
        gbc.weighty = 0.8;
        gbc.weightx = 0.7;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(mapView, gbc);

        // Territory Info View
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        gbc.weighty = 0.4;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(territoryInfoView, gbc);

        // Notification View
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(notificationView, gbc);

        // Player Bar View
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weighty = 0.1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(playerBarView, gbc);

        this.setMinimumSize(new Dimension(1250,800));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);

        for(int i = 1; i < 20; i++){
            if(i%2==0){
                this.notificationView.notifyUser("Event that goes way too long and takes up way too much space. This should probably be split." + i, NotificationModel.NotificationType.INFO);
            } else {
                this.notificationView.notifyUser("Event " + i, NotificationModel.NotificationType.WARNING);
            }

        }

    }

    /**
     * Creates a new view of risk. This method is used when a new risk game is created.
     * @param riskModel The risk model
     */
    public void newRiskView(RiskModel riskModel){
        this.riskModel = riskModel;
        new RiskView();
    }

    public NotificationView getNotificationView() {
        return notificationView;
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

    public TerritoryInfoView getTerritoryInfoView(){
        return territoryInfoView;
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
