package Main;

import Main.ActionBar.*;
import Main.IntializeFrame.InitializeView;
import Main.Map.*;
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
    private JFrame welcomeScreen, initializeFrame;
    private RiskModel riskModel;
    private TerritoryInfoView territoryInfoView;
    private NotificationView notificationView;
    private MapView mapView;
    private RiskController rc;
    private InitializeView initializeGame;

    /**
     * Initializes the JFrame containing all the different components.
     */
    RiskView(){

        super("Risk");
        this.riskModel = new RiskModel();

        rc = new RiskController(riskModel,this);
        //displays welcome screen
        welcomeScreen();
        //displays initial setup
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

        welcomeScreen = new JFrame("Risk Setup");

        ImageIcon riskPic = null;
        try {
            riskPic = new ImageIcon(ImageIO.read(getClass().getResourceAsStream("resources/Risk.png")));
        } catch (Exception e){

        }

        JLabel labelIcon = new JLabel(riskPic);
        JPanel panel = new JPanel(new GridBagLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel,BoxLayout.PAGE_AXIS));
        panel.add(labelIcon);
        JLabel title = new JLabel("SYSC3110: Final Project");
        JLabel text = new JLabel("The Game of Risk");
        title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        title.setFont(new Font("Ubuntu", Font.BOLD, 20));
        text.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        text.setFont(new Font("Ubuntu", Font.BOLD, 20));

        JButton loadGame = new JButton("Load Game");
        loadGame.setFocusPainted(false);
        loadGame.setBackground(Color.white);
        JButton newGame = new JButton("New Game");
        newGame.setBackground(Color.white);
        newGame.setFocusPainted(false);
        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.PAGE_AXIS));
        textPanel.add(title);
        textPanel.add(text);

        panel2.add(textPanel);
        panel2.add(panel);
        buttonPanel.add(newGame);
        buttonPanel.add(loadGame);
        panel2.add(buttonPanel);

        welcomeScreen.setBackground(Color.LIGHT_GRAY);
        welcomeScreen.add(panel2);
        welcomeScreen.pack();
        welcomeScreen.setLocationRelativeTo(null);
        welcomeScreen.setVisible(true);
        welcomeScreen.setSize(500,300);
        welcomeScreen.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //adding actionlisteners
        loadGame.addActionListener(rc);
        loadGame.setActionCommand("load");

        newGame.addActionListener(rc);
        newGame.setActionCommand("newGame");
    }

    /**
     *
     */
    public void setupInit(){
        welcomeScreen.dispose();

        initializeFrame = new JFrame("Initialize Game");
        initializeFrame.setLayout(new BorderLayout());
        initializeGame = new InitializeView();

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton startGame = new JButton("Continue to Game");
        startGame.setFocusPainted(false);
        startGame.setBackground(Color.white);
        JButton cancel = new JButton("Cancel");
        cancel.setBackground(Color.white);
        cancel.setFocusPainted(false);

        buttonPanel.add(startGame);
        buttonPanel.add(cancel);

        //adding actionlisteners
        startGame.addActionListener(rc);
        startGame.setActionCommand("start");
        cancel.addActionListener(rc);
        cancel.setActionCommand("cancel");
        initializeFrame.add(initializeGame, BorderLayout.CENTER);
        initializeFrame.add(buttonPanel,BorderLayout.PAGE_END);

        initializeFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        initializeFrame.setSize(400,400);
        initializeFrame.pack();
        initializeFrame.setLocationRelativeTo(null);
        initializeFrame.setVisible(true);

    }

    /**
     *
     */
    public void setupPlayers(){
        ArrayList<String> nameOfPlayers = initializeGame.getNameOfPlayers();
        ArrayList<Color> coloursOfPlayers = initializeGame.getPlayersColour();
        ArrayList<Boolean> isPlayerAI = initializeGame.getIsPlayerAI();

        for(int i = 0; i < initializeGame.getNumberOfPlayers(); i++){
            if(!isPlayerAI.get(i)){
                riskModel.addPlayer(new Player(nameOfPlayers.get(i),coloursOfPlayers.get(i), this));
            } else {
                riskModel.addPlayer(new AIEasy(nameOfPlayers.get(i),coloursOfPlayers.get(i), this));
            }
        }
        riskModel.loadMap(initializeGame.getMapPath());
        riskModel.setCurrentMap(initializeGame.getMapName());
    }

    public void setupView(){
        initializeFrame.dispose();
        this.notificationView = new NotificationView();
        riskModel.play();

        this.setLayout(new BorderLayout());

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setJMenuBar(new MenuBarView(riskModel,this));
        //creates map listeners for territoryinfoview and actionbarview
        this.mapView = new MapView(this.riskModel);
        territoryInfoView = new TerritoryInfoView(riskModel);
        mapView.getMapModel().addMapListener(territoryInfoView);
        ActionBarView actionBarView = new ActionBarView(this, riskModel);

        mapView.getMapModel().addMapListener(actionBarView.getAbc());

        PlayerBarView playerBarView = new PlayerBarView(riskModel);
        riskModel.addRiskViewListeners(playerBarView);
        riskModel.addRiskViewListeners(this);

        //adds the different views to this component
        // Action Bar View

        this.add(actionBarView, BorderLayout.PAGE_START);

        // Map View

        this.add(mapView, BorderLayout.CENTER);

        // Territory Info View
        JSplitPane splitPane = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                territoryInfoView,
                notificationView);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(500);

        this.add(splitPane, BorderLayout.LINE_END);

        // Player Bar View

        this.add(playerBarView, BorderLayout.PAGE_END);

        this.setMinimumSize(new Dimension(1250,800));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
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
    public MapView getMapView(){
        return mapView;
    }
    public void disposeSetup(){
        initializeFrame.dispose();
    }
    @Override
    public void handleTurnUpdate(RiskEvent e) {
        this.mapView.handleMapUpdate(new MapRedrawEvent(this));
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
