package Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RiskController implements ActionListener {
    private RiskModel riskModel;
    private RiskView riskView;

    public RiskController(RiskModel riskModel, RiskView riskView) {
        this.riskView = riskView;
        this.riskModel = riskModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o instanceof JComboBox) {
            if (riskView.getPlayerBox().getSelectedItem() != null) {
                riskView.setNumOfPlayers((Integer) riskView.getPlayerBox().getSelectedItem());
            }
        }
    }
}
