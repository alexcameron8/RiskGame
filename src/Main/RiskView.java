package Main;

import Main.ActionBar.*;
import Main.PlayerBar.*;
import Player.Player;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class RiskView extends JFrame implements RiskViewListener{
    private RiskModel riskModel;
    private RiskController riskController;
    private final Integer[] numSetupPlayers = {null,2,3,4,5,6};
    private JComboBox<Integer> playerBox;
    private int numofPlayers;

    RiskView(){
        this.riskModel = new RiskModel();
        this.riskController = new RiskController(riskModel,this);
        welcomeScreen();
        initSetup();
        if(numofPlayers>1 || numofPlayers < 7) {
            setupPlayers(numofPlayers);
        }else{
            initSetup();
        }
        this.setSize(new Dimension(1000, 800));

        riskModel.addRiskView(this);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setJMenuBar(new MenuBarView());

        MapView mapView = new MapView();
        TerritoryInfoView territoryInfoView = new TerritoryInfoView(riskModel);
        mapView.getMapModel().addMapListener(territoryInfoView);

        ActionBarViewListener actionBarViewListener = new ActionBarViewListener(riskModel);
        mapView.getMapModel().addMapListener(actionBarViewListener);

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
    public void initSetup(){

        //Setup panel
        JPanel setupScreen = new JPanel();
        setupScreen.setLayout(new BorderLayout());

        //Total possible options for number of players
        playerBox = new JComboBox<>(numSetupPlayers);
        TitledBorder comboBoxTitle = BorderFactory.createTitledBorder("Select number of Players:");
        playerBox.setBorder(comboBoxTitle);

        //adding to panel
        setupScreen.add(playerBox);

        //ActionListener
        playerBox.addActionListener(riskController);
        playerBox.setActionCommand("numPlayers");

        JOptionPane selectPlayers = new JOptionPane();
        selectPlayers.showMessageDialog(this,setupScreen,"Select Number of Players.", JOptionPane.DEFAULT_OPTION );

    }

    public void setNumOfPlayers(int numOfPlayers){
        this.numofPlayers=numOfPlayers;
    }

    public void setupPlayers(int numOfPlayers){
        for(int i=0;i<numOfPlayers;i++){
            String s= "Enter player name (" + (i+1) + "/" + numOfPlayers + ")";

            String name = "";
            while(name.isEmpty()){
                name =JOptionPane.showInputDialog(this,s);
            }
            riskModel.addPlayer( new Player(name));
        }
        //sets GUI visible
        this.setVisible(true);
    }
    public RiskModel getRiskModel(){
        return riskModel;
    }



    public JComboBox<Integer> getPlayerBox(){
        return playerBox;
    }

    @Override
    public void handleTurnUpdate(RiskEvent event){
        Turn currentTurn = event.getCurrentTurn();
    }

    public static void main(String[] args) {
        new RiskView();
    }
}
