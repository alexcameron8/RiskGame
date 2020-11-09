package Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class MenuBarController implements ActionListener {
    private RiskModel riskModel;
    private RiskView riskView;
    public MenuBarController(RiskModel riskModel, RiskView riskView){
        this.riskModel = riskModel;
        this.riskView = riskView;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("howtoplay")) {
            try {
                Desktop.getDesktop().browse(new URL("https://en.wikipedia.org/wiki/Risk_(game)").toURI());
            } catch (Exception a) {}
        }else if (e.getActionCommand().equals("manual")) {
            try {
                Desktop.getDesktop().browse(new URL("https://drive.google.com/file/d/1ZyVn-hjSJK7hDP9uS90wCvZ496TQ_Wyi/view").toURI());
            } catch (Exception a) {}
        }else if(e.getActionCommand().equals("newGame")){
            //creates new game
            riskView.dispose();
            RiskModel newGame = riskModel.newGame();
            riskView.newRiskView(newGame);
        }
    }
}

