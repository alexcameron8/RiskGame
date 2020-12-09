package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

/**
 * The MenuBarController which performs actions based on which Menu Items are clicked
 *
 * @Author Alex Cameron
 */
public class MenuBarController implements ActionListener {
    private RiskModel riskModel;
    private RiskView riskView;

    /**
     * Constructor gets the risk model and view which the current Risk game is being played
     * @param riskModel the current risk model
     * @param riskView the current risk view
     */
    public MenuBarController(RiskModel riskModel, RiskView riskView){
        this.riskModel = riskModel;
        this.riskView = riskView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("howtoplay")) { //brings to wikipedia website to learn how to play
            try {
                Desktop.getDesktop().browse(new URL("https://en.wikipedia.org/wiki/Risk_(game)").toURI());
            } catch (Exception ignored) {}
        }else if (e.getActionCommand().equals("manual")) { //links to the game manual
            try {
                Desktop.getDesktop().browse(new URL("https://drive.google.com/file/d/10f_wSMXT4B321GG6q1Rzht-eY5L8cPN_/view").toURI());
            } catch (Exception ignored) {}
        }else if(e.getActionCommand().equals("newGame")){ //creates a new game
            //creates new game
            riskView.dispose();
            RiskModel newGame = riskModel.newGame();
            riskView.newRiskView(newGame);
        }else if(e.getActionCommand().equals("transferAll")){
            riskModel.getActivePlayer().placeReinforcement(riskModel.getActivePlayer().getListOfTerritories().get(0), riskModel.getActivePlayer().getRemainingReinforcements());
            riskModel.getActivePlayer().transferAllTerritory(riskModel.getPlayers().get(riskModel.getActivePlayerID()+1==riskModel.getPlayers().size()? 0:riskModel.getActivePlayerID()+1));
            riskView.repaint();
            riskView.revalidate();
        }else if(e.getActionCommand().equals("save")){
            String fileName = JOptionPane.showInputDialog(riskView, "Enter Risk Game's name");
            riskModel.save(fileName);
        }
    }
}

