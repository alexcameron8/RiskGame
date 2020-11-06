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
                //place troops
                System.out.println("place troops");
            }else if(e.getActionCommand().equals("attack")){
                //attack
                System.out.println("attack");
            }else if(e.getActionCommand().equals("next")){
                System.out.println("next turn");
            }
        }
    }
}
