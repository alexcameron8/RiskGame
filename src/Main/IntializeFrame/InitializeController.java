package Main.IntializeFrame;

import Main.RiskView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitializeController implements ActionListener {

    private InitializeModel im;
    private InitializeView iv;

    public InitializeController(InitializeModel im, InitializeView iv){
        this.im=im;
        this.iv=iv;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("numPlayers")) {
            im.setPlayerNumbers((int)(((JComboBox) e.getSource()).getSelectedItem()));
        }
    }
}
