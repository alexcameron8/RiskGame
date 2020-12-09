package Main.IntializeFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import static Main.RiskModel.MAX_NUMBER_PLAYERS;
import static Main.RiskModel.MIN_NUMBER_PLAYERS;

/**
 * this class create the GUI for initializing the players
 * @author Thomas
 */
public class InitializeView extends JPanel implements InitializeViewListener {

    public static final String ACTION_MAP_SELECT = "mapSelectAction";

    private JPanel[] playerConfigPanel;
    private JPanel playersConfigPanel;
    private final InitializeModel im;
    private final InitializeController ic;


    private static final String[] COLOURS = {null, "Red", "Green", "Blue", "Yellow", "Orange", "Purple"};
    public static final int PREFERRED_WIDTH = 400;
    public static final int PREFERRED_HEIGHT = 400;
    //Actioncommand constants
    public static final String NUMBER_PLAYERS_NAME = "numPlayers";
    public static final String AI_CHECKBOX_NAME = "isAI";
    public static final String COLOUR_NAME = "colour";
    public static final String PLAYER_NAME = "name";

    /**
     * constructor for InitializeView that create teh GUI
     */
    public InitializeView(){
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));

        im = new InitializeModel();
        im.addInitializeView(this);
        ic = new InitializeController(im);

        createMapSelectRequest();
        
        createNumberOfPlayersRequest();

        createPlayersInfoRequest();
    }

    /**
     * This creates the JComboBox to the the initial view with a list of the available maps that are playable.
     *
     */
    private void createMapSelectRequest(){
        JComboBox mapSelect;
        mapSelect = new JComboBox(InitializeModel.AVAILABLE_MAPS.keySet().toArray(new String[0]));
        mapSelect.setPreferredSize(new Dimension(this.getPreferredSize().width,this.getPreferredSize().height/(MAX_NUMBER_PLAYERS+1)));
        mapSelect.setBorder(BorderFactory.createTitledBorder("Select map:"));
        mapSelect.addActionListener(ic);
        mapSelect.setActionCommand(ACTION_MAP_SELECT);
        mapSelect.setSelectedItem(InitializeModel.DEFAULT_MAP);
        this.add(mapSelect);
    }

    /**
     * This creates the panel with the player configurations. This is where players are created and specified whether they are
     * an AI or not. As well, the list of possible player colours is there in a drop down menu.
     *
     */
    private void createPlayersInfoRequest(){
        JTextField[] nameOfPlayers = new JTextField[MAX_NUMBER_PLAYERS];

        playersConfigPanel = new JPanel();
        playersConfigPanel.setLayout(new BoxLayout(playersConfigPanel, BoxLayout.Y_AXIS));
        playersConfigPanel.setPreferredSize(new Dimension(this.getPreferredSize().width,this.getPreferredSize().height*MAX_NUMBER_PLAYERS/(MAX_NUMBER_PLAYERS+1)));


        JComboBox[] playerColour = new JComboBox[MAX_NUMBER_PLAYERS];
        playerConfigPanel = new JPanel[MAX_NUMBER_PLAYERS];

        JCheckBox[] playerIsAI = new JCheckBox[MAX_NUMBER_PLAYERS];

        JLabel[] playerIsAILabel = new JLabel[MAX_NUMBER_PLAYERS];

        for (int i=0; i < MAX_NUMBER_PLAYERS; i++) {
            playerConfigPanel[i] = new JPanel();
            playerConfigPanel[i].setLayout(new GridBagLayout());
            playerConfigPanel[i].setBorder(BorderFactory.createTitledBorder("Player "+(i+1) + " setup:"));
            playerConfigPanel[i].setMaximumSize(
                    new Dimension(playersConfigPanel.getPreferredSize().width,
                            playersConfigPanel.getPreferredSize().height/(MAX_NUMBER_PLAYERS)));
            GridBagConstraints gridBagConstraints = new GridBagConstraints();

            nameOfPlayers[i]=new JTextField();
            nameOfPlayers[i].getDocument().addDocumentListener(ic);
            nameOfPlayers[i].getDocument().putProperty(PLAYER_NAME,(i+1));
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.weightx = 0.8;
            gridBagConstraints.fill = GridBagConstraints.BOTH;
            playerConfigPanel[i].add(nameOfPlayers[i], gridBagConstraints);

            playerColour[i] = new JComboBox(COLOURS);
            playerColour[i].addActionListener(ic);
            playerColour[i].setActionCommand(COLOUR_NAME+" "+(i+1));
            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.weightx = 0.1;
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            playerConfigPanel[i].add(playerColour[i], gridBagConstraints);

            playerIsAILabel[i] = new JLabel("AI: ");
            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 2;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.weightx = 0.05;
            gridBagConstraints.fill = GridBagConstraints.NONE;
            gridBagConstraints.anchor = GridBagConstraints.LINE_END;
            playerConfigPanel[i].add(playerIsAILabel[i], gridBagConstraints);

            playerIsAI[i] = new JCheckBox();
            playerIsAI[i].setActionCommand(AI_CHECKBOX_NAME +" "+ (i+1));
            playerIsAI[i].addActionListener(ic);
            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 3;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.weightx = 0.05;
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints.anchor = GridBagConstraints.LINE_START;
            playerConfigPanel[i].add(playerIsAI[i], gridBagConstraints);

        }
        for (int i=0; i < MIN_NUMBER_PLAYERS; i++) {
            playersConfigPanel.add(playerConfigPanel[i]);
        }
        this.add(playersConfigPanel);
    }

    /**
     * Changes the total number of players to be created. For instance, there can be a minimum of 2 players in the
     * game and a maximum of 6 players.
     */
    private void createNumberOfPlayersRequest(){

        Integer[] numberOfPlayers = new Integer[MAX_NUMBER_PLAYERS - 1];
        for(int i = MIN_NUMBER_PLAYERS; i <= MAX_NUMBER_PLAYERS; i++){
            numberOfPlayers[i-MIN_NUMBER_PLAYERS]=i;
        }

        JComboBox numPlayers = new JComboBox(numberOfPlayers);
        numPlayers.setPreferredSize(new Dimension(this.getPreferredSize().width,this.getPreferredSize().height/(MAX_NUMBER_PLAYERS+1)));
        numPlayers.setBorder(BorderFactory.createTitledBorder("Select number of Players:"));
        numPlayers.addActionListener(ic);
        numPlayers.setActionCommand(NUMBER_PLAYERS_NAME);
        this.add(numPlayers, BorderLayout.PAGE_START);
    }

    /**
     * Display a number of player characteristics that is the number of players
     *
     * @param e The initializing event
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
     * Get the final version of the player names
     *
     * @return ArrayList<String> Returns a list of all the names of the players being added
     */
    public ArrayList<String> getNameOfPlayers(){
        return im.getNamesOfPlayers();
    }

    /**
     * get the final version of the player colours
     *
     * @return ArrayList<Color> Returns an arraylist of the players colours
     */
    public ArrayList<Color> getPlayersColour(){
        return im.getPlayersColours();
    }

    /**
     * Gets the arraylist of of all players and if they are an Ai or not
     *
     * @return ArrayList<Boolean> Returns an arraylist of all the players and whether they are an AI or not.
     */
    public ArrayList<Boolean> getIsPlayerAI(){
        return im.getPlayersIsAI();
    }


    /**
     * get the final number of players
     *
     * @return int Returns the total number of players
     */
    public int getNumberOfPlayers() {
        return im.getNumberOfPlayers();
    }

    /**
     * get the path of the selected map
     * @return Path of the selected map
     */
    public String getMapPath() { return im.getMapPath(); }

    /**
     * Gets the name of the selected map
     * @return returns the name of the selected map
     */
    public String getMapName(){
        return im.getMapName();
    }
}
