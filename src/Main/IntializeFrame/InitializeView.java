package Main.IntializeFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static Main.RiskModel.MAX_NUMBER_PLAYERS;
import static Main.RiskModel.MIN_NUMBER_PLAYERS;

/**
 * this class create the GUI for initializing the players
 */
public class InitializeView extends JPanel implements InitializeViewListener {

    private JComboBox numPlayers;
    private JPanel[] playerConfigPanel;
    private JComboBox[] playerColour;
    private JTextField[] nameOfPlayers;
    private JPanel playersConfigPanel;
    private Integer[] numberOfPlayers;
    private InitializeModel im;

    private static final String[] COLOURS = {null, "Red", "Green", "Blue", "Yellow", "Orange", "Purple"};
    public static final int PREFERRED_WIDTH = 400;
    public static final int PREFERRED_HEIGHT = 400;

    /**
     * constructor for InitializeView that create teh GUI
     */
    public InitializeView(){
        super();
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));

        im = new InitializeModel();
        im.addInitializeView(this);
        InitializeController ic = new InitializeController(im, this);

        numberOfPlayers = new Integer[MAX_NUMBER_PLAYERS-1];
        for(int i = MIN_NUMBER_PLAYERS; i <= MAX_NUMBER_PLAYERS; i++){
            numberOfPlayers[i-MIN_NUMBER_PLAYERS]=i;
        }

        numPlayers= new JComboBox(numberOfPlayers);
        numPlayers.setPreferredSize(new Dimension(this.getPreferredSize().width,this.getPreferredSize().height/(MAX_NUMBER_PLAYERS+1)));
        numPlayers.setBorder(BorderFactory.createTitledBorder("Select number of Players:"));
        numPlayers.addActionListener(ic);
        numPlayers.setActionCommand("numPlayers");
        this.add(numPlayers, BorderLayout.PAGE_START);

        nameOfPlayers = new JTextField[MAX_NUMBER_PLAYERS];

        playersConfigPanel = new JPanel();
        playersConfigPanel.setLayout(new BoxLayout(playersConfigPanel, BoxLayout.Y_AXIS));
        playersConfigPanel.setPreferredSize(new Dimension(this.getPreferredSize().width,this.getPreferredSize().height*MAX_NUMBER_PLAYERS/(MAX_NUMBER_PLAYERS+1)));


        playerColour = new JComboBox[MAX_NUMBER_PLAYERS];
        playerConfigPanel = new JPanel[MAX_NUMBER_PLAYERS];

        for (int i=0; i < MAX_NUMBER_PLAYERS; i++) {
            playerConfigPanel[i] = new JPanel();
            playerConfigPanel[i].setLayout(new FlowLayout(FlowLayout.LEFT));
            playerConfigPanel[i].setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            playerConfigPanel[i].setMaximumSize(new Dimension(playersConfigPanel.getPreferredSize().width, playersConfigPanel.getPreferredSize().height/(MAX_NUMBER_PLAYERS)));
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
        for (int i=0; i < MIN_NUMBER_PLAYERS; i++) {
            playersConfigPanel.add(playerConfigPanel[i]);
        }
        this.add(playersConfigPanel, BorderLayout.CENTER);
    }

    /**
     * display a number of player characteristics that is the number of players
     *
     * @param e
     */
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

    /**
     * get the final version of the player names
     *
     * @return ArrayList<String>
     */
    public ArrayList<String> getNameOfPlayers(){
        return im.getNamesOfPlayers();
    }

    /**
     * get the final version of the player colours
     *
     * @return ArrayList<Color>
     */
    public ArrayList<Color> getPlayersColour(){
        return im.getPlayersColours();
    }

    /**
     * get the final number of players
     *
     * @return int
     */
    public int getNumberOfPlayers() {
        return im.getNumberOfPlayers();
    }
}
