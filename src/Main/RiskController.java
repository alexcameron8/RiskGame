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
        if(e.getActionCommand().equals("load")){
            String fileName = "";
            try {
                fileName = JOptionPane.showInputDialog(riskView, "Enter Risk Game's name to load");
            }catch (Exception exception){
                JOptionPane.showMessageDialog(riskView,"Could not load game: " + fileName);
            }
            if(fileName!=null && !fileName.equals("")) {
                riskModel.load(fileName, riskView);
                riskView.setupView();
            }else{
                JOptionPane.showMessageDialog(riskView,"Could not load that game.");
            }
        }else if(e.getActionCommand().equals("newGame")){
            riskView.setupInit();
        }else if(e.getActionCommand().equals("start")){
            riskView.setupPlayers();
            riskView.setupView();
        }else if(e.getActionCommand().equals("cancel")){
            riskView.disposeSetup();
        }
    }
}
