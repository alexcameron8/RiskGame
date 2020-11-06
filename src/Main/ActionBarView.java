package Main;

import javax.swing.*;
import java.awt.*;

public class ActionBarView extends JPanel {
    ActionBarView(){
        // JPanel Config
        this.setLayout(new BorderLayout());
        this.setBackground(Color.RED);
        this.add(new JLabel("Action Bar"), BorderLayout.CENTER);
    }
}
