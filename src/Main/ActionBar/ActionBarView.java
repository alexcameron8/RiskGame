package Main.ActionBar;

import Main.RiskController;
import Main.RiskModel;
import Main.RiskView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

//import Resources.*;
public class ActionBarView extends JPanel implements ActionBarViewListener {

    private JButton placeTroops;
    private JButton attack;
    private JButton nextTurn;
    private Image placeImg;
    private Image nextTurnImg;
    private Image attackImg;
    private Color darkBlue = new Color(102,178,255);
    private ActionBarController abc;
    private RiskModel riskModel;
    private RiskView riskView;
    private JComboBox numberOfTroops;
    private JButton deployButton;
    private JPanel deployPanel;
    private int reinforcements;

    public ActionBarView(RiskView riskView, RiskModel riskModel){
        this.riskView = riskView;
        this.riskModel = riskModel;
        // JPanel Config
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBackground(new Color(153,204,255));

        ActionBarModel abm = new ActionBarModel(this.riskModel,this.riskView);
        abm.addActionBarModelViews(this);
        abc = new ActionBarController(this, abm);

        initActionPanel();
        initActionButtons();

    }

    public void initActionPanel(){
        JLabel actionPanel = new JLabel("Actions:");
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


        //delete this
        JButton testStart = new JButton("Test Start");
        this.add(testStart);
        testStart.addActionListener(abc);
        testStart.setActionCommand("setup");
    }

    public void deployTroopsInfo(){
        deployPanel = new JPanel();
        JLabel deployInfo = new JLabel("Click Country. Choose reinforcements:");
        numberOfTroops = new JComboBox<Integer>();
        deployButton= new JButton("Deploy Troops");
        deployButton.setEnabled(true);
        if(riskModel.getActivePlayer().getReinforcement()>0) {
            for (int i = 1; i <= riskModel.getActivePlayer().getReinforcement(); i++) {
                numberOfTroops.addItem(i);
            }
        }else{
            numberOfTroops.setEnabled(false);
        }
        if(reinforcements==0){
          //  deployButton.setEnabled(false);
            updateUI();
        }
        deployPanel.add(deployInfo);
        deployPanel.add(numberOfTroops);
        deployPanel.add(deployButton);
        deployPanel.setBackground(darkBlue);
        this.add(deployPanel);

        //adding Actionlisteners
        numberOfTroops.addActionListener(abc);
        numberOfTroops.setActionCommand("numTroops");
        deployButton.addActionListener(abc);
        deployButton.setActionCommand("deploy");
        updateUI();
    }
    public void removeDeployTroopsBar(){
        this.remove(deployPanel);
    }
    public JComboBox<Integer> getNumberOfTroops(){
        return numberOfTroops;
    }
    public ActionBarController getAbc(){
        return abc;
    }

    @Override
    public void handleTroopDeployment(ActionBarEvent e){
        if(reinforcements>=0) {
            reinforcements = e.getReinforcements();
            System.out.println("The (1) reinforcements are: " + reinforcements);
        }else if(reinforcements<0){
            reinforcements =0;
            System.out.println("The (2) reinforcements are: " + reinforcements);
        }else{
            System.out.println("different error (3).");
        }
    }
}
