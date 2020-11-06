package Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;
import java.io.IOException;

//import Resources.*;
public class ActionBarView extends JPanel {

    private JButton placeTroops;
    private JButton attack;
    private JButton nextTurn;
    private Image placeImg;
    private Image nextTurnImg;
    private Image attackImg;
    private Color darkBlue = new Color(102,178,255);

    ActionBarView(){
        // JPanel Config
        this.setLayout(new FlowLayout());
        this.setBackground(new Color(153,204,255));
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
    }
}
