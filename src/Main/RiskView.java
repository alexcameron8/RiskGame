package Main;

import Main.ActionBar.*;
import Main.PlayerBar.*;

import Main.ActionBar.ActionBarView;
import Main.PlayerBar.PlayerBarView;

import javax.imageio.ImageIO;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class RiskView extends JFrame {
    private RiskModel riskModel;
    private RiskController riskController;
    private final Integer[] numSetupPlayers = {null,2,3,4,5,6};
    private JComboBox<Integer> comboBox;

    RiskView(){
        this.riskModel = new RiskModel();
        this.riskController = new RiskController(riskModel,this);

        initSetup();

        this.setSize(new Dimension(800, 600));

        RiskModel rm = new RiskModel();
        rm.addRiskView(this);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setJMenuBar(new MenuBarView());

        MapView mapView = new MapView();
        TerritoryInfoView territoryInfoView = new TerritoryInfoView();
       mapView.getMapModel().addMapListener(territoryInfoView);

        this.add(new ActionBarView(), BorderLayout.PAGE_START);
        this.add(mapView, BorderLayout.CENTER);
        this.add(territoryInfoView, BorderLayout.LINE_END);
        this.add(new PlayerBarView(), BorderLayout.PAGE_END);

        this.setVisible(true);
    }
    public void initSetup(){
        //Welcome Screen
        ImageIcon icon = new ImageIcon("src/Main/Resources/Risk.png");
        JLabel labelIcon = new JLabel(icon);
        JPanel panel = new JPanel(new GridBagLayout());
        panel.add(labelIcon);
        JLabel text = new JLabel("Welcome to Risk. Click OK to continue to game setup.");
        text.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        text.setFont(new Font("Ubuntu", Font.BOLD, 20));
        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.PAGE_AXIS));
        panel2.add(panel);
        panel2.add(text);
        JOptionPane startGame = new JOptionPane();
        startGame.showMessageDialog(this,panel2, "Welcome to Risk",JOptionPane.DEFAULT_OPTION);

        //Setup Screen
        JPanel setupScreen = new JPanel();
        setupScreen.setLayout(new BorderLayout());

        //Total possible options for number of players
        comboBox = new JComboBox<>(numSetupPlayers);
        TitledBorder comboBoxTitle = BorderFactory.createTitledBorder("Select number of Players:");
        comboBox.setBorder(comboBoxTitle);

        //adding to panel
        setupScreen.add(comboBox);

        //ActionListener
        comboBox.addActionListener(riskController);
        comboBox.setActionCommand("numPlayers");

        JOptionPane.showMessageDialog(this,setupScreen,"Select Number of Players.", JOptionPane.DEFAULT_OPTION );

    }

    public JComboBox<Integer> getComboBox(){
        return comboBox;
    }

    public static void main(String[] args) {
        new RiskView();
    }
}
