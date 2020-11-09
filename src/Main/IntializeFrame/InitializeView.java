package Main.IntializeFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class InitializeView extends JPanel implements InitializeViewListener {

    private JComboBox numPlayers;
    private JPanel[] playerConfigPanel;
    private JComboBox[] playerColour;
    private JTextField[] nameOfPlayers;
    private JPanel playersConfigPanel;
    private Integer[] numberOfPlayers;
    private InitializeModel im;

    public static final String[] COLOURS = {null, "Red", "Green", "Blue", "Yellow", "Orange", "Purple"};
    public static final int PREFERRED_WIDTH = 400;
    public static final int PREFERRED_HEIGHT = 400;

    public InitializeView(){
        super();
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));

        im = new InitializeModel();
        im.addInitializeView(this);
        InitializeController ic = new InitializeController(im, this);

        numberOfPlayers = new Integer[InitializeModel.MAX_NUMBER_PLAYERS-1];
        for(int i = InitializeModel.MIN_NUMBER_PLAYERS; i <= InitializeModel.MAX_NUMBER_PLAYERS; i++){
            numberOfPlayers[i-InitializeModel.MIN_NUMBER_PLAYERS]=i;
        }

        numPlayers= new JComboBox(numberOfPlayers);
        numPlayers.setPreferredSize(new Dimension(this.getPreferredSize().width,this.getPreferredSize().height/(InitializeModel.MAX_NUMBER_PLAYERS+1)));
        numPlayers.setBorder(BorderFactory.createTitledBorder("Select number of Players:"));
        numPlayers.addActionListener(ic);
        numPlayers.setActionCommand("numPlayers");
        this.add(numPlayers, BorderLayout.PAGE_START);

        nameOfPlayers = new JTextField[InitializeModel.MAX_NUMBER_PLAYERS];

        playersConfigPanel = new JPanel();
        playersConfigPanel.setLayout(new BoxLayout(playersConfigPanel, BoxLayout.Y_AXIS));
        playersConfigPanel.setPreferredSize(new Dimension(this.getPreferredSize().width,this.getPreferredSize().height*InitializeModel.MAX_NUMBER_PLAYERS/(InitializeModel.MAX_NUMBER_PLAYERS+1)));


        playerColour = new JComboBox[InitializeModel.MAX_NUMBER_PLAYERS];
        playerConfigPanel = new JPanel[InitializeModel.MAX_NUMBER_PLAYERS];

        for (int i=0; i < InitializeModel.MAX_NUMBER_PLAYERS; i++) {
            playerConfigPanel[i] = new JPanel();
            playerConfigPanel[i].setLayout(new FlowLayout(FlowLayout.LEFT));
            playerConfigPanel[i].setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            playerConfigPanel[i].setMaximumSize(new Dimension(playersConfigPanel.getPreferredSize().width, playersConfigPanel.getPreferredSize().height/(InitializeModel.MAX_NUMBER_PLAYERS)));

            playerColour[i] = new JComboBox(COLOURS);
            playerColour[i].addActionListener(ic);
            playerColour[i].setActionCommand("colour "+(i+1));
            playerColour[i].setPreferredSize(new Dimension(playerConfigPanel[i].getMaximumSize().width/5, playerConfigPanel[i].getMaximumSize().height/2));

            nameOfPlayers[i]=new JTextField();
            nameOfPlayers[i].getDocument().addDocumentListener(ic);
            nameOfPlayers[i].getDocument().putProperty("name",(i+1));
            nameOfPlayers[i].setPreferredSize(new Dimension((playerConfigPanel[i].getMaximumSize().width*3)/4, playerConfigPanel[i].getMaximumSize().height/2));


            playerConfigPanel[i].setBorder(BorderFactory.createTitledBorder("Player "+(i+1) + " setup:"));
            playerConfigPanel[i].add(nameOfPlayers[i]);
            playerConfigPanel[i].add(playerColour[i]);

        }
        for (int i=0; i < InitializeModel.MIN_NUMBER_PLAYERS; i++) {
            playersConfigPanel.add(playerConfigPanel[i]);
        }
        this.add(playersConfigPanel, BorderLayout.CENTER);
    }

    @Override
    public void handleInitializeUpdate(InitializeEvent e) {
        int numPlayers = e.getNumberOfPlayer();
        playersConfigPanel.removeAll();
        playersConfigPanel.revalidate();
        playersConfigPanel.repaint();
        for (int i=0; i < numPlayers; i++) {
            playersConfigPanel.add(playerConfigPanel[i]);
        }
        this.revalidate();
    }

    public ArrayList<String> getNameOfPlayers(){
        return im.getNamesOfPlayers();
    }

    public ArrayList<Color> getPlayersColour(){
        return im.getPlayersColours();
    }

    public static void main(String[] args) {
        InitializeView initializeGame = new InitializeView();
        int test = JOptionPane.showConfirmDialog(null, initializeGame, "JOptionPane Example : ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        for(String name : initializeGame.getNameOfPlayers()){
            System.out.println(name);
        }
        for(Color colour : initializeGame.getPlayersColour()){
            System.out.println(colour);
        }
    }

    public int getNumberOfPlayers() {
        return im.getNumberOfPlayers();
    }
}
