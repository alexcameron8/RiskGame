package Main.ActionBar;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionBarController implements ActionListener {
    private ActionBarView abv;
    private ActionBarModel abm;
    public ActionBarController(ActionBarView abv, ActionBarModel abm){
        this.abv = abv;
        this.abm = abm;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        Object o = e.getSource();
        if(o instanceof JButton){
            if(e.getActionCommand().equals("place")){
                //place troops updating the model
                System.out.println("place troops");
            }else if(e.getActionCommand().equals("attack")){
                //attack
                abm.attack();
                System.out.println("attack");
            }else if(e.getActionCommand().equals("next")){
                //advances turn
                abm.getRiskModel().advanceTurn();
                System.out.println("next turn");
            }
        }
    }
}
