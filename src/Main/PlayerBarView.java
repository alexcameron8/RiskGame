package Main;

import javax.swing.*;
import java.awt.*;

public class PlayerBarView extends JPanel {
    PlayerBarView(){
        // JPanel Config
        this.setLayout(new BorderLayout());
        this.setBackground(Color.MAGENTA);
        this.add(new JLabel("Player Bar"), BorderLayout.CENTER);
    }
}
