package Main.ActionBar;

import Main.RiskModel;
import Main.RiskView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

//import Resources.*;
public class ActionBarView extends JPanel implements ActionBarViewListener {

    private JButton placeTroops,attack,nextTurn,deployButton;
    private Image placeImg,nextTurnImg,attackImg, lock, cancel, backImg;
    private Color darkBlue = new Color(102,178,255);
    private ActionBarController abc;
    private RiskModel riskModel;
    private RiskView riskView;
    private JComboBox numberOfTroops,numberAttackTroops;
    private JPanel deployPanel,attackPanel, messagePanel;
    private JLabel deployInfo;
    private JLabel attackerTerritoryLabel;
    private JLabel defenderTerritoryLabel;
    private JLabel defenderPlayerLabel;
    private boolean placetroopsFlag;
    private boolean attackFlag;
    private boolean messageFlag;


    public ActionBarView(RiskView riskView, RiskModel riskModel){
        this.riskView = riskView;
        this.riskModel = riskModel;
        // JPanel Config
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBackground(RiskModel.BACKGROUND);

        ActionBarModel abm = new ActionBarModel(this.riskModel,this.riskView);
        abm.addActionBarModelViews(this);
        abc = new ActionBarController(this, abm);

        initActionPanel();
        initActionButtons();

    }

    public void initActionPanel(){
        JLabel actionPanel = new JLabel("Actions:");
        actionPanel.setFont(new Font("Actions:", Font.PLAIN,20));
        actionPanel.setBackground(darkBlue);
        this.add(actionPanel);
    }

    public void initActionButtons(){
        try {
            placeImg = ImageIO.read(new File("src//Main//Resources//Soldier.PNG")).getScaledInstance(20,20, Image.SCALE_DEFAULT);
            nextTurnImg = ImageIO.read(new File("src//Main//Resources//NextTurn.PNG")).getScaledInstance(20,20, Image.SCALE_DEFAULT);
            attackImg = ImageIO.read(new File("src//Main//Resources//Attack.PNG")).getScaledInstance(20,20, Image.SCALE_DEFAULT);
        }catch(IOException ex){
        }
        placeTroops = new JButton("Place Troops", new ImageIcon(placeImg));
        placeTroops.setBackground(darkBlue);
        attack = new JButton("Attack", new ImageIcon(attackImg));
        attack.setBackground(darkBlue);
        nextTurn = new JButton("Next Turn", new ImageIcon(nextTurnImg));
        nextTurn.setBackground(darkBlue);

        this.add(placeTroops);
        this.add(attack);
        this.add(nextTurn);

        //adding Action Listeners
        placeTroops.addActionListener(abc);
        placeTroops.setActionCommand("place");
        attack.addActionListener(abc);
        attack.setActionCommand("attack");
        nextTurn.addActionListener(abc);
        nextTurn.setActionCommand("next");

    }
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
    public void attackInfo(){
        if(getPlaceTroopsFlag()){
            removeDeployTroopsBar();
        }
        attackFlag = true;
        attackPanel = new JPanel();
        JPanel defenderPanel = new JPanel();
        defenderPanel.setLayout(new BoxLayout(defenderPanel,BoxLayout.Y_AXIS));
        defenderPanel.setBackground(darkBlue);

        attackerTerritoryLabel =new JLabel("Attacker Territory: ");
        JLabel numberAttackersLabel = new JLabel("Number of Attackers: ");
        defenderTerritoryLabel = new JLabel("Defender Territory: ");
        defenderPlayerLabel = new JLabel("Defending Player: ");

        numberAttackTroops = new JComboBox<Integer>();
        JButton attackButton = new JButton("Attack",new ImageIcon(attackImg));

        try {
            backImg = ImageIO.read(new File("src//Main//Resources//back.PNG")).getScaledInstance(20,20, Image.SCALE_DEFAULT);
            lock = ImageIO.read(new File("src//Main//Resources//lock.PNG")).getScaledInstance(20,20, Image.SCALE_DEFAULT);
            cancel = ImageIO.read(new File("src//Main//Resources//cancel.PNG")).getScaledInstance(20,20, Image.SCALE_DEFAULT);
        }catch(IOException ex){
        }

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


        defenderPanel.add(defenderTerritoryLabel);
        defenderPanel.add(defenderPlayerLabel);

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

        //actionlisteners
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
    public void deployTroopsInfo(){
        placetroopsFlag = true;
        deployPanel = new JPanel();
        deployInfo = new JLabel("Territory:    Reinforcements:");
        numberOfTroops = new JComboBox<Integer>();
        deployButton= new JButton("Deploy Troops");
        deployButton.setEnabled(true);
        if(riskModel.getActivePlayer().getReinforcements()>0) {
            for (int i = 1; i <= riskModel.getActivePlayer().getReinforcements(); i++) {
                numberOfTroops.addItem(i);
            }
        }else{
            deployButton.setEnabled(false);
            numberOfTroops.setEnabled(false);
        }
        try {
            backImg = ImageIO.read(new File("src//Main//Resources//back.PNG")).getScaledInstance(20,20, Image.SCALE_DEFAULT);
        }catch(IOException ex){
        }
        JButton backButton = new JButton(new ImageIcon(backImg));

        deployPanel.add(deployInfo);
        deployPanel.add(numberOfTroops);
        deployPanel.add(deployButton);
        deployPanel.add(backButton);
        deployPanel.setBackground(darkBlue);
        this.add(deployPanel);

        //adding Actionlisteners
        numberOfTroops.addActionListener(abc);
        numberOfTroops.setActionCommand("numTroops");
        deployButton.addActionListener(abc);
        deployButton.setActionCommand("deploy");
        backButton.addActionListener(abc);
        backButton.setActionCommand("backDeploy");
        updateUI();
    }

    public void setMessage(String msg){
        messageFlag =true;
        messagePanel = new JPanel();
        JLabel message = new JLabel(msg);
        messagePanel.add(message);
        messagePanel.setBackground(darkBlue);
        this.add(messagePanel);
        updateUI();
    }

    public void removeDeployTroopsBar() {
        if (deployPanel != null) {
            deployPanel.setVisible(false);
            placetroopsFlag = false;
        }
    }
    public void removeAttackBar(){
        if(attackPanel!=null){
            attackPanel.setVisible(false);
            attackFlag = false;
        }
    }
    public void removeMessageBar(){
        if(messagePanel !=null){
            messagePanel.setVisible(false);
            messageFlag = false;
        }
    }
    public JComboBox<Integer> getNumberOfTroops(){
        return numberOfTroops;
    }
    public JComboBox<Integer> getAttackNumberOfTroops(){
        return numberAttackTroops;
    }
    public ActionBarController getAbc(){
        return abc;
    }
    public void setDeployInfo(){
        deployInfo.setText("Country: "+ abc.getTerritory() + "   Reinforcements: ");
    }
    public void setAttackerInfo(){
        attackerTerritoryLabel.setText("Attacker Territory: " + abc.getAttackerTerritory());
    }
    public void setDefenderInfo(){
        defenderTerritoryLabel.setText("Defender Territory:" + abc.getDefenderTerritory());
        defenderPlayerLabel.setText("Defending Player: " + abc.getDefenderTerritory().getOwner().getName());
    }
    public boolean getPlaceTroopsFlag(){
        return placetroopsFlag;
    }
    public boolean getAttackFlag(){
        return attackFlag;
    }
    public boolean getMessageFlag(){
        return messageFlag;
    }

    @Override
    public void handleTroopDeployment(ActionBarEvent e){
        int reinforcements = e.getReinforcements();
            System.out.println("The (1) reinforcements are: " + reinforcements);
    }
}
