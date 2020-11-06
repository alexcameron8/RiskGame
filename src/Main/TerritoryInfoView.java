package Main;

import javax.swing.*;
import java.awt.*;

public class TerritoryInfoView extends JPanel {
    TerritoryInfoView(){
        // JPanel Config
        this.setLayout(new BorderLayout());
        this.setBackground(Color.GREEN);
        this.add(new JLabel("Territory Info"), BorderLayout.CENTER);
    }
}
