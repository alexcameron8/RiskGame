package Main.IntializeFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class InitializeView extends JFrame implements InitializeViewListener {

    private JComboBox numPlayers;
    private JTextField[] nameOfPlayers;
    private JPanel nameOfPlayersPanel;
    private Integer[] numberOfPlayers;
    private JPanel buttonPanel;

    public InitializeView(){
        super("Initialize Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(400, 400));

        InitializeModel im = new InitializeModel();
        im.addInitializeView(this);
        InitializeController ic = new InitializeController(im, this);

        numberOfPlayers = new Integer[InitializeModel.MAX_NUMBER_PLAYERS-1];
        for(int i = InitializeModel.MIN_NUMBER_PLAYERS; i <= InitializeModel.MAX_NUMBER_PLAYERS; i++){
            numberOfPlayers[i-InitializeModel.MIN_NUMBER_PLAYERS]=i;
        }

        numPlayers= new JComboBox(numberOfPlayers);
        numPlayers.setMinimumSize(new Dimension(this.getWidth(),this.getHeight()/(InitializeModel.MAX_NUMBER_PLAYERS+1)));
        numPlayers.setBorder(BorderFactory.createTitledBorder("Select number of Players:"));
        numPlayers.addActionListener(ic);
        numPlayers.setActionCommand("numPlayers");
        this.add(numPlayers, BorderLayout.PAGE_START);

        nameOfPlayers = new JTextField[InitializeModel.MAX_NUMBER_PLAYERS];
        nameOfPlayersPanel = new JPanel();
        nameOfPlayersPanel.setLayout(new BoxLayout(nameOfPlayersPanel, BoxLayout.Y_AXIS));
        for (int i=0; i < InitializeModel.MAX_NUMBER_PLAYERS; i++) {
            JTextField playerName = new JTextField();
            nameOfPlayers[i]=playerName;
            nameOfPlayers[i].setMaximumSize(new Dimension(this.getWidth(), this.getHeight()/(InitializeModel.MAX_NUMBER_PLAYERS+1)));
            playerName.addActionListener(ic);
            playerName.setActionCommand("player"+(i+1));
            playerName.setBorder(BorderFactory.createTitledBorder("Name of Player "+(i+1) + ":"));
        }
        for (int i=0; i < InitializeModel.MIN_NUMBER_PLAYERS; i++) {
            nameOfPlayersPanel.add(nameOfPlayers[i]);
        }
        this.add(nameOfPlayersPanel, BorderLayout.CENTER);


        buttonPanel = new JPanel();

        JButton continueButton = new JButton("Continue");
        continueButton.addActionListener(ic);
        continueButton.setActionCommand("continue");
        buttonPanel.add(continueButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(ic);
        cancelButton.setActionCommand("cancel");
        buttonPanel.add(cancelButton);

        this.add(buttonPanel, BorderLayout.PAGE_END);
        this.setVisible(true);
    }



    @Override
    public void handleInitializeUpdate(InitializeEvent e) {
        int numPlayers = e.getNumberOfPlayer();
        nameOfPlayersPanel.removeAll();
        nameOfPlayersPanel.revalidate();
        nameOfPlayersPanel.repaint();
        for (int i=0; i < numPlayers; i++) {
            nameOfPlayersPanel.add(nameOfPlayers[i]);
        }
        this.revalidate();
    }

    public ArrayList<String> getNameOfPlayers(){
        ArrayList<String> namesOfPlayers = new ArrayList<>();
        for (int i=0; i < (int) numPlayers.getSelectedItem(); i++) {
            namesOfPlayers.add(nameOfPlayers[i].getText());
        }
        return namesOfPlayers;
    }

    public static void main(String[] args) {
        new InitializeView();
    }
}
