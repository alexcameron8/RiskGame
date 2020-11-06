package Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RiskController implements ActionListener {
    private RiskModel rm;
    private RiskView rv;

    public RiskController(RiskModel rm, RiskView rv){
        this.rv = rv;
        this.rm = rm;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if(o instanceof JComboBox){
            System.out.println("USer chose " + rv.getComboBox().getSelectedItem() + "players");
        }
    }
}
