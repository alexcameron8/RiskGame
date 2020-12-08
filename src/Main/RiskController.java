package Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RiskController implements ActionListener {

    private RiskView riskView;
    private RiskModel riskModel;

    public RiskController(RiskModel riskModel, RiskView riskView){
        this.riskModel = riskModel;
        this.riskView = riskView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("load")){
            String fileName = JOptionPane.showInputDialog(riskView, "Enter Risk Game's name to load");
            riskModel.load(fileName);
        }else if(e.getActionCommand().equals("newGame")){
            riskView.setupInit();
        }
    }
}
