package Main.ActionBar;

import Main.RiskModel;
import Main.RiskView;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;

/**
 * This class is the view for the Action Bar component within the game of Risk.
 *
 * @Author Alex Cameron
 */
public class ActionBarView extends JPanel implements ActionBarListener {

    private JButton nextTurn;
    private JButton moveTroopsButton;
    private Image placeImg,nextTurnImg,attackImg, lock, cancel, backImg, tankImg;
    private final Color darkBlue = new Color(102,178,255);
    private final Color darkerBlue = new Color(93,182,240);
    private final Color lighterBlue = new Color(148,210,255);
    private final Border blackBorder = BorderFactory.createLineBorder(Color.BLACK,1);
    private final ActionBarController abc;
    private final RiskModel riskModel;
    private RiskView riskView;
    private JComboBox numberOfTroops,numberAttackTroops, numberMoveTroops;
    private JPanel deployPanel,attackPanel, messagePanel,troopPanel, fortifyTroopBar, actionPanel, fortifyInfo;
    private JLabel deployInfo, attackerTerritoryLabel, defenderTerritoryLabel, defenderPlayerLabel,currTerritoryLabel,moveTerritoryLabel,fortifyFurtherLabel ;
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
        if(fortifyInfo != null){
            fortifyInfo.setVisible(false);
        }
        if(fortifyTroopBar!=null){
            fortifyTroopBar.setVisible(false);
        }
        troopPanel = new JPanel();
        troopPanel.setBorder(blackBorder);
        JLabel troopMovement = new JLabel("Bonus Army Placement");
        troopMovement.setFont(new Font("Bonus Army Placement", Font.BOLD,20));
        troopPanel.setBackground(darkBlue);

        try {
            placeImg = ImageIO.read(getClass().getResourceAsStream("resources/Soldier.png")).getScaledInstance(20,20, Image.SCALE_DEFAULT);
        }catch(Exception ex){
        }

        JButton placeTroops = new JButton("Place Troops", new ImageIcon(placeImg));
        placeTroops.setBackground(lighterBlue);
        placeTroops.setFocusPainted(false);
        placeTroops.addActionListener(abc);
        placeTroops.setActionCommand("place");
        troopPanel.add(troopMovement);
        troopPanel.add(placeTroops);
        this.add(troopPanel);

    }

    /**
     * Initializes the panel which contains a label describing that the active player is in the attack phase (2/3).
     */
    public void initAttackPanel(){
        actionPanel = new JPanel();
        actionPanel.setBorder(blackBorder);
        this.add(actionPanel);
        JLabel actionLabel = new JLabel("Attack Phase:");
        actionLabel.setFont(new Font("Attack Phase:", Font.BOLD,20));
        actionLabel.setBackground(darkBlue);
        actionPanel.setBackground(darkBlue);
        actionPanel.add(actionLabel);
    }
    /**
     * Initializes the Buttons for the action panel containing advancing to troop movement phase (3/3)
     * and allows the user to perform attacks.
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
        JButton attack = new JButton("Attack", new ImageIcon(attackImg));
        attack.setBackground(lighterBlue);
        attack.setFocusPainted(false);
        JButton fortify = new JButton("Fortify Phase", new ImageIcon(tankImg));
        fortify.setBackground(lighterBlue);
        fortify.setFocusPainted(false);
        //adds the buttons to the JLabel
        actionPanel.add(attack);
        actionPanel.add(fortify);

        //adding Action Listeners
        attack.addActionListener(abc);
        attack.setActionCommand("attack");
        fortify.addActionListener(abc);
        fortify.setActionCommand("fortify");
    }

    /**
     * The label showing the user that it is troop movement phase (phase 3/3).
     */
    public void fortifyTroops(){
        actionPanel.setVisible(false);
        fortifyTroopBar = new JPanel();
        fortifyTroopBar.setBorder(blackBorder);
        JLabel troopFortify = new JLabel("Troop Movement Phase");
        troopFortify.setFont(new Font("Troop Movement Phase", Font.BOLD,20));
        fortifyTroopBar.add(troopFortify);
        fortifyTroopBar.setBackground(darkBlue);
        try {
            nextTurnImg = ImageIO.read(getClass().getResourceAsStream("resources/NextTurn.png")).getScaledInstance(20,20, Image.SCALE_DEFAULT);
        }catch(Exception ex){
        }
        fortifyTroopsInfo();
        nextTurn = new JButton("Next Turn", new ImageIcon(nextTurnImg));
        nextTurn.setBackground(lighterBlue);
        nextTurn.setFocusPainted(false);
        fortifyTroopBar.add(nextTurn);

        //adding actionlisteners
        nextTurn.addActionListener(abc);
        nextTurn.setActionCommand("next");

        this.add(fortifyTroopBar);
    }

    /**
     * This is the panel that contains the info regarding where the active player is moving troops from what territory
     * to another territory. There is specfications for how many troops desired to move as well.
     */
    public void fortifyTroopsInfo(){
        fortifyInfo = new JPanel();
        fortifyInfo.setBorder(blackBorder);
        currTerritoryLabel =new JLabel("Territory: ");
        JLabel numberAttackersLabel = new JLabel("Number of troops: ");
        moveTerritoryLabel = new JLabel("Move to Territory: ");
        //creates the JComboBox with number of attackers
        numberMoveTroops = new JComboBox<Integer>();
        numberMoveTroops.setBackground(lighterBlue);
        try {
            tankImg = ImageIO.read(getClass().getResourceAsStream("resources/Tank.png")).getScaledInstance(20,20, Image.SCALE_DEFAULT);
            lock = ImageIO.read(getClass().getResourceAsStream("resources/lock.png")).getScaledInstance(20,20, Image.SCALE_DEFAULT);
            cancel = ImageIO.read(getClass().getResourceAsStream("resources/cancel.png")).getScaledInstance(20,20, Image.SCALE_DEFAULT);
        }catch(Exception ex){
        }
        JButton moveTroopsButton = new JButton("Move Troops",new ImageIcon(tankImg));
        moveTroopsButton.setFocusPainted(false);
        moveTroopsButton.setBackground(lighterBlue);
        //Confirmation Buttons for choosing attack territory
        JPanel confirmButtons1 = new JPanel();
        JButton confirm1 = new JButton(new ImageIcon(lock));
        confirm1.setFocusPainted(false);
        confirm1.setBackground(darkerBlue);
        JButton cancel1 = new JButton(new ImageIcon(cancel));
        cancel1.setFocusPainted(false);
        cancel1.setBackground(darkerBlue);
        confirmButtons1.add(confirm1);
        confirmButtons1.add(cancel1);
        confirmButtons1.setBackground(darkBlue);
        //Confirmation Buttons for choosing defender territory
        JPanel confirmButtons2 = new JPanel();
        JButton confirm2 = new JButton(new ImageIcon(lock));
        confirm2.setFocusPainted(false);
        confirm2.setBackground(darkerBlue);
        JButton cancel2 = new JButton(new ImageIcon(cancel));
        cancel2.setFocusPainted(false);
        cancel2.setBackground(darkerBlue);
        confirmButtons2.add(confirm2);
        confirmButtons2.add(cancel2);
        confirmButtons2.setBackground(darkBlue);

        //add components into fortifyInfo
        fortifyInfo.add(currTerritoryLabel);
        fortifyInfo.add(numberAttackersLabel);
        fortifyInfo.add(numberMoveTroops
        );
        fortifyInfo.add(confirmButtons1);
        fortifyInfo.add(moveTerritoryLabel);
        fortifyInfo.add(confirmButtons2);
        fortifyInfo.add(moveTroopsButton);
        fortifyInfo.setBackground(darkBlue);
        fortifyTroopBar.add(fortifyInfo);

        //ActionListeners
        numberMoveTroops.addActionListener(abc);
        numberMoveTroops.setActionCommand("move");
        //Lock and cancel for current Territory selected to move troops from
        confirm1.addActionListener(abc);
        confirm1.setActionCommand("lockMove1");
        cancel1.addActionListener(abc);
        cancel1.setActionCommand("cancelMove1");
        //Lock and cancel for Territory the troops will be moved to.
        confirm2.addActionListener(abc);
        confirm2.setActionCommand("lockMove2");
        cancel2.addActionListener(abc);
        cancel2.setActionCommand("cancelMove2");
        //Move troops button
        moveTroopsButton.addActionListener(abc);
        moveTroopsButton.setActionCommand("moveTroops");
    }

    /**
     * Once a player has moved troops in a turn, they can only move troops from the territory they moved the troops from
     * and this method adds a double check implementation to see if user wants to move even more troops before passing turns.
     */
    public void fortifyMoreTroops(){
        fortifyInfo.setVisible(false);
        fortifyTroopBar.setVisible(false);
        fortifyInfo = new JPanel();
        fortifyInfo.setBorder(blackBorder);
        JLabel troopFortify = new JLabel("Troop Movement Phase");
        troopFortify.setFont(new Font("Troop Movement Phase", Font.BOLD,20));
        fortifyInfo.add(troopFortify);
        fortifyInfo.setBackground(darkBlue);
        fortifyFurtherLabel = new JLabel("Do you want to send more troops to " + abc.getMoveTerritory().getName() + " from " + abc.getCurrTerritory() +"?");
        moveTroopsButton = new JButton("Move Troops",new ImageIcon(tankImg));
        moveTroopsButton.setBackground(lighterBlue);
        moveTroopsButton.setFocusPainted(false);
        fortifyInfo.add(fortifyFurtherLabel);
        fortifyInfo.add(numberMoveTroops);
        fortifyInfo.add(moveTroopsButton);
        fortifyInfo.add(nextTurn);
        this.add(fortifyInfo);

        //adding actionlistener
        numberMoveTroops.addActionListener(abc);
        numberMoveTroops.setActionCommand("move");

        moveTroopsButton.addActionListener(abc);
        moveTroopsButton.setActionCommand("moveTroops");

    }

    /**
     * Used for when there are no possible troops left to move.
     */
    public void removeTroopsLabel(){
        fortifyInfo.remove(fortifyFurtherLabel);
        fortifyInfo.remove(numberMoveTroops);
        fortifyInfo.remove(moveTroopsButton);
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
        attackPanel.setBorder(blackBorder);
        JPanel defenderPanel = new JPanel();
        defenderPanel.setLayout(new BoxLayout(defenderPanel,BoxLayout.Y_AXIS));
        defenderPanel.setBackground(darkBlue);

        attackerTerritoryLabel =new JLabel("Attacker Territory: ");
        JLabel numberAttackersLabel = new JLabel("Number of Attackers: ");
        defenderTerritoryLabel = new JLabel("Defender Territory: ");
        defenderPlayerLabel = new JLabel("Defending Player: ");
        //creates the JComboBox with number of attackers
        numberAttackTroops = new JComboBox<Integer>();
        numberAttackTroops.setBackground(lighterBlue);
        JButton attackButton = new JButton("Attack",new ImageIcon(attackImg));
        attackButton.setBackground(lighterBlue);
        attackButton.setFocusPainted(false);
        //gets images
        try {
            backImg = ImageIO.read(getClass().getResourceAsStream("resources/back.png")).getScaledInstance(20,20, Image.SCALE_DEFAULT);
            lock = ImageIO.read(getClass().getResourceAsStream("resources/lock.png")).getScaledInstance(20,20, Image.SCALE_DEFAULT);
            cancel = ImageIO.read(getClass().getResourceAsStream("resources/cancel.png")).getScaledInstance(20,20, Image.SCALE_DEFAULT);
        }catch(Exception ex){
        }
        //creates back, lock and cancel buttons

        JButton backButton = new JButton(new ImageIcon(backImg));
        backButton.setFocusPainted(false);
        backButton.setBackground(darkerBlue);
        //Confirmation Buttons for choosing attack territory
        JPanel confirmButtonsA = new JPanel();
        JButton confirmA = new JButton(new ImageIcon(lock));
        confirmA.setFocusPainted(false);
        confirmA.setBackground(darkerBlue);
        JButton cancelA = new JButton(new ImageIcon(cancel));
        cancelA.setFocusPainted(false);
        cancelA.setBackground(darkerBlue);
        confirmButtonsA.add(confirmA);
        confirmButtonsA.add(cancelA);
        confirmButtonsA.setBackground(darkBlue);
        //Confirmation Buttons for choosing defender territory
        JPanel confirmButtonsD = new JPanel();
        JButton confirmD = new JButton(new ImageIcon(lock));
        confirmD.setFocusPainted(false);
        confirmD.setBackground(darkerBlue);
        JButton cancelD = new JButton(new ImageIcon(cancel));
        cancelD.setFocusPainted(false);
        cancelD.setBackground(darkerBlue);
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
        deployPanel.setBorder(blackBorder);
        deployInfo = new JLabel("Territory:    Reinforcements:");
        numberOfTroops = new JComboBox<Integer>();
        numberOfTroops.setBackground(lighterBlue);
        JButton deployButton = new JButton("Deploy Troops");
        deployButton.setFocusPainted(false);
        deployButton.setBackground(lighterBlue);
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
        backButton.setFocusPainted(false);
        backButton.setBackground(darkerBlue);
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
        messagePanel.setBorder(blackBorder);
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

    public JComboBox<Integer> getMoveNumberOfTroops(){
        return numberMoveTroops;
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
        defenderTerritoryLabel.setText("Defender Territory: " + abc.getDefenderTerritory());
        defenderPlayerLabel.setText("Defending Player: " + abc.getDefenderTerritory().getOwner().getName());
    }else{
        defenderTerritoryLabel.setText("Defender Territory: ");
        }
    }

    /**
     * Sets the current territory info in the troop movement phase bar. This method is used to change the label whenever a territory is clicked.
     */
    public void setCurrTerrInfo(){
        if(abc.getCurrTerritory()!=null){
            currTerritoryLabel.setText("Territory: " + abc.getCurrTerritory());
        }else{
            currTerritoryLabel.setText("Territory: ");
        }
    }
    /**
     * Sets the territory info in the troop movement phase bar for the territory which troops will be moved to.
     * This method is used to change the label whenever a territory is clicked.
     */
    public void setMoveTerrInfo(){
        if(abc.getMoveTerritory()!= null){
            moveTerritoryLabel.setText("Move to Territory: " + abc.getMoveTerritory());
        }else{
            moveTerritoryLabel.setText("Move to Territory: " );
        }
    }

    /**
     * Each time a new territory is selected the JComboBox has to be adjusted to show the number of troops available to move.
     */
    public void setNumberMoveTroopsRange(){
        numberMoveTroops.removeAllItems();
        for (int i = 1; i < abc.getCurrTerritory().getSoldiers(); i++) {
            numberMoveTroops.addItem(i);
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
        if(e.isTurnComplete()){ //When troop movement is finished and the remaining soldiers to place is 0 then the action buttons will appear in actionbar
            troopPanel.setVisible(false);
            initActionButtons();
        }
    }
}
