package Main.ActionBar;

import Main.RiskModel;
import Main.RiskView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * This class is the view for the Action Bar component within the game of Risk.
 *
 * @Author Alex Cameron
 */
public class ActionBarView extends JPanel implements ActionBarListener {

    private JButton placeTroops,attack,nextTurn,fortify,deployButton;
    private Image placeImg,nextTurnImg,attackImg, lock, cancel, backImg, tankImg;
    private Color darkBlue = new Color(102,178,255);
    private ActionBarController abc;
    private RiskModel riskModel;
    private RiskView riskView;
    private JComboBox numberOfTroops,numberAttackTroops;
    private JPanel deployPanel,attackPanel, messagePanel,troopPanel, fortifyTroopBar, actionPanel;
    private JLabel deployInfo;
    private JLabel attackerTerritoryLabel;
    private JLabel defenderTerritoryLabel;
    private JLabel defenderPlayerLabel;
    private boolean placetroopsFlag;
    private boolean attackFlag;
    private boolean messageFlag;

    /**
     * The Constructor which gets the current Risk view and Risk model and initializes the action bar panel.
     * @param riskView The risk view
     * @param riskModel The risk model
     */
    public ActionBarView(RiskView riskView, RiskModel riskModel){
        this.riskView = riskView;
        this.riskModel = riskModel;
        // JPanel Config
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBackground(RiskModel.BACKGROUND);

        //adds this view to the model listeners
        ActionBarModel abm = new ActionBarModel(this.riskModel,this.riskView);
        abm.addActionBarModelViews(this);
        abc = new ActionBarController(this, abm);
        //initializes the actionbar / buttons
        initTroopMovement();

    }

    /**
     * This method sets up the view for the troop movement phase which is phase 1/3.
     */
    public void initTroopMovement(){
        if(fortifyTroopBar != null){
            fortifyTroopBar.setVisible(false);
        }
        troopPanel = new JPanel();
        JLabel troopMovement = new JLabel("Bonus Army Placement");
        troopMovement.setFont(new Font("Bonus Army Placement", Font.PLAIN,20));
        troopPanel.setBackground(darkBlue);

        try {
            placeImg = ImageIO.read(getClass().getResourceAsStream("resources/Soldier.png")).getScaledInstance(20,20, Image.SCALE_DEFAULT);
        }catch(Exception ex){
        }

        placeTroops = new JButton("Place Troops", new ImageIcon(placeImg));
        placeTroops.setBackground(darkBlue);

        placeTroops.addActionListener(abc);
        placeTroops.setActionCommand("place");
        troopPanel.add(troopMovement);
        troopPanel.add(placeTroops);
        this.add(troopPanel);

    }
    public void initAttackPanel(){
        actionPanel = new JPanel();
        this.add(actionPanel);
        JLabel actionLabel = new JLabel("Attack Phase:");
        actionLabel.setFont(new Font("Attack Phase:", Font.PLAIN,20));
        actionLabel.setBackground(darkBlue);
        actionPanel.setBackground(darkBlue);
        actionPanel.add(actionLabel);
    }
    /**
     * Initializes the Buttons for the action panel
     */
    public void initActionButtons(){
        initAttackPanel();
        //gets the images
        try {
            tankImg = ImageIO.read(getClass().getResourceAsStream("resources/Tank.png")).getScaledInstance(20,20, Image.SCALE_DEFAULT);
            attackImg = ImageIO.read(getClass().getResourceAsStream("resources/Attack.png")).getScaledInstance(20,20, Image.SCALE_DEFAULT);
        }catch(Exception ex){
        }
        //creates the buttons
        attack = new JButton("Attack", new ImageIcon(attackImg));
        attack.setBackground(darkBlue);
        fortify = new JButton("Fortify Phase", new ImageIcon(tankImg));
        fortify.setBackground(darkBlue);
        //adds the buttons to the JLabel
        actionPanel.add(attack);
        actionPanel.add(fortify);

        //adding Action Listeners
        attack.addActionListener(abc);
        attack.setActionCommand("attack");
        fortify.addActionListener(abc);
        fortify.setActionCommand("fortify");
    }
    public void fortifyTroops(){
        actionPanel.setVisible(false);
        fortifyTroopBar = new JPanel();
        JLabel troopFortify = new JLabel("Troop Movement Phase");
        troopFortify.setFont(new Font("Troop Movement Phase", Font.PLAIN,20));
        fortifyTroopBar.add(troopFortify);
        fortifyTroopBar.setBackground(darkBlue);
        try {
            nextTurnImg = ImageIO.read(getClass().getResourceAsStream("resources/NextTurn.png")).getScaledInstance(20,20, Image.SCALE_DEFAULT);
        }catch(Exception ex){
        }

        nextTurn = new JButton("Next Turn", new ImageIcon(nextTurnImg));
        nextTurn.setBackground(darkBlue);
        fortifyTroopBar.add(nextTurn);

        //ActionListeners
        nextTurn.addActionListener(abc);
        nextTurn.setActionCommand("next");
        this.add(fortifyTroopBar);
    }
    public void fortifyTroopsInfo(){
        JPanel fortifyInfo = new JPanel();

    }
    /**
     * Adds the number of possible troops to send in an attack
     */
    public void setAttackNumberRange(){
        if(messageFlag){
            removeMessageBar();
        }
        numberAttackTroops.removeAllItems();
        if(abc.getAttackerTerritory()!=null) {
            if (abc.getAttackerTerritory().getSoldiers() == 1) {
               setMessage("You cannot attack with this territory. Your territory must be occupied at all times.");
            } else {
                for (int i = 1; i < abc.getAttackerTerritory().getSoldiers(); i++) {
                    numberAttackTroops.addItem(i);
                }
            }
        }
    }

    /**
     * Displays the info in the current attack in a JPanel
     */
    public void attackInfo(){
        //removes placetroops bar
        if(getPlaceTroopsFlag()){
            removeDeployTroopsBar();
        }
        //create attack and defend panels which display the attacker and defender info
        attackFlag = true;
        attackPanel = new JPanel();
        JPanel defenderPanel = new JPanel();
        defenderPanel.setLayout(new BoxLayout(defenderPanel,BoxLayout.Y_AXIS));
        defenderPanel.setBackground(darkBlue);

        attackerTerritoryLabel =new JLabel("Attacker Territory: ");
        JLabel numberAttackersLabel = new JLabel("Number of Attackers: ");
        defenderTerritoryLabel = new JLabel("Defender Territory: ");
        defenderPlayerLabel = new JLabel("Defending Player: ");
        //creates the JComboBox with number of attackers
        numberAttackTroops = new JComboBox<Integer>();
        JButton attackButton = new JButton("Attack",new ImageIcon(attackImg));
        //gets images
        try {
            backImg = ImageIO.read(getClass().getResourceAsStream("resources/back.png")).getScaledInstance(20,20, Image.SCALE_DEFAULT);
            lock = ImageIO.read(getClass().getResourceAsStream("resources/lock.png")).getScaledInstance(20,20, Image.SCALE_DEFAULT);
            cancel = ImageIO.read(getClass().getResourceAsStream("resources/cancel.png")).getScaledInstance(20,20, Image.SCALE_DEFAULT);
        }catch(Exception ex){
        }
        //creates back, lock and cancel buttons

        JButton backButton = new JButton(new ImageIcon(backImg));

        //Confirmation Buttons for choosing attack territory
        JPanel confirmButtonsA = new JPanel();
        JButton confirmA = new JButton(new ImageIcon(lock));
        JButton cancelA = new JButton(new ImageIcon(cancel));
        confirmButtonsA.add(confirmA);
        confirmButtonsA.add(cancelA);
        confirmButtonsA.setBackground(darkBlue);
        //Confirmation Buttons for choosing defender territory
        JPanel confirmButtonsD = new JPanel();
        JButton confirmD = new JButton(new ImageIcon(lock));
        JButton cancelD = new JButton(new ImageIcon(cancel));
        confirmButtonsD.add(confirmD);
        confirmButtonsD.add(cancelD);
        confirmButtonsD.setBackground(darkBlue);

        //adds defender labels to defenderPanel
        defenderPanel.add(defenderTerritoryLabel);
        defenderPanel.add(defenderPlayerLabel);

        //Adds all components into the attackPanel
        attackPanel.add(attackerTerritoryLabel);
        attackPanel.add(numberAttackersLabel);
        attackPanel.add(numberAttackTroops);
        attackPanel.add(confirmButtonsA);
        attackPanel.add(defenderPanel);
        attackPanel.add(confirmButtonsD);
        attackPanel.add(attackButton);
        attackPanel.add(backButton);
        attackPanel.setBackground(darkBlue);
        this.add(attackPanel);

        //Create actionlisteners
        numberAttackTroops.addActionListener(abc);
        numberAttackTroops.setActionCommand("attackTroops");
        attackButton.addActionListener(abc);
        attackButton.setActionCommand("attackButton");
        backButton.addActionListener(abc);
        backButton.setActionCommand("backAttack");

        confirmA.addActionListener(abc);
        confirmA.setActionCommand("confirmAttack");
        cancelA.addActionListener(abc);
        cancelA.setActionCommand("cancelAttack");

        confirmD.addActionListener(abc);
        confirmD.setActionCommand("confirmDefend");
        cancelD.addActionListener(abc);
        cancelD.setActionCommand("cancelDefend");

        updateUI();
    }

    /**
     * This method displays a Jpanel with the information regarding deploying troops
     */
    public void deployTroopsInfo(){
        placetroopsFlag = true;
        deployPanel = new JPanel();
        deployInfo = new JLabel("Territory:    Reinforcements:");
        numberOfTroops = new JComboBox<Integer>();
        deployButton= new JButton("Deploy Troops");
        deployButton.setEnabled(true);
        //adds the total possible troops to deploy to the JComboBox
        if(riskModel.getActivePlayer().getReinforcements()>0) {
            for (int i = 1; i <= riskModel.getActivePlayer().getReinforcements(); i++) {
                numberOfTroops.addItem(i);
            }
        }else{ //if the total reinforcement is 0 then the deploy troops button is disabled
            deployButton.setEnabled(false);
            numberOfTroops.setEnabled(false);
        }
        //gets images
        try {
            backImg = ImageIO.read(getClass().getResourceAsStream("resources/back.png")).getScaledInstance(20,20, Image.SCALE_DEFAULT);
        }catch(IOException ex){
        }
        //creates back button
        JButton backButton = new JButton(new ImageIcon(backImg));
        //adding information to the deploy troops panel
        deployPanel.add(deployInfo);
        deployPanel.add(numberOfTroops);
        deployPanel.add(deployButton);
        deployPanel.add(backButton);
        deployPanel.setBackground(darkBlue);
        this.add(deployPanel);

        //adding Actionlisteners to components
        numberOfTroops.addActionListener(abc);
        numberOfTroops.setActionCommand("numTroops");
        deployButton.addActionListener(abc);
        deployButton.setActionCommand("deploy");
        backButton.addActionListener(abc);
        backButton.setActionCommand("backDeploy");
        updateUI();
    }

    /**
     * Sets the message to be displayed in the ActionBar
     * @param msg the message displayed in Message bar
     */
    public void setMessage(String msg){
        messageFlag =true;
        messagePanel = new JPanel();
        JLabel message = new JLabel(msg);
        messagePanel.add(message);
        messagePanel.setBackground(darkBlue);
        this.add(messagePanel);
        updateUI();
    }

    /**
     * Removes the deploy troops bar from the GUI
     */
    public void removeDeployTroopsBar() {
        if (deployPanel != null) {
            deployPanel.setVisible(false);
            placetroopsFlag = false;
        }
    }

    /**
     * Removes the attack bar from the GUI
     */
    public void removeAttackBar(){
        if(attackPanel!=null){
            attackPanel.setVisible(false);
            attackFlag = false;
        }
    }

    /**
     * Removes the recent message bar from the GUI
     */
    public void removeMessageBar(){
        if(messagePanel !=null){
            messagePanel.setVisible(false);
            messageFlag = false;
        }
    }

    /**
     * Gets the number of troops allowed to be deployed
     * @return The JComboBox of the number of troops allowed to be deployed
     */
    public JComboBox<Integer> getNumberOfTroops(){
        return numberOfTroops;
    }

    /**
     * Gets the number of troops allowed to attack with in a certain territory
     * @return The JComboBox of the number of troops allowed to attack
     */
    public JComboBox<Integer> getAttackNumberOfTroops(){
        return numberAttackTroops;
    }

    /**
     * Gets the action bar controller which controls the view and the model
     * @return The action bar controller
     */
    public ActionBarController getAbc(){
        return abc;
    }

    /**
     * Sets the territories that are clicked in the deploy info bar in the GUI
     */
    public void setDeployInfo(){
        deployInfo.setText("Territory: "+ abc.getTerritory() + "   Reinforcements: ");
    }

    /**
     * Sets the attacker territory that was clicked in the attack info bar in the GUI
     */
    public void setAttackerInfo(){
        if(abc.getAttackerTerritory()!=null) {
            attackerTerritoryLabel.setText("Attacker Territory: " + abc.getAttackerTerritory());
        }else{
            attackerTerritoryLabel.setText("Attacker Territory: ");
        }
    }

    /**
     * Sets the defender territory and the defender that was clicked in the attack info bar in the GUI
     */
    public void setDefenderInfo(){
        if(abc.getDefenderTerritory()!=null) {
        defenderTerritoryLabel.setText("Defender Territory:" + abc.getDefenderTerritory());
        defenderPlayerLabel.setText("Defending Player: " + abc.getDefenderTerritory().getOwner().getName());
    }else{
        defenderTerritoryLabel.setText("Defender Territory: ");
        }
    }
    public RiskView getRiskView(){
        return riskView;
    }
    /**
     * Gets the boolean value if the place troops info bar is visible or not
     * @return True if the bar is visible, false otherwise
     */
    public boolean getPlaceTroopsFlag(){
        return placetroopsFlag;
    }
    /**
     * Gets the boolean value if the attack info bar is visible or not
     * @return True if the bar is visible, false otherwise
     */
    public boolean getAttackFlag(){
        return attackFlag;
    }
    /**
     * Gets the boolean value if the message bar is visible or not
     * @return True if the bar is visible, false otherwise
     */
    public boolean getMessageFlag(){
        return messageFlag;
    }

    @Override
    public void handleTroopDeployment(ActionBarEvent e){
        if(e.isTurnComplete()){
            troopPanel.setVisible(false);
            initActionButtons();
        }
    }
}
