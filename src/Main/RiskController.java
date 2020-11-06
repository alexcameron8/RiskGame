package Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RiskController implements ActionListener {
    private RiskModel rm;
    private RiskView rv;
    private int numOfPlayers;
    public RiskController(RiskModel rm, RiskView rv){
        this.rv = rv;
        this.rm = rm;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if(o instanceof JComboBox){
            numOfPlayers = (Integer)rv.getPlayerBox().getSelectedItem();
            rv.setupPlayers(numOfPlayers);
            System.out.println("USer chose " + rv.getPlayerBox().getSelectedItem() + "players");
        }
    }
}
