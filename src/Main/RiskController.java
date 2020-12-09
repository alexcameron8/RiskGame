package Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This is the RiskController which controls the RiskView buttons
 */
public class RiskController implements ActionListener {

    private RiskView riskView;
    private RiskModel riskModel;

    /**
     * The constructor for the RiskController
     * @param riskModel The current RiskModel
     * @param riskView The current RiskView
     */
    public RiskController(RiskModel riskModel, RiskView riskView){
        this.riskModel = riskModel;
        this.riskView = riskView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("newGame")){
            riskView.setupInit();
        }else if(e.getActionCommand().equals("start")){
            riskView.setupPlayers();
            riskView.setupView();
        }else if(e.getActionCommand().equals("cancel")){
            riskView.disposeSetup();
        } else if (e.getSource() instanceof JFileChooser && e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)){
            JFileChooser source = (JFileChooser) e.getSource();

            riskModel.load(source.getSelectedFile().getAbsolutePath(), riskView);
            riskView.setupView();
        }
    }
}
